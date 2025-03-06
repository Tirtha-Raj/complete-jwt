package com.fresco.tendermanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BiddingModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true)
	private int biddingId;
	private final String projectName ="Metro Phase V 2024";
	private Double bidAmount;
	private Double yearsToComplete;
	@JsonFormat(pattern = "dd/mm/yyyy")
	private String dateOfBidding;
	private String status = "pending";
	private int bidderId;
}
