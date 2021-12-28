package com.example.Tarp;

import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import androidx.annotation.MainThread;

import com.example.Tarp.Api.ApiClientFactory;
import com.example.Tarp.model.Student;

import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class MockitoTests {

    @Mock
    Student student;
    ApiClientFactory mockapi;
/*
    @Test
    public void Signup(){  //Will work once able to mock server
        when(mockapi.GetStudentApi()).thenReturn(42);
        SignUpActivity testSignup = new SignUpActivity();
        boolean check[] = testSignup.sendStuData("Link", "TheGoat", "blah@gmail.com", "link", "TheGoat");
        assertTrue(check[0]);
    }
*/

    @Test
    public void TestPasswordConfirmation(){      //Works!!
        when(student.getPassword()).thenReturn("Password");
        String pass = student.getPassword();
        String confirmPass = "Password";
        SignUpActivity testSignup = new SignUpActivity();
        boolean check = testSignup.checkPass(pass, confirmPass);
        assertTrue(check);
    }

    @Test
    public void TestWrongPassWord(){     //Works!!!!
        when(student.getPassword()).thenReturn("WrongPass");
        String pass = student.getPassword();
        String confirmPass = "Password";
        SignUpActivity testSignup = new SignUpActivity();
        boolean check = testSignup.checkPass(pass, confirmPass);
        assertFalse(check);
    }


//    @Test
//    public void TestIfValidClassTrue(){
//        String aClass = "Math 207";
//        List<String> classList = Arrays.asList("Coms 309", "Math 207", "Cpre 185");
//        StudentAddClass addClassActivity = new StudentAddClass();
//        boolean check = addClassActivity.isClass(classList, aClass);
//        assertTrue(check);
//    }
//
//    @Test
//    public void TestIfValidClassFalse(){
//        String aClass = "Math 300";
//        List<String> classList = Arrays.asList("Coms 309", "Math 207", "Cpre 185");
//        StudentAddClass addClassActivity = new StudentAddClass();
//        boolean check = addClassActivity.isClass(classList, aClass);
//        assertFalse(check);
//    }


}
