package coms309.tarp.Backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.tarp.Backend.repository.*;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherrepo;
	
	public Teacher findById(int id) {
		return teacherrepo.findById(id);
	}
	
	public void deleteById(int id) {
		teacherrepo.deleteById(id);
	}
	
}