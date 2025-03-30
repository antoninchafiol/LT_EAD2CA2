package com.example.labtestappfront;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Get all records
    @GET(".")
    Call<List<TestRecord>> getAllRecords();

    // Get a record by id
    @GET("{id}")
    Call<TestRecord> getRecordById(@Path("id") int id);

    // Create a new record
    @POST(".")
    Call<TestRecord> createRecord(@Body TestRecord record);

    // Update an existing record
    @PUT("records/{id}")
    Call<TestRecord> updateRecord(@Path("id") int id, @Body TestRecord record);

    // Delete a record
    @DELETE("records/{id}")
    Call<Void> deleteRecord(@Path("id") int id);
}
