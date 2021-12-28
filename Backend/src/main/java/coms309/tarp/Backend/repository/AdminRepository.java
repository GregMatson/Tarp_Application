package coms309.tarp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.tarp.Backend.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	Admin findById(int id);
	void deleteById(int id);
}
