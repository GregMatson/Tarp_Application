package coms309.tarp.Backend.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.tarp.Backend.model.Teacher;
import coms309.tarp.Backend.model.Classes;
import coms309.tarp.Backend.model.Student;
import coms309.tarp.Backend.model.Teacher;
import coms309.tarp.Backend.repository.ClassRepository;
import coms309.tarp.Backend.repository.TeacherRepository;

@RestController
@Service
@Scope//(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class TeacherController {

	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;
	
	/*================================================================*
	 *						GET MAPPINGS							  *
	 *================================================================*/

	/**
     * Gets the password of the teacher based on the ID
     * @param userID ID of teacher
     * @return password of the teacher
     */
	@GetMapping("teacher/getPassword/{userID}")
    String getPassword(@PathVariable("userID") int userID) {
		
		String pass = teacherRepository.findById(userID).getPassword();
		System.out.println("Password Found: " + pass);
		return pass;
	}
	
	/**
     * Gets list of classes the teacher is teaching
     * @param teachID ID of teacher
     * @return list of classes being taught by teacher
     */
	@GetMapping("teacher/{teachID}/teaching")
	List<Classes> getClassesBeingTaught(@PathVariable int teachID) {
		Teacher teacher = teacherRepository.findById(teachID);
		if(teacher == null) {
			return null;
		}
		
		return teacher.getCourses();
	}
	
	/**
     * Gets the teacher based off of ID in the database
     * @param userID ID of the teacher
     * @return Teacher object associated with ID
     */
	@GetMapping("teacher/{teachID}")
	Teacher getTeacherByID(@PathVariable("teachID") int userID){
		
		Teacher teach = teacherRepository.findById(userID);
		System.out.println("Found teacher, " + teach.getLastName() + " | " + teach.getUsername());
		return teach;
	}
	
	/**
     * Gets the class ID to remove from the teacher class list
     * @param userID Id of teacher
     * @param className Name of class to remove
     * @param classSection Name of section to remove
     * @return ID of class to be removed
     */
	//Made By Austin
	@GetMapping("teacher/findClass/{teachID}/{className}/{classSection}")
    Integer findTeacherClass(@PathVariable("teachID") int teachID, @PathVariable("className") String className, @PathVariable("classSection") String classSection) {
		Teacher teacher = teacherRepository.findById(teachID);
		if(teacher == null) {
			return null;
		}
		List<Classes> courses = teacher.getCourses();
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getClassName().equalsIgnoreCase(className) && courses.get(i).getClassSection().equalsIgnoreCase(classSection)) {
				System.out.println("Class found in teacher list");
				return courses.get(i).getId();
			}
		}
		System.out.println("Class NOT found in teacher list");
		return 0;
    }


	@GetMapping("teacher/all")
	List<Teacher> GetAllTeachers() {
		return teacherRepository.findAll();
	}

    @GetMapping("teacher/exists/{username}")
    List<Teacher> checkTeacherExist(@PathVariable("username") String username){
    	List<Teacher> teachList = teacherRepository.findAll();
		int numClass = teachList.size();
		for(int i = 0; i < numClass; i++){
            if(teachList.get(i).getUsername().equalsIgnoreCase(username)){
                System.out.println("Teacher exists");	
            	return teachList;
            }
        }
		teachList.clear();
		System.out.println("Teacher doesn't exist");
        return teachList;
    }
	
	/*================================================================*
	 *						POST MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Checks if username and password match that of the user that is logging in
     * @param teacherCheck Teacher object that contains a username and password
     * @return Teacher object that contains that username and password, otherwise returns null
     */
	@PostMapping("teacher/login")
	Teacher loginValidation(@RequestBody Teacher loginAttempt) {
		List<Teacher> teachers = teacherRepository.findAll();
		int numTeach = teachers.size();
		for (int i = 0; i < numTeach; i++) {
			if (loginAttempt.getUsername().equals(teachers.get(i).getUsername())) {
				if (loginAttempt.getPassword().equals(teachers.get(i).getPassword())) {
					System.out.println("Found Teachers");
					return teachers.get(i);
				}
			}
		}
		
		System.out.println("Teachers Not Found");

		return null;
	}

	/**
     * Adds a new teacher object to database
     * @param teacher Teacher object to be added
     * @return teacher object that was added
     */
	@PostMapping("teacher/add")
	Teacher addTeacher(@RequestBody Teacher teacher) {
		teacherRepository.save(teacher);
		System.out.println(teacher.getUsername());
		return teacher;
	}
	
	/**
     * Finds list of teachers based on a username
     * @param username username to look for
     * @return list of teachers with that username
     */
	//Made By Austin
	@PostMapping("teacher/findUserName/{username}")
    List<Teacher> findTeaUserName(@PathVariable("username") String username){
		List<Teacher> teachers = teacherRepository.findAll();
		for(int i = 0; i < teachers.size(); i++){
            if(teachers.get(i).getUsername().equals(username)){
                return teachers;
            }
        }
		teachers.clear();
        return teachers;
	}

