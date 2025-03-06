package com.fresco.tendermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fresco.tendermanagement.model.BiddingModel;

@Repository
public interface BiddingRepository extends JpaRepository<BiddingModel, Integer>{
	
	@Query("SELECT b FROM BiddingModel b WHERE b.bidAmount>:bidAmount")
	List<BiddingModel> findByAmountGreaterThan(double bidAmount);
	
	BiddingModel findByBiddingId(int id);
}
