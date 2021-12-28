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

import coms309.tarp.Backend.model.Classes;
import coms309.tarp.Backend.model.Student;
import coms309.tarp.Backend.model.Teacher;
import coms309.tarp.Backend.repository.ClassRepository;
import coms309.tarp.Backend.repository.StudentRepository;

@RestController
@Service
@Scope//(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	ClassRepository classRepository;
	
	/*================================================================*
	 *						GET MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Gets list of all students
     * @return list of all students
     */
	@GetMapping("student/all")
	List<Student> GetAllStudents() {
		return studentRepository.findAll();
	}

	/**
     * Gets the current student info based on their ID
     * @param userID ID of current student
     * @return The student object based off the ID
     */
	@GetMapping("student/{studID}")
	Student getStudentByID(@PathVariable("studID") int userID){
		System.out.println("student " + userID + " found");
		return studentRepository.findById(userID);
	}
	
	/**
     * Gets the password of the student based on the student ID
     * @param userID ID number of student in the database
     * @return the password of the current student
     */
	@GetMapping("student/getPassword/{studID}")
    String getPassword(@PathVariable("studID") int userID) {
		return studentRepository.findById(userID).getPassword();
	}
	
	/**
     * Gets list of classes that the student is enrolled in
     * @param studId ID of student in database
     * @return list of classes that student is enrolled in
     */	
	@GetMapping("student/enrolled/{studId}")
	List<Classes> getStudentsSchedule(@PathVariable int studId) {
		Student student = studentRepository.findById(studId);
		if(student == null) {
			return null;
		}
		
		return student.getCourses();

	}
	
	/**
     * Gets the class ID to remove from the student class list
     * @param userID ID of the student user
     * @param className Name of the class to be removed
     * @param classSection Section of the class to be removed
     * @return ID of class if name and section found, otherwise 0
     */
	//Made By Austin
	@GetMapping("student/findClass/{studID}/{className}/{classSection}")
    Integer findStudentClass(@PathVariable("studID") int studID, @PathVariable("className") String className, @PathVariable("classSection") String classSection) {
		Student student = studentRepository.findById(studID);
		if(student == null) {
			return null;
		}
		List<Classes> courses = student.getCourses();
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getClassName().equalsIgnoreCase(className) && courses.get(i).getClassSection().equalsIgnoreCase(classSection)) {
				System.out.println("Class found in student list");
				return courses.get(i).getId();
			}
		}
		System.out.println("Class NOT found in student list");
		return 0;
    }
	
	 @GetMapping("student/exists/{username}")
	    List<Student> checkStudentExist(@PathVariable("username")String username){
		 List<Student> stuList = studentRepository.findAll();
			int numClass = stuList.size();
			for(int i = 0; i < numClass; i++){
	            if(stuList.get(i).getUsername().equalsIgnoreCase(username)){
	                System.out.println("Students exists");	
	            	return stuList;
	            }
	        }
			stuList.clear();
			System.out.println("Student doesn't exist");
	        return stuList;
	 }
	
	/*================================================================*
	 *						POST MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Checks if username and password match that of the user that is logging in
     * @param loginAttempt Student object that contains a username and password
     * @return Student object that contains that username and password, otherwise returns null
     */
	//Made By Austin
	@PostMapping("student/login")
	Student loginValidation(@RequestBody Student loginAttempt) {
		List<Student> students = studentRepository.findAll();
		int numStuds = students.size();
		for (int i = 0; i < numStuds; i++) {
			if (loginAttempt.getUsername().equals(students.get(i).getUsername())) {
				if (loginAttempt.getPassword().equals(students.get(i).getPassword())) {
					return students.get(i);
				}
			}
		}

		return null;
	}

	/**
     * Adds a new student to the database
     * @param student student object to add
     * @return student object that was added
     */
	@PostMapping("student/add")
	Student addStudent(@RequestBody Student newStudent) {
		studentRepository.save(newStudent);
		System.out.println(newStudent.getUsername());
		return newStudent;
	}
	
	/**
     * Finds Student based on username
     * @param username username of the student to be found
     * @return list of students that have that username
     */
	//Made By Austin
	@PostMapping("student/findUserName/{username}")
	List<Student> findStuUserName(@PathVariable String username) {
		List<Student> students = studentRepository.findAll();
		for(int i = 0; i < students.size(); i++){
            if(students.get(i).getUsername().equals(username)){
                return students;
            }
        }
		students.clear();
        return students;
	}
	
