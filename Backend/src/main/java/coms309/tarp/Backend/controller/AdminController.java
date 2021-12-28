package coms309.tarp.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.tarp.Backend.model.Admin;
import coms309.tarp.Backend.model.Teacher;
import coms309.tarp.Backend.model.Student;
import coms309.tarp.Backend.model.Classes;
import coms309.tarp.Backend.repository.AdminRepository;
import coms309.tarp.Backend.repository.TeacherRepository;
import coms309.tarp.Backend.repository.StudentRepository;
import coms309.tarp.Backend.repository.ClassRepository;

@RestController
public class AdminController {

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ClassRepository classRepository;
	
	/*================================================================*
	 *						GET MAPPINGS							  *
	 *================================================================*/

	/**
     * Gets list of all admin users
     * @return list of all admins
     */
	@GetMapping("admin/users/all")
	List<Admin> getAllAdmins(){
		return adminRepository.findAll();
	}	
	
	/**
     * Gets list of all teachers
     * @return list of all teachers
     */
	@GetMapping("admin/teacher/all")
	List<Teacher> getAllTeachers(){
		return teacherRepository.findAll();
	}
	
	/**
     * Gets list of all students
     * @return list of all students
     */
	@GetMapping("admin/student/all")
	List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	/**
     * Gets list of all classes
     * @return list of all classes
     */
	@GetMapping("admin/classes/all")
	List<Classes> getClassList(){
		return classRepository.findAll();
	}
	
	/**
     * Checks if username and password match that of the user that is logging in
     * @param loginAttempt Admin object that contains a username and password
     * @return Admin object that contains that username and password, otherwise returns null
     */
	@PostMapping("admin/login/{user}/{pass}")
    Admin loginValidation(@PathVariable("user") String user, @PathVariable("pass")String pass) {
		List<Admin> admins = adminRepository.findAll();
		int numAdmin = admins.size();
		for (int i = 0; i < numAdmin; i++) {
			if (user.equals(admins.get(i).getUsername())) {
				if (pass.equals(admins.get(i).getPassword())) {
					System.out.println("Found Admin");
					return admins.get(i);
				}
			}
		}

		return null;
	}

	/*================================================================*
	 *						DELETE MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Deletes the specified teacher user based on the given teacher ID
     * @return returns "success" once the deletion is complete
     */
	@DeleteMapping("admin/teacher/delete/{teachID}")
	String deleteTeacher(@PathVariable int teachID) {
		teacherRepository.deleteById(teachID);
		return "success";
	}
	
	/**
     * Deletes the specified student user based on the given student ID
     * @return returns "success" once the deletion is complete
     */
	@DeleteMapping("admin/student/delete/{studID}")
	String deleteStudent(@PathVariable int studID) {
		studentRepository.deleteById(studID);
		return "success";
	}
	
	/**
     * Deletes the specified class based on the given class ID
     * @return returns "success" once the deletion is complete
     */
	@DeleteMapping("admin/classes/delete/{classID}")
	String deleteClass(@PathVariable int classID) {
		classRepository.deleteById(classID);
		return "success";
	}
	
}
