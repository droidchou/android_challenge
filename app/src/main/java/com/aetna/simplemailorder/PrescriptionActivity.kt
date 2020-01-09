package com.aetna.simplemailorder

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aetna.simplemailorder.data.Prescription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrescriptionActivity : BaseAppCompatActivity(), PrescriptionAdapter.AddToCartButtonListener {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PrescriptionAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        // set up recyclerView
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        linearLayoutManager =
            LinearLayoutManager(this@PrescriptionActivity, RecyclerView.VERTICAL, false)
        adapter = PrescriptionAdapter(this@PrescriptionActivity)
        rv.layoutManager = linearLayoutManager
        rv.adapter = adapter

        // mock api response
        service.prescriptions.enqueue(object : Callback<List<Prescription>> {
            override fun onResponse(
                    call: Call<List<Prescription>>,
                    response: Response<List<Prescription>>
            ) {

                var listOfPrescriptions = response.body()
                listOfPrescriptions?.let {
                    adapter.setData(listOfPrescriptions)
                }
            }

            override fun onFailure(call: Call<List<Prescription>>, t: Throwable) {
                //TODO: what we plan to do here if api fail
            }
        })
    }

    override fun onAddToCartButtonClicked(prescription: Prescription) {
        Log.d("ButtonClicked", "prescription : --> " + prescription.prescriptionid)
    }
}
