package com.example.labtestappfront;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

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

        rvAdapter = new TestRecordRVAdapter(new ArrayList<>());
        recyclerView.setAdapter(rvAdapter);

        apiService = ApiClient.getClient().create(ApiService.class);

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
                        Toast.makeText(MainActivity.this, "Invalid ID, should be number only", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "No records found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<TestRecord>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching records", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "Record not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TestRecord> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching record", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
