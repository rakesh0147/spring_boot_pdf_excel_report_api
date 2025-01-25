package com.nit.rest;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nit.model.SearchInputs;
import com.nit.model.SearchResults;
import com.nit.service.ICourseMgmtService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CouresReportOperationsController {
	@Autowired
	private ICourseMgmtService courseMgmtService;
	
	/*
	 * @PostMapping("/save") public ResponseEntity<?> save(@RequestBody
	 * CourseDetails course){
	 * 
	 * course.setAdminName("Admin3"); course.setTrainingMode("offline");
	 * course.setAdminContact(9495789035l); course.setCourseCategory("AI");
	 * course.setCourseFee(6000.0); course.setCourseName("Machine Learning");
	 * course.setCourseStatus("Active"); course.setCreatedBy("NARESHIT");
	 * course.setFacultyName("Vijay Kumar"); course.setLocation("HYDERABAD");
	 * course.setStartDate(LocalDateTime.now()); course.setUpdatedBy("ADMIN3");
	 * System.out.println(course); String saveCourse =
	 * courseMgmtService.saveCourse(course);
	 * 
	 * return new ResponseEntity<String>(saveCourse,HttpStatus.OK); }
	 */
	@GetMapping("/courses")
	public ResponseEntity<?> fetchCourseCategories(){
		try {
			List<String> courseCategories = courseMgmtService.showAllCourseCategories();
			System.out.println(courseCategories);
			return new ResponseEntity<List<String>>(courseCategories,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/training-modes")
	public ResponseEntity<?> fetchTrainingModes(){
		try {
			Set<String> trainingModes = courseMgmtService.showAllTrainingModes();
			return new ResponseEntity<Set<String>>(trainingModes,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/faculties")
	public ResponseEntity<?> fetchFaculties(){
		try {
			Set<String> faculties = courseMgmtService.showAllFaculties();
			return new ResponseEntity<Set<String>>(faculties,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/search")
	public ResponseEntity<?> fetchCouresesByFilters(@RequestBody SearchInputs inputs){
		System.out.println(inputs);
		try {
			List<SearchResults> courseByFilters = courseMgmtService.showCourseByFilters(inputs);
			return new ResponseEntity<List<SearchResults>>(courseByFilters,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/pdf-report")
	public void showPdfReport(@RequestBody SearchInputs inputs,HttpServletResponse rep){
		try {
			rep.setContentType("application/pdf");
			rep.setHeader("Content-Disposition","attachment;fileName=course.pdf");
			courseMgmtService.generatePdfReport(inputs, rep);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PostMapping("/excel-report")
	public void showExcelReport(@RequestBody SearchInputs inputs,HttpServletResponse rep){
		try {
			rep.setContentType("application/vnd.ms-excel");
			rep.setHeader("Content-Disposition","attachment;fileName=course.xls");
			courseMgmtService.generateExcelReport(inputs, rep);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@GetMapping("/all-pdf-report")
	public void showPdfReportAllData(HttpServletResponse rep){
		try {
			rep.setContentType("application/pdf");
			rep.setHeader("Content-Disposition","attachment;fileName=course.pdf");
			courseMgmtService.generatePdfReportAllData(rep);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@GetMapping("/all-excel-report")
	public void showExcelReportAllData(HttpServletResponse rep){
		try {
			rep.setContentType("application/vnd.ms-excel");
			rep.setHeader("Content-Disposition","attachment;fileName=course.xls");
			courseMgmtService.generateExcelReportAllData(rep);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
