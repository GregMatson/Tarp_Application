package coms309.tarp.Backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Creates a new entity and table called "students"
 */
@Entity
@Table(name = "students")
public class Student {

/*
 * 	Generates the columns to be used in the students table
 *  Also determines the type of variable to be used in the column
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stud_id")
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
 * Establishes a relationship with the classes table by creating a separate enrolled_in column and joining together
 * the stud_id and class_id columns in both tables.	
 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "enrolled_in",
				joinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "stud_id"),
				inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName = "class_id"))
	@JsonIgnore
	private List<Classes> courses;
	
	public Student() {
		courses = new ArrayList<>();
	}
	
/*
 * Create a new constructor for the Student object. Each student object created will have its own firstName, lastName,
 * email, username, and password. The object is created when a student first signs up on the Tarp app.
 */
	public Student(String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		courses = new ArrayList<>();
	}

/*
 * The following methods retrieve and set specified information from certain columns of data
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
