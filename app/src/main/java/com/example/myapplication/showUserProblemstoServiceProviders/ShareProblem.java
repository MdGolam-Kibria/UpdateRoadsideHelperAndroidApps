package com.example.myapplication.showUserProblemstoServiceProviders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShareProblem {
    @GET("allUserProblems")
    Call <List<ShareProblemToServiceProviders>> getAllUserProblem();
}
