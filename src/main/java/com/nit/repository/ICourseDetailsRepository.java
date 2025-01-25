package com.nit.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nit.entity.CourseDetails;
@Repository
public interface ICourseDetailsRepository extends JpaRepository<CourseDetails,Integer>{

	@Query("SELECT c.courseCategory FROM CourseDetails c")
	public List<String> getAllCourseCategories();
	@Query("SELECT c.trainingMode FROM CourseDetails c")
	public Set<String> getAllTrainingModes();
	@Query("SELECT c.facultyName FROM CourseDetails c")
	public Set<String> getAllFacultyName();
}
