package coms309.tarp.Backend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import coms309.tarp.Backend.controller.*;
import coms309.tarp.Backend.model.*;
import coms309.tarp.Backend.repository.*;

public class MockitoTests {

	@InjectMocks
	StudentService studentService;
	TeacherService teacherService;
	AdminService adminService;
	
	@Mock
	StudentRepository studentrepo;
	TeacherRepository teacherrepo;
	AdminRepository adminrepo;
	

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getStudentByIdTest() {
		when(studentrepo.findById(1)).thenReturn(new Student("student", "test", "student@gmail.com", "student", "password")); //GREG
		
		Student student = studentService.findById(1);
		
		assertEquals("student", student.getFirstName());
		assertEquals("test", student.getFirstName());
		assertEquals("student@gmail.com", student.getFirstName());
		assertEquals("student", student.getFirstName());
		assertEquals("password", student.getFirstName());
	}
	
	@Test
	public void getTeacherByIdTest() {
		when(teacherrepo.findById(1)).thenReturn(new Teacher("milf", "teeacher", "gmail@gmail.com", "teacher", "password")); //GREG
		
		Teacher teacher = teacherService.findById(1);
		
		assertEquals("student", teacher.getFirstName());
		assertEquals("test", teacher.getFirstName());
		assertEquals("student@gmail.com", teacher.getFirstName());
		assertEquals("student", teacher.getFirstName());
		assertEquals("password", teacher.getFirstName());
	}
	
	@Test
	public void getAdminByIdTest() {
		when(adminrepo.findById(1)).thenReturn(new Admin("admin", "test", "email@gmail.com", "admin", "password"));		//GREG
		
		Admin admin = adminService.findById(1);
		
		assertEquals("student", admin.getFirstName());
		assertEquals("test", admin.getFirstName());
		assertEquals("student@gmail.com", admin.getFirstName());
		assertEquals("student", admin.getFirstName());
		assertEquals("password", admin.getFirstName());
	}
	
}
