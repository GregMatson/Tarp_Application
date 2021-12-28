package coms309.tarp.Backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import coms309.tarp.Backend.model.Student;

/*
 * Creates a repository for the Student object. The two methods (findById and deleteById) allow the program to find specific rows of data
 * based on the unique table id that's set when creating each row
 */
@Service
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findById(int id);
	void deleteById(int id);
}