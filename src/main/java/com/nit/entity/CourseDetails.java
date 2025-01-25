package com.nit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "JRTP01")
@Data
public class CourseDetails {
	@Id
	@SequenceGenerator(name = "gen1",sequenceName = "seq_1",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "gen1",strategy = GenerationType.SEQUENCE)
	private Integer courseId;
	@Column(length = 30)
	private String courseName;
	@Column(length = 20)
	private String location;
	@Column(length = 20)
	private String courseCategory;
	@Column(length = 20)
	private String facultyName;
	private Double courseFee;
	@Column(length = 20)
	private String adminName;
	private Long adminContact;
	@Column(length = 20)
	private String trainingMode;
	private LocalDateTime startDate;
	@Column(length = 20)
	private String courseStatus;
	
	
	@Column(length = 20)
	private String createdBy;
	@Column(length = 20)
	private String updatedBy;

}
