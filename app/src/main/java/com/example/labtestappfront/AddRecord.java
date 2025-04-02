package com.example.labtestappfront;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddRecord extends AppCompatActivity {
    private EditText etPatientName;
    private EditText etTestType;
    private EditText etTestDate;
    private EditText etResult;
    private Button btnSubmit;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);

        etPatientName = findViewById(R.id.etPatientName);
        etTestType = findViewById(R.id.etTestType);
        etTestDate = findViewById(R.id.etTestDate);
        etResult = findViewById(R.id.etResult);
        btnSubmit = findViewById(R.id.btnSubmit);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnSubmit.setOnClickListener(v -> addNewRecord());

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }

    private void addNewRecord() {
        String name = etPatientName.getText().toString();
        String testType = etTestType.getText().toString();
        String dateInput = etTestDate.getText().toString();
        String result = etResult.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date testDate = dateFormat.parse(dateInput);

            TestRecord newRecord = new TestRecord(name, testType, testDate, result);
            Call<TestRecord> call = apiService.createRecord(newRecord);

            call.enqueue(new Callback<TestRecord>() {
                @Override
                public void onResponse(Call<TestRecord> call, Response<TestRecord> response) {
                    if (response.isSuccessful()) {
                        CustomToast.showToast(AddRecord.this, "Record added!", CustomToast.ToastType.SUCCESS);
                        finish();
                    } else {
                        CustomToast.showToast(AddRecord.this, "Failed to add record", CustomToast.ToastType.WARNING);
                    }
                }

                @Override
                public void onFailure(Call<TestRecord> call, Throwable t) {
                    CustomToast.showToast(AddRecord.this, "Error: " + t.getMessage(), CustomToast.ToastType.ERROR);
                }
            });

        } catch (Exception e) {
            CustomToast.showToast(AddRecord.this, "Invalid date format! Use YYYY-MM-DD", CustomToast.ToastType.ERROR);
        }
    }
}