/*	
	@PostMapping("teacher/post/{f}/{l}/{e}/{u}/{p}")
	Teacher PostTeacherByPath(@PathVariable String f, @PathVariable String l, @PathVariable String e, @PathVariable String u,@PathVariable String p) {
		Teacher newTeacher = new Teacher();
		
		newTeacher.setFirstName(f);
		newTeacher.setLastName(l);
		newTeacher.setEmail(e);
		newTeacher.setUsername(u);
		newTeacher.setPassword(p);
		
		teacherRepository.save(newTeacher);
		return newTeacher;
	}
*/
	

	/*================================================================*
	 *						PUT MAPPINGS							  *
	 *================================================================*/
	
	/**
     * creates a relationship between the student and class they enroll in
     * @param teachID ID of teacher
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
	@PutMapping("teacher/{teachID}/class/{classID}")
	String assignCourseToTeacher(@PathVariable int teachID, @PathVariable int classID) {
		Teacher teacher = teacherRepository.findById(teachID);
		Classes course = classRepository.findById(classID);
		if (teacher == null || course == null) {
			System.out.println("Class " + classID + " not related to teacher " + teachID);
			return "failure";
		}
		
		teacher.addCourses(course);
		course.setTeacher(teacher);
		teacherRepository.save(teacher);
		classRepository.save(course);
		
		System.out.println("Class " + classID + " related to teacher " + teachID + "\n");
		return "success";
	}
	
	/**
     * removes student from class and removes relationship between that student and class
     * @param userID ID of teacher
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
	//Made by Austin
		@PutMapping("teacher/removeClass/{teachID}/{classID}")
	    String unassignTeacherFromCourse(@PathVariable("teachID") int teachID, @PathVariable("classID") int classID) {
			Teacher teacher = teacherRepository.findById(teachID);
			Classes course = classRepository.findById(classID);
			
			teacher.removeCourses(course);
			//course.removeTeacher(teacher);
			teacherRepository.save(teacher);
			//classRepository.save(course);
			
			System.out.println("Class " + classID + " Removed from list of Teacher " + teachID);
			return "success";
		}
		
		/**
	     * Edits the info for the teacher in the database
	     * @param userID Id of teacher
	     * @param first First name of teacher
	     * @param last last name of teacher
	     * @param user username of teacher
	     * @param email email of teacher
	     * @param passNew new password of teacher
	     * @return string saying whether it was a success or failure
	     */
		//Made By Austin
		@PutMapping("teacher/editProfile/{userID}/{first}/{last}/{user}/{email}/{passNew}")
	    String editTeacherProfile(@PathVariable("userID")int userID, @PathVariable("first")String first, @PathVariable("last")String last, 
	                                    @PathVariable("user")String user, @PathVariable("email")String email, @PathVariable("passNew")String passNew) {
			
			Teacher teacher = teacherRepository.findById(userID);
			
			if(!(first.equals(teacher.getFirstName()))) {
				teacher.setFirstName(first);
				System.out.println("Teacher " + userID + " First Name changed");
			}
			if(!(last.equals(teacher.getLastName()))) {
				teacher.setLastName(last);
				System.out.println("Teacher " + userID + " Last Name changed");
			}
			if(!(user.equals(teacher.getUsername()))) {
				teacher.setUsername(user);
				System.out.println("Teacher " + userID + " Username changed");
			}
			if(!(email.equals(teacher.getEmail()))) {
				teacher.setEmail(email);
				System.out.println("Teacher " + userID + " email changed");
			}
			if(!(passNew.equals("----"))) {
				teacher.setPassword(passNew);
				System.out.println("Teacher " + userID + " Password changed");
			}
			
			teacherRepository.save(teacher);
			System.out.println("Teacher " + userID + " profile updated");
			return "success";
		}
		
		
		@PutMapping("teacher/delete/{username}")
	    String deleteTeacher(@PathVariable("username")String username){
	    	int id = getIdByUsername(username);
	    	Teacher teacher = teacherRepository.findById(id);
			List<Classes> courses = teacher.getCourses();
			for(int i = 0; i < courses.size(); i++) {
				
				List<Student> currStudents = courses.get(i).getStudents();
				for(int j = 0; j < currStudents.size(); j++) {
					currStudents.get(j).removeCourses(courses.get(i));
					courses.get(i).removeStudent(currStudents.get(j));
				}
				int courseID = courses.get(i).getId();
				teacher.removeCourses(courses.get(i));
				classRepository.deleteById(courseID);
				System.out.println("Class " + courseID + " Deleted");
			}
			teacherRepository.deleteById(id);
			System.out.println("Teacher Deleted");
			return "success";
	    }

	    
	    
		private int getIdByUsername(String username) {
			List<Teacher> teachers = teacherRepository.findAll();
			for (int i = 0; i < teachers.size(); i++) {
				if (teachers.get(i).getUsername().equalsIgnoreCase(username)) {
					int id = (int) teachers.get(i).getId();
					System.out.println(username + " found, ID: " + id);
					return id;
				}
			}
			System.out.println("User not found");
			return 0;
		}
		
	/*================================================================*
	 *						DELETE MAPPINGS							  *
	 *================================================================*/
/*
	@DeleteMapping("teacher/delete/{teachID}")
	String deleteTeacher(@PathVariable int teachID) {
		teacherRepository.deleteById(teachID);
		return "success";
	}
*/
		
}

