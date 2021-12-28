package coms309.tarp.Backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Creates a new entity and table called "admin"
 */
@Entity
@Table(name = "admin")
public class Admin {

/*
 * Generates the columns to be used in the admin table
 * Also determines the type of variable to be used in the column
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
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
 * Creates a new constructor for the Admin object. Each admin will have its own first name, last name, email
 * username, and password, just like a student and teacher user. A new admin user is created by directly inputting the
 * information into the database by hand, rather than "signing-up" to be an admin
 */
	
	public Admin() {
		
	}
	
	public Admin(String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
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
	
	
}
