package com.nit.service;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nit.entity.CourseDetails;
import com.nit.model.SearchInputs;
import com.nit.model.SearchResults;
import com.nit.repository.ICourseDetailsRepository;

import jakarta.servlet.http.HttpServletResponse;
@Service
public class CourseMgmtServiceImpl implements ICourseMgmtService{

	@Autowired
	private ICourseDetailsRepository courseRepository;
	@Override
	public List<String> showAllCourseCategories() {
		
		List<String> allCourseCategories = courseRepository.getAllCourseCategories();
		return allCourseCategories;
	}

	@Override
	public Set<String> showAllTrainingModes() {
		Set<String> allTrainingModes = courseRepository.getAllTrainingModes();
		return allTrainingModes;
	}

	@Override
	public Set<String> showAllFaculties() {
		Set<String> allFacultyName = courseRepository.getAllFacultyName();
		return allFacultyName;
	}

	@Override
	public List<SearchResults> showCourseByFilters(SearchInputs inputs) {
		CourseDetails entity=new CourseDetails();
		String category=inputs.getCourseCategory();
		if(StringUtils.hasLength(category)) {
			entity.setCourseCategory(category);
		}
		String facultyName=inputs.getFacultyName();
		if(StringUtils.hasLength(facultyName)) {
			entity.setFacultyName(facultyName);
		}
		String trainingMode=inputs.getTrainingMode();
		if(StringUtils.hasLength(trainingMode)) {
			entity.setTrainingMode(trainingMode);
		}
		LocalDateTime startDate=inputs.getStartOn();
		if(ObjectUtils.isEmpty(startDate)) {
			entity.setStartDate(startDate);
		}
		System.out.println(entity);
		Example<CourseDetails> example = Example.of(entity);
		List<CourseDetails> list = courseRepository.findAll(example);
		
		List<SearchResults> listResults=new ArrayList<>();
		
		list.forEach(course->{
			SearchResults result=new SearchResults();
			BeanUtils.copyProperties(course,result);
			listResults.add(result);
		});
		return listResults;
	}

