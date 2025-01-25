package com.nit.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResults {
	
	private Integer courseId;
	private String courseName;
	private String location;
	private String courseCategory;
	private String trainingMode;
	private String facultyName;
	private String adminName;
	private String courseStatus;
	private Double courseFee;
	private Long adminContact;
	private LocalDateTime startDate;

}
