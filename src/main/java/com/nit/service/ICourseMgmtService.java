package com.nit.service;

import java.util.List;
import java.util.Set;

import com.nit.entity.CourseDetails;
import com.nit.model.SearchInputs;
import com.nit.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

public interface ICourseMgmtService {
	
	public String saveCourse(CourseDetails course);
	public List<String> showAllCourseCategories();
	public Set<String> showAllTrainingModes();
	public Set<String> showAllFaculties();
	
	public List<SearchResults> showCourseByFilters(SearchInputs inputs);
    
	public void generatePdfReport(SearchInputs inputs,HttpServletResponse rep) throws Exception;
	public void generateExcelReport(SearchInputs inputs,HttpServletResponse rep) throws Exception;
	public void generatePdfReportAllData(HttpServletResponse rep) throws Exception;
	public void generateExcelReportAllData(HttpServletResponse rep) throws Exception;
	

}
