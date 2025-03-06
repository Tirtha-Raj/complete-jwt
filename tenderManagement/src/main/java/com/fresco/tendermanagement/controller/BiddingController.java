package com.fresco.tendermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.tendermanagement.model.BiddingModel;
import com.fresco.tendermanagement.service.BiddingService;

@RestController
@RequestMapping("/bidding")
public class BiddingController {
	
	@Autowired
	private BiddingService biddingService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postBidding(@RequestBody BiddingModel bidding){
		return biddingService.postBidding(getCurrentUser(), bidding);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getBidding(@RequestParam double bidAmount){
		return biddingService.getBidding(bidAmount);
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<Object> getBidding(){
		return biddingService.getAllBidding();
	}
	
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateBidding(@PathVariable int id,@RequestBody BiddingModel biddingModel){
		return biddingService.updateBidding(id, biddingModel);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteMapping(@PathVariable int id){
		return biddingService.deleteBidding(getCurrentUser(), id);
	}
	
    private String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
	
}
