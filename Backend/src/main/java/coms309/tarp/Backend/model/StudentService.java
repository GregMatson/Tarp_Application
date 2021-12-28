package coms309.tarp.Backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.tarp.Backend.repository.*;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentrepo;
	
	public Student findById(int id) {
		return studentrepo.findById(id);
	}
	
	public void deleteById(int id) {
		studentrepo.deleteById(id);
	}
	
}
