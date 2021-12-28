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

@RestController
@Service
@Scope//(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ClassController {

	@Autowired
	ClassRepository classRepository;
	
	/*================================================================*
	 *						GET MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Gets list of all classes
     * @return list of all classes
     */
	@GetMapping("classes/getList")
	List<Classes> getClassList(){
		return classRepository.findAll();
	}
	
	/**
     * Checks if a class already exists
     * @param className Name of class
     * @param classSect Section of class
     * @return list of classes that contain that name and section
     */
	//Made By Austin
	@GetMapping("classes/checkExist/{className}/{classSect}")
	List<Classes> checkClassExist(@PathVariable("className") String className, @PathVariable("classSect") String classSect) {
		List<Classes> classList = classRepository.findAll();
		int numClass = classList.size();
		for(int i = 0; i < numClass; i++){
            if(classList.get(i).getClassName().equalsIgnoreCase(className) && classList.get(i).getClassSection().equalsIgnoreCase(classSect)){
                System.out.println("Class & Section exist");	
            	return classList;
            }
        }
		classList.clear();
		System.out.println("Class & Section don't exist");
        return classList;
	}
	
	/**
     * Gets the ID of a class in database
     * @param className Name of class
     * @param classSection Section of class
     * @return ID of class based on name and section
     */
	@GetMapping("classes/className/{className}/classSection/{classSection}")
	int getClassByNameAndSection(@PathVariable String className, @PathVariable String classSection) {
		int id = findClass(className, classSection);
		return id;
	}
	


/*
	@GetMapping("class/{classID}/enrolled")
	List<Student> getStudentsEnrolled(@PathVariable int classID) {
		Classes course = classRepository.findById(classID);
		if(course == null) {
			return null;
		}
		
		return course.getStudents();
	}
*/
	
	
	/*================================================================*
	 *						POST MAPPINGS							  *
	 *================================================================*/
	
	/**
     * Reduces the list of classes based on a searched class
     * @param searchClass Class object to be searched
     * @return reduced list of classes based on the searched c
     */
	//Made By Austin
	@PostMapping("classes/reduceList")
	List<Classes> reducedList(@RequestBody Classes searchClass) {
		List<Classes> classes = classRepository.findAll(); 
		System.out.println(classes.size() + " total classes");
		int numClass = classes.size();
		for(int i = 0; i < numClass; i++) {
			if(!(searchClass.getClassName().equalsIgnoreCase(classes.get(i).getClassName()))) {
				classes.remove(classes.get(i));
				i--;
				numClass = classes.size();
			}
		}
		System.out.println(classes.size() + " classes found");
		return classes;
	}
	
	/**
     * Adds a new class to database
     * @param newClass New class object to be added
     * @return class object that was added
     */
	@PostMapping("class/add")
	Classes addClassToList(@RequestBody Classes newClass) {
		classRepository.save(newClass);
		System.out.println("New Class created/added to list");
		return newClass;
	}
	
	
	
	
	@PutMapping("class/delete/{name}/{section}")
	String deleteFromList(@PathVariable String name, @PathVariable String section) {
		int id = findClass(name, section);
		unenrollAll(id);
		classRepository.deleteById(id);
		System.out.println("Class Deleted");
		return "success";
	}
	
	private void unenrollAll(int id) {
		Classes course = classRepository.findById(id);
		List<Student> students = course.getStudents();
		Teacher teacher = course.getTeacher();
		
		// Remove this class from each students class list
		for(int i = 0; i < students.size(); i++) {
			List<Classes> courses = students.get(i).getCourses();
			for (int j = 0; j < courses.size(); j++) {
				if (courses.get(j).getId() == course.getId()) {
					courses.remove(j);
				}
			}
		}
		
		// Remove this class from the teacher's class list
		List<Classes> courses = teacher.getCourses();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId() == course.getId()) {
				courses.remove(i);
			}
		}
		
		course.removeStudentList();
		course.removeTeacher();
		classRepository.save(course);
	}

	
	private int findClass(String className, String classSection) {
		
		List<Classes> courses = classRepository.findAll();
		for(int j = 0; j < 30; j++) {
			for (int i = 0; i < courses.size(); i++) {
				if (courses.get(i).getClassName().equalsIgnoreCase(className) && courses.get(i).getClassSection().equalsIgnoreCase(classSection)) {
					int id = courses.get(i).getId();
					System.out.println(className + " section " + classSection + " found ID: " + id);
					return id;
				}
			}
		}
		System.out.println(className + " section " + classSection + " not found");
		
		return 0;
		}
/*	
	//Made By Austin
	@PostMapping("classes/teachList/{ID}")
	List<Classes> getTeachClassList(@PathVariable int ID){
		Classes newClass = new Classes();
		newClass.setTeachID(ID);
		List<Classes> classes = classRepository.findAll(); 
		int numClass = classes.size();
		for(int i = 0; i < numClass; i++) {
			if((newClass.getTeachID() != (classes.get(i).getTeachID()))) {
				classes.remove(classes.get(i));
				i--;
				numClass = classes.size();
			}
		}
		return classes;
		
	}
*/
/*
	@PostMapping("class/post/{n}/{s}/{t}/{d}/{p}")
	Classes PostClassByPath(@PathVariable String n, @PathVariable String s, @PathVariable String t, @PathVariable String d) {
		Classes newClass = new Classes();
		
		newClass.setClassName(n);
		newClass.setClassSection(s);
		newClass.setClassTime(t);
		newClass.setClassDescription(d);
		
		classRepository.save(newClass);
		return newClass;
	}
*/
	/*================================================================*
	 *						DELETE MAPPINGS							  *
	 *================================================================*/
/*	
	@DeleteMapping("class/delete/{classID}")
	String deleteTeacher(@PathVariable int classID) {
		classRepository.deleteById(classID);
		return "success";
	}
*/
	
}