package com.aetna.simplemailorder.api;

import com.aetna.simplemailorder.data.Prescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PrescriptionService {

    @GET("prescriptions.json")
    Call<List<Prescription>> getPrescriptions();
}
