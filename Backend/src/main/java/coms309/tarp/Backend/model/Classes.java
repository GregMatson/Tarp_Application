package coms309.tarp.Backend.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Creates a new entity and table called "classes"
 */
@Entity
@Table(name = "classes")
public class Classes {

/*
 * Generates the columns to be used in the classes table
 * Also determines the type of variable to be used in the column	
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	private int id;
	
	@Column(name = "class_name")
	private String className;
	
	@Column(name = "section")
	private String classSection;
	
	@Column(name = "time")
	private String classTime;
	
	@Column(name = "description")
	private String classDescription;
	
	@Column(name = "teach_Name")
	private String teacherName;
	
	@Column(name = "temp_teach_id")
	private int teachId;

/*
 * Establishes relationships with the students and teachers tables by creating a teach_id and courses column	
 */
	@ManyToOne
	@JoinColumn(name = "teach_id")
	private Teacher teacher;
	
	@ManyToMany(mappedBy = "courses")
	@JsonIgnore
	private List<Student> students;

	public Classes() {
		students = new ArrayList<>();
	}

/*
 * Creates new constructors for the Classes object. The first one is used to display the information about a certain class from a teacher's perspective
 * The second constructor is used to display the class's information from the student's perspective.
 * The object is created when a teacher creates the class with the specified information.
 */
	public Classes(String className, String classSection, String classTime, String classDescription) {
		this.className = className;
		this.classSection = classSection;
		this.classTime = classTime;
		this.classDescription = classDescription;
		students = new ArrayList<>();
	}
	
	public Classes(String className, String classTime, String classSection, String classDescription, String teacherName, int teachID) {
        this.className = className;
        this.classTime = classTime;
        this.classSection = classSection;
        this.classDescription = classDescription;
        this.teacherName = teacherName;
        this.id = teachID;
	}
	
/*
 * The following methods retrieve and set specified information from certain columns of data.
 */
	public int getId() { return id; }
	
	public String getClassName() { return className; }
	
	public void setClassName(String className) { this.className = className; }
	
	public String getClassSection() { return classSection; }
	
	public void setClassSection(String classSection) { this.classSection = classSection; }
	
	public String getClassTime() { return classTime; }
	
	public void setClassTime(String classTime) { this.classTime = classTime; }
	
	public String getClassDescription() { return classDescription; }
	
	public void setClassDescription(String classDescription) { this.classDescription = classDescription; }
	
	public void setTeacher(Teacher teacher) { this.teacher = teacher; }
	
	public Teacher getTeacher() { return teacher; }
	
	public List<Student> getStudents() { return students; }
	
	public void removeStudentList() { students = null; }
	
	public void setStudents(List<Student> students) { this.students = students; }
	
	public void addStudents(Student student) { this.students.add(student); }
	
	public void removeStudent(Student student) { this.students.remove(student); }
		
	public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
	
	public void removeTeacher() { teacher = null; }
	
	public int getTeachID() { return teachId;}
	
	public void setTeachID(int teachId) { this.teachId = teachId;}
}


