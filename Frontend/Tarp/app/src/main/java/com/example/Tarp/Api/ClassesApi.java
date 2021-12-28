package com.example.Tarp.Api;

import com.example.Tarp.model.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassesApi {

    /*================================================================*
     *						GET MAPPINGS							  *
     *================================================================*/

    /**
     * Gets list of all classes
     * @return list of all classes
     */
    @GET("classes/getList")
    Call<List<Classes>> getClassList();

    /**
     * Checks if a class already exists
     * @param className Name of class
     * @param classSect Section of class
     * @return list of classes that contain that name and section
     */
    @GET("classes/checkExist/{className}/{classSect}")
    Call<List<Classes>> checkClassExist(@Path("className") String className, @Path("classSect") String classSect);

    /**
     * Gets the ID of a class in database
     * @param className Name of class
     * @param classSection Section of class
     * @return ID of class based on name and section
     */
    @GET("classes/className/{className}/classSection/{classSection}")
    Call<Integer> getClassByNameAndSection(@Path("className") String className, @Path("classSection") String classSection);


    /*================================================================*
     *						POST MAPPINGS							  *
     *================================================================*/

    /**
     * Reduces the list of classes based on a searched class
     * @param searchClass Class object to be searched
     * @return reduced list of classes based on the searched c
     */
    @POST("classes/reduceList")
    Call<List<Classes>> reducedList(@Body Classes searchClass);

    /**
     * Adds a new class to database
     * @param newClass New class object to be added
     * @return class object that was added
     */
    @POST("class/add")
    Call<Classes> addClassToList(@Body Classes newClass);



    @PUT("class/delete/{name}/{section}")
    Call<String> deleteFromList(@Path("name") String name, @Path("section") String section);
}
