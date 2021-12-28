package com.example.Tarp.Api;

import com.example.Tarp.model.Classes;
import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface TeacherApi {

    /*================================================================*
     *						GET MAPPINGS							  *
     *================================================================*/

    /**
     * Gets the password of the teacher based on the ID
     * @param userID ID of teacher
     * @return password of the teacher
     */
    @GET("teacher/getPassword/{userID}")
    Call<String> getPassword(@Path("userID") int userID);

    @GET("teacher/all")
    Call<List<Teacher>> getAllTeachers();
    /**
     * Gets the teacher based off of ID in the database
     * @param userID ID of the teacher
     * @return Teacher object associated with ID
     */
    @GET("teacher/{teachID}")
    Call<Teacher> getTeacherByID(@Path("teachID") int userID);

    /**
     * Gets list of classes the teacher is teaching
     * @param teachID ID of teacher
     * @return list of classes being taught by teacher
     */
    @GET("teacher/{teachID}/teaching")
    Call<List<Classes>> getClassesBeingTaught(@Path("teachID") int teachID);

    /**
     * Gets the class ID to remove from the teacher class list
     * @param userID Id of teacher
     * @param className Name of class to remove
     * @param classSection Name of section to remove
     * @return ID of class to be removed
     */
    @GET("teacher/findClass/{teachID}/{className}/{classSection}")
    Call<Integer> findTeacherClass(@Path("teachID") int userID, @Path("className") String className, @Path("classSection") String classSection);


    /*================================================================*
     *						POST MAPPINGS							  *
     *================================================================*/

    /**
     * Adds a new teacher object to database
     * @param teacher Teacher object to be added
     * @return teacher object that was added
     */
    @POST("teacher/add")
    Call<Teacher> addTeacher(@Body Teacher teacher);

    /**
     * Finds list of teachers based on a username
     * @param username username to look for
     * @return list of teachers with that username
     */
    @POST("teacher/findUserName/{username}")
    Call<List<Teacher>> findTeaUserName(@Path("username") String username);

    /**
     * Checks if username and password match that of the user that is logging in
     * @param teacherCheck Teacher object that contains a username and password
     * @return Teacher object that contains that username and password, otherwise returns null
     */
    @POST("teacher/login")
    Call<Teacher> loginValidation(@Body Teacher teacherCheck);


    /*================================================================*
     *						PUT MAPPINGS							  *
     *================================================================*/

    /**
     * creates a relationship between the student and class they enroll in
     * @param teachID ID of teacher
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
    @PUT("teacher/{teachID}/class/{classID}")
    Call<String> assignCourseToTeacher(@Path("teachID") int teachID, @Path("classID") int classID);

    /**
     * removes student from class and removes relationship between that student and class
     * @param userID ID of teacher
     * @param classID ID of class
     * @return string saying whether it was a success or failure
     */
    @PUT("teacher/removeClass/{teachID}/{classID}")
    Call<String> unassignTeacherFromCourse(@Path("teachID") int userID, @Path("classID") int classID);

    /**
     * Edits the info for the teacher in the database
     * @param userID Id of teacher
     * @param first First name of teacher
     * @param last last name of teacher
     * @param user username of teacher
     * @param email email of teacher
     * @param passNew new password of teacher
     * @return string saying whether it was a success or failure
     */
    @PUT("teacher/editProfile/{userID}/{first}/{last}/{user}/{email}/{passNew}")
    Call<String> editTeacherProfile(@Path("userID")int userID, @Path("first")String first, @Path("last")String last,
                                    @Path("user")String user, @Path("email")String email, @Path("passNew")String passNew);

    @PUT("teacher/delete/{username}")
    Call<String> deleteTeacher(@Path("username")String username);

    @GET("teacher/exists/{username}")
    Call<List<Teacher>> checkTeacherExist(@Path("username")String username);
}