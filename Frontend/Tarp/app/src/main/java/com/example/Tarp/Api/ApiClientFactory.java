package com.example.Tarp.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {

    static Retrofit apiClientSeed = null;

   static Retrofit getApiClientSeed() {
        if(apiClientSeed == null){
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                //  Local URL: http://10.0.2.2:8080/
                // Server URL: http://coms-309-036.cs.iastate.edu:8080/
        }
        return apiClientSeed;
   }

   public static StudentApi GetStudentApi() { return getApiClientSeed().create(StudentApi.class); }
   public static TeacherApi GetTeacherApi() { return getApiClientSeed().create(TeacherApi.class); }
   public static ClassesApi GetClassesApi() { return getApiClientSeed().create(ClassesApi.class); }
   public static AdminApi   GetAdminApi()   { return getApiClientSeed().create(AdminApi.class);   }
}
