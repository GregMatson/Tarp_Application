package com.example.Tarp.Api;

import com.example.Tarp.model.Classes;
import com.example.Tarp.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentApi {

    /*================================================================*
     *						GET MAPPINGS							  *
     *================================================================*/

    /**
     * Gets the password of the student based on the student ID
     * @param userID ID number of student in the database
     * @return the password of the current student
     */
    @GET("student/getPassword/{studID}")
    Call<String> getPassword(@Path("studID") int userID);

    /**
     * Gets list of all students
     * @return list of all students
     */
    @GET("student/all")
    Call<List<Student>> GetAllStudents();

    /**
     * Gets list of classes that the student is enrolled in
     * @param studId ID of student in database
     * @return list of classes that student is enrolled in
     */
    @GET("student/enrolled/{studId}")
    Call<List<Classes>> getStudentsSchedule(@Path("studId") int studId);

    /**
     * Gets the current student info based on their ID
     * @param userID ID of current student
     * @return The student object based off the ID
     */
    @GET("student/{studID}")
    Call<Student> getStudentByID(@Path("studID") int userID);

    /**
     * Gets the class ID to remove from the student class list
     * @param userID ID of the student user
     * @param className Name of the class to be removed
     * @param classSection Section of the class to be removed
     * @return ID of class if name and section found, otherwise 0
     */
    @GET("student/findClass/{studID}/{className}/{classSection}")
    Call<Integer> findStudentClass(@Path("studID") int userID, @Path("className") String className,
                                   @Path("classSection") String classSection);

    /*================================================================*
     *						POST MAPPINGS							  *
     *================================================================*/

    /**
     * Adds anew student to the database
     * @param student student object to add
     * @return student object that was added
     */
    @POST("student/add")
    Call<Student> addStudent(@Body Student student);

    /**
     * Finds Student based on username
     * @param username username of the student to be found
     * @return list of students that have that username
     */
    @POST("student/findUserName/{username}")
    Call<List<Student>> findStuUserName(@Path("username") String username);

    /**
     * Checks if username and password match that of the user that is logging in
     * @param loginAttempt Student object that contains a username and password
     * @return Student object that contains that username and password, otherwise returns null
     */
    @POST("student/login")
    Call<Student> loginValidation(@Body Student loginAttempt);


    /*================================================================*
     *						PUT MAPPINGS							  *
     *================================================================*/

    /**
     * creates a relationship between the student and class they enroll in
     * @param studID ID of student
     * @param classID Id of class
     * @return string saying whether it was a success or failure
     */
    @PUT("student/{studID}/class/{classID}")
    Call<String> assignCourseToStudent(@Path("studID") int studID, @Path("classID") int classID);

    /**
     * removes student from class and removes relationship between that student and class
     * @param userID ID of student
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
    @PUT("student/removeClass/{studID}/{classID}")
    Call<String> unassignStudentFromCourse(@Path("studID") int userID, @Path("classID") int classID);

    /**
     * Edits the info for the student in the database
     * @param studID Id of student
     * @param first First name of student
     * @param last last name of student
     * @param user username of student
     * @param email email of student
     * @param passNew new password of student
     * @return string saying whether it was a success or failure
     */
    @PUT("student/editProfile/{studID}/{first}/{last}/{user}/{email}/{passNew}")
    Call<String> editStudentProfile(@Path("studID")int studID, @Path("first")String first, @Path("last")String last,
                                    @Path("user")String user, @Path("email")String email, @Path("passNew")String passNew);

    @PUT("student/delete/{username}")
    Call<String> deleteStudent(@Path("username")String username);

    @GET("student/exists/{username}")
    Call<List<Student>> checkStudentExist(@Path("username")String username);





    /*================================================================*
     *						DELETE MAPPINGS							  *
     *================================================================*/

}
