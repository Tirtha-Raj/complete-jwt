package com.fresco.tendermanagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresco.tendermanagement.model.BiddingModel;
import com.fresco.tendermanagement.model.UserModel;
import com.fresco.tendermanagement.repository.BiddingRepository;
import com.fresco.tendermanagement.repository.UserRepository;

@Service
public class BiddingService {

	@Autowired
	private BiddingRepository biddingRepository;

	@Autowired
	private UserRepository userRepository;

	// created()->201
	// badRequest()->400
	public ResponseEntity<Object> postBidding(String username, @RequestBody BiddingModel bidding) {
		try {
			Optional<UserModel> user = userRepository.findByEmail(username);
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = sdf.format(currentDate);
			BiddingModel bid = biddingRepository.findByBiddingId(bidding.getBiddingId());
			if (bid == null) {
				BiddingModel bids = new BiddingModel();
				bids.setBiddingId(bidding.getBiddingId());
				bids.setBidAmount(bidding.getBidAmount());
				bids.setYearsToComplete(bidding.getYearsToComplete());
				// bids.setBidderId(bidding.getBidderId());
				bids.setDateOfBidding(formattedDate);
				bids.setBidderId(user.get().getId());
				BiddingModel bidder = biddingRepository.save(bids);
				return ResponseEntity.status(HttpStatus.CREATED).body(bidder);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
		}
	}

	// OK()->200
	// badRequest()->400
	public ResponseEntity<Object> updateBidding(@PathVariable int id, @RequestBody BiddingModel biddingModel) {
		BiddingModel bidding = biddingRepository.findByBiddingId(id);
		if (bidding == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND");
		}
		bidding.setStatus(biddingModel.getStatus());
		biddingRepository.save(bidding);
		return ResponseEntity.status(HttpStatus.OK).body(bidding);
	}

	// OK()->200
	// badRequest()->400
	public ResponseEntity<Object> getBidding(@RequestParam double bidAmount) {
		List<BiddingModel> biddings = biddingRepository.findByAmountGreaterThan(bidAmount);
		if (biddings.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Data Abailable");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(biddings);
		}
	}

	// OK()->200
	// badRequest()->400
	public ResponseEntity<Object> getAllBidding() {
		List<BiddingModel> biddings = biddingRepository.findAll();
		if (biddings.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Data Available");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(biddings);
		}
	}

	// noContent()->204
	// forbidden()->403
	// badRequest()->400
	public ResponseEntity<Object> deleteBidding(String username, int id) {
		Optional<UserModel> user = userRepository.findByEmail(username);
		BiddingModel bidding = biddingRepository.findByBiddingId(id);
		if (bidding == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND");
		} else if (user.get().getRole().getRolename().equals("APPROVER")
				|| (user.get().getId() == bidding.getBidderId())) {
			biddingRepository.delete(bidding);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("DELETED SUCCESSFULLY");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Not Authenticated");
		}

	}

}