/*
	@PostMapping("student/post/{f}/{l}/{e}/{u}/{p}")
	Student PostStudentByPath(@PathVariable String f, @PathVariable String l, @PathVariable String e, @PathVariable String u,@PathVariable String p) {
		Student newStudent = new Student();
		
		newStudent.setFirstName(f);
		newStudent.setLastName(l);
		newStudent.setEmail(e);
		newStudent.setUsername(u);
		newStudent.setPassword(p);
		
		studentRepository.save(newStudent);
		return newStudent;
	}
*/
/*	
	@PostMapping("student/checkUsername/{username}")
	Boolean checkStudentUser(@PathVariable("username") String username) {
		List<Student> students = studentRepository.findAll();
		int numStuds = students.size();
		for (int i = 0; i < numStuds; i++) {
			if (students.get(i).getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
*/
	
	/*================================================================*
	 *						PUT MAPPINGS							  *
	 *================================================================*/
	
	/**
     * creates a relationship between the student and class they enroll in
     * @param studID ID of student
     * @param classID Id of class
     * @return string saying whether it was a success or failure
     */
	@PutMapping("student/{studID}/class/{classID}")
	String assignCourseToStudent(@PathVariable int studID, @PathVariable int classID) {
		Student student = studentRepository.findById(studID);
		Classes course = classRepository.findById(classID);
		if (student == null || course == null) {
			System.out.println("Student " + studID + " not enrolled in class " + classID);
			return "failure";
		}
		
		List<Classes> enrolledIn = student.getCourses();
		for (int i = 0; i < enrolledIn.size(); i++) {
			if(enrolledIn.get(i).getId() == course.getId()) {
				System.out.println("Student " + studID + " already enrolled in class " + classID);
				return "failure";
			}
		}
		
		student.addCourses(course);
		course.addStudents(student);
		studentRepository.save(student);
		classRepository.save(course);
		
		System.out.println("Student " + studID + " enrolled in class " + classID);
		return "success";
	}
	
	/**
     * removes student from class and removes relationship between that student and class
     * @param userID ID of student
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
	//Made by Austin
	@PutMapping("student/removeClass/{studID}/{classID}")
    String unassignStudentFromCourse(@PathVariable("studID") int studID, @PathVariable("classID") int classID) {
		Student student = studentRepository.findById(studID);
		Classes course = classRepository.findById(classID);
		
		student.removeCourses(course);
		course.removeStudent(student);
		studentRepository.save(student);
		classRepository.save(course);
		
		System.out.println("Student " + studID + " unenrolled in class " + classID);
		return "success";
	}
	
	/**
     * Edits the info for the student in the database
     * @param studID Id of student
     * @param first First name of student
     * @param last last name of student
     * @param user username of student
     * @param email email of student
     * @param passNew new password of student
     * @return string saying whether it was a success or failure
     */
	//Made By Austin
	@PutMapping("student/editProfile/{studID}/{first}/{last}/{user}/{email}/{passNew}")
    String editStudentProfile(@PathVariable("studID")int studID, @PathVariable("first")String first, @PathVariable("last")String last, 
                                    @PathVariable("user")String user, @PathVariable("email")String email, @PathVariable("passNew")String passNew) {
		
		Student student = studentRepository.findById(studID);
		System.out.println("Editing student " + studID);
		
		if(!(first.equals(student.getFirstName()))) {
			student.setFirstName(first);
			System.out.println("Student " + studID + " First Name changed");
		}
		if(!(last.equals(student.getLastName()))) {
			student.setLastName(last);
			System.out.println("Student " + studID + " Last Name changed");
		}
		if(!(user.equals(student.getUsername()))) {
			student.setUsername(user);
			System.out.println("Student " + studID + " Username changed");
		}
		if(!(email.equals(student.getEmail()))) {
			student.setEmail(email);
			System.out.println("Student " + studID + " email changed");
		}
		if(!(passNew.equals("----"))) {
			student.setPassword(passNew);
			System.out.println("Student " + studID + " Password changed");
		}
		
		studentRepository.save(student);
		System.out.println("Student " + studID + " profile updated");
		return "success";
	}

    @PutMapping("student/delete/{username}")
    String deleteStudent(@PathVariable("username")String username){
    	int id = getIdByUsername(username);
    	Student student = studentRepository.findById(id);
		List<Classes> courses = student.getCourses();
		for(int i = 0; i < courses.size(); i++) {
			student.removeCourses(courses.get(i));
			courses.get(i).removeStudent(student);
		}
		studentRepository.save(student);
		studentRepository.deleteById(id);
		System.out.println("Student Deleted");
		return "success";
    }

    
    
	private int getIdByUsername(String username) {
		List<Student> students = studentRepository.findAll();
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getUsername().equalsIgnoreCase(username)) {
				int id = (int) students.get(i).getId();
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
	@DeleteMapping("student/delete/{studID}")
	String deleteStudent(@PathVariable int studID) {
		studentRepository.deleteById(studID);
		return "success";
	}
*/
	
	
}
