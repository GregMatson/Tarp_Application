package com.example.Tarp.Api;

import com.example.Tarp.model.Admin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminApi {

    @POST("admin/login/{user}/{pass}")
    Call<Admin> loginValidation(@Path("user") String user, @Path("pass")String pass);

}