	@Override
	public void generatePdfReport(SearchInputs inputs, HttpServletResponse rep) throws Exception {
		List<SearchResults> listResults = showCourseByFilters(inputs);
		
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, rep.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);font.setColor(Color.CYAN);
		Paragraph paragraph=new Paragraph("Search report of courses",font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		
		PdfPTable table=new PdfPTable(11);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f});
		table.setSpacingBefore(2.0f);
		
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(10);
		Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		cellFont.setColor(Color.BLACK);
		
		cell.setPhrase(new Phrase("courseId",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("CourseName",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Category",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("FacultyName",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Location",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("courseFee",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Status",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Training Mode",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Name",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Contact",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Start Date",cellFont));
		table.addCell(cell);
		
		listResults.forEach(result->{
			table.addCell(String.valueOf(result.getCourseId()));
			table.addCell(result.getCourseName());
			table.addCell(result.getCourseCategory());
			table.addCell(result.getFacultyName());
			table.addCell(result.getLocation());
			table.addCell(String.valueOf(result.getCourseFee()));
			table.addCell(result.getCourseStatus());
			table.addCell(result.getTrainingMode());
			table.addCell(result.getAdminName());
			table.addCell(String.valueOf(result.getAdminContact()));
			table.addCell(result.getStartDate().toString());
			
		});
		document.add(table);
		document.close();
		
	}

	@Override
	public void generateExcelReport(SearchInputs inputs, HttpServletResponse rep) throws Exception {
		List<SearchResults> listResults = showCourseByFilters(inputs);
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet1=workbook.createSheet("CourseDetails");
		HSSFRow headerRow=sheet1.createRow(0);
		headerRow.createCell(0).setCellValue("CourseId");
		headerRow.createCell(1).setCellValue("CourseName");
		headerRow.createCell(2).setCellValue("CourseCategory");
		headerRow.createCell(3).setCellValue("Location");
		headerRow.createCell(4).setCellValue("FacultyName");
		headerRow.createCell(5).setCellValue("CourseFee");
		headerRow.createCell(6).setCellValue("AdminName");
		headerRow.createCell(7).setCellValue("AdminContact");
		headerRow.createCell(8).setCellValue("TrainingMode");
		headerRow.createCell(9).setCellValue("StartDate");
		headerRow.createCell(10).setCellValue("CourseStatus");
		int i=1;
		for(SearchResults result:listResults){
			HSSFRow dataRow=sheet1.createRow(i);
			dataRow.createCell(0).setCellValue(result.getCourseId());
			dataRow.createCell(1).setCellValue(result.getCourseName());
			dataRow.createCell(2).setCellValue(result.getCourseCategory());
			dataRow.createCell(3).setCellValue(result.getLocation());
			dataRow.createCell(4).setCellValue(result.getFacultyName());
			dataRow.createCell(5).setCellValue(result.getCourseFee());
			dataRow.createCell(6).setCellValue(result.getAdminName());
			dataRow.createCell(7).setCellValue(result.getAdminContact());
			dataRow.createCell(8).setCellValue(result.getTrainingMode());
			dataRow.createCell(9).setCellValue(result.getStartDate());
			dataRow.createCell(10).setCellValue(result.getCourseStatus());
			i++;
		}
		workbook.write(rep.getOutputStream());
		workbook.close();
	}

	@Override
	public void generatePdfReportAllData(HttpServletResponse rep) throws Exception {
		List<CourseDetails> list = courseRepository.findAll();
		
		List<SearchResults> listResults=new ArrayList<>();
		list.forEach(course->{
			SearchResults result=new SearchResults();
			BeanUtils.copyProperties(course, result);
			listResults.add(result);
		});
		
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, rep.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);font.setColor(Color.CYAN);
		Paragraph paragraph=new Paragraph("Search report of courses",font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		
		PdfPTable table=new PdfPTable(10);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f});
		table.setSpacingBefore(2.0f);
		
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(5);
		Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		cellFont.setColor(Color.BLACK);
		
		cell.setPhrase(new Phrase("courseId",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("CourseName",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Category",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("FacultyName",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Location",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("courseFee",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Status",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Training Mode",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Name",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Contact",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Start Date",cellFont));
		table.addCell(cell);
		
		listResults.forEach(result->{
			table.addCell(String.valueOf(result.getCourseId()));
			table.addCell(result.getCourseName());
			table.addCell(result.getCourseCategory());
			table.addCell(result.getFacultyName());
			table.addCell(result.getLocation());
			table.addCell(String.valueOf(result.getCourseFee()));
			table.addCell(result.getCourseStatus());
			table.addCell(result.getTrainingMode());
			table.addCell(result.getAdminName());
			table.addCell(String.valueOf(result.getAdminContact()));
			table.addCell(result.getStartDate().toString());
			
		});
		document.add(table);
		document.close();
		
	}

	@Override
	public void generateExcelReportAllData(HttpServletResponse rep) throws Exception {
        List<CourseDetails> list = courseRepository.findAll();
		
		List<SearchResults> listResults=new ArrayList<>();
		list.forEach(course->{
			SearchResults result=new SearchResults();
			BeanUtils.copyProperties(course, result);
			listResults.add(result);
		});
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet1=workbook.createSheet("CourseDetails");
		HSSFRow headerRow=sheet1.createRow(0);
		headerRow.createCell(0).setCellValue("CourseId");
		headerRow.createCell(1).setCellValue("CourseName");
		headerRow.createCell(2).setCellValue("CourseCategory");
		headerRow.createCell(3).setCellValue("Location");
		headerRow.createCell(4).setCellValue("FacultyName");
		headerRow.createCell(5).setCellValue("CourseFee");
		headerRow.createCell(6).setCellValue("AdminName");
		headerRow.createCell(7).setCellValue("AdminContact");
		headerRow.createCell(8).setCellValue("TrainingMode");
		headerRow.createCell(9).setCellValue("StartDate");
		headerRow.createCell(10).setCellValue("CourseStatus");
		int i=1;
		for(SearchResults result:listResults){
			HSSFRow dataRow=sheet1.createRow(i);
			dataRow.createCell(0).setCellValue(result.getCourseId());
			dataRow.createCell(1).setCellValue(result.getCourseName());
			dataRow.createCell(2).setCellValue(result.getCourseCategory());
			dataRow.createCell(3).setCellValue(result.getLocation());
			dataRow.createCell(4).setCellValue(result.getFacultyName());
			dataRow.createCell(5).setCellValue(result.getCourseFee());
			dataRow.createCell(6).setCellValue(result.getAdminName());
			dataRow.createCell(7).setCellValue(result.getAdminContact());
			dataRow.createCell(8).setCellValue(result.getTrainingMode());
			dataRow.createCell(9).setCellValue(result.getStartDate());
			dataRow.createCell(10).setCellValue(result.getCourseStatus());
			i++;
		}
		workbook.write(rep.getOutputStream());
		workbook.close();
		
	}

	@Override
	public String saveCourse(CourseDetails course) {
		CourseDetails save = courseRepository.save(course);
		return "saved"+save.getCourseId();
	}

}
