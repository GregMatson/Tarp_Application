package coms309.tarp.Backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Creates a new entity and table called "teachers"
 */
@Entity
@Table(name = "teachers")
public class Teacher {

/*
 * Generates the columns to be used in the classes table
 * Also determines the type of variable to be used in the column
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teach_id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

/*
 * Establishes a relationship with the Classes table. In this case, it establishes that one teacher can have many different classes
 * It does this by creating a courses Array List for every teacher object that is created.
 */
	@OneToMany
	@JsonIgnore
	private List<Classes> courses;
	
	public Teacher() {
		courses = new ArrayList<>();
	}

/*
 * Creates a new constructor for the Teacher object. Each Teacher will have its own firstName, lastName, email,
 * username, and password, just like a student. The object is created when a teacher fist signs up on the Tarp app.
 */
	public Teacher(String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		courses = new ArrayList<>();
	}

/*
 * The following methods retrieve and set specified information from certain columns of data.	
 */
	
	public long getId() { return id; }
		
	public String getFirstName() { return firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getUsername() { return username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public List<Classes> getCourses() { return courses; }
	
	public void setCourses(List<Classes> courses) { this.courses = courses; }
	
	public void addCourses(Classes course) { this.courses.add(course); }
	
	public void removeCourses(Classes course) { this.courses.remove(course); }
}
