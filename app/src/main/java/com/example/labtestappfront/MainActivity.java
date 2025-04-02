package com.example.labtestappfront;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private TestRecordRVAdapter rvAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get vals
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        rvAdapter = new TestRecordRVAdapter(new ArrayList<>(), apiService);
        recyclerView.setAdapter(rvAdapter);

        Spinner spinnerSort = findViewById(R.id.spinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getPatientName));
                        break;
                    case 1:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getPatientName).reversed());
                        break;
                    case 2:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getTestDate).reversed());
                        break;
                    case 3:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getTestDate));
                        break;
                    case 4:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getTestType));
                        break;
                    case 5:
                        rvAdapter.sortRecords(Comparator.comparing(TestRecord::getTestType).reversed());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Set listeners
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchText)) {
                    fetchAllRecords();
                } else {
                    try {
                        int id = Integer.parseInt(searchText);
                        fetchRecordById(id);
                    } catch (NumberFormatException e) {
                        CustomToast.showToast(MainActivity.this, "Invalid ID, should be number only", CustomToast.ToastType.ERROR);
                    }
                }
            }
        });

        FloatingActionButton addBtn = findViewById(R.id.addTest);
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecord.class);
            startActivity(intent);
        });
    }

    private void fetchAllRecords() {
        apiService.getAllRecords().enqueue(new Callback<List<TestRecord>>() {
            @Override
            public void onResponse(Call<List<TestRecord>> call, Response<List<TestRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rvAdapter.setRecords(response.body());
                } else {
                    CustomToast.showToast(MainActivity.this, "No records found", CustomToast.ToastType.WARNING);
                }
            }
            @Override
            public void onFailure(Call<List<TestRecord>> call, Throwable t) {
                CustomToast.showToast(MainActivity.this, "Error fetching records", CustomToast.ToastType.ERROR);
            }
        });
    }

    private void fetchRecordById(int id) {
        apiService.getRecordById(id).enqueue(new Callback<TestRecord>() {
            @Override
            public void onResponse(Call<TestRecord> call, Response<TestRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TestRecord> singleRecord = new ArrayList<>();
                    singleRecord.add(response.body());
                    rvAdapter.setRecords(singleRecord);
                } else {
                    CustomToast.showToast(MainActivity.this, "No records found", CustomToast.ToastType.WARNING);
                }
            }
            @Override
            public void onFailure(Call<TestRecord> call, Throwable t) {
                CustomToast.showToast(MainActivity.this, "Error fetching record", CustomToast.ToastType.ERROR);
            }
        });
    }
}
