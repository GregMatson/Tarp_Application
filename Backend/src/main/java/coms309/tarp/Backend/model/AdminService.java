package coms309.tarp.Backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.tarp.Backend.repository.*;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminrepo;
	
	public Admin findById(int id) {
		return adminrepo.findById(id);
	}
	
	public void deleteById(int id) {
		adminrepo.deleteById(id);
	}
	
}