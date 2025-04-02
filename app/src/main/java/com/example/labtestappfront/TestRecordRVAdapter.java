package com.example.labtestappfront;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Collections;
import java.util.Comparator;
public class TestRecordRVAdapter extends RecyclerView.Adapter<TestRecordRVAdapter.ViewHolder> {

    private List<TestRecord> records;
    private ApiService apiService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public TestRecordRVAdapter(List<TestRecord> records, ApiService apiservice) {
        this.records = records;
        this.apiService = apiservice;
    }

    public void setRecords(List<TestRecord> records) {
        this.records = records;
        Log.w("myApp", "RV Adapter Add");
        notifyDataSetChanged();
    }

    private void deleteRecord(int recordId, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestRecord record = records.get(position);
        String newPatientInfo = "ID: " + record.getId() + " | " + record.getPatientName();
        String newDetails = "Date: " + dateFormat.format(record.getTestDate()) + " | " + "Type: " + record.getTestType();
        String newResult = "Result: " + record.getResult();

        holder.tvPatientInfo.setText(newPatientInfo);
        holder.tvTestDetails.setText(newDetails);
        holder.tvResult.setText(newResult);

        holder.btnDelete.setOnClickListener(x -> {
            int currentPosition = holder.getAdapterPosition();

            apiService.deleteRecord(record.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        records.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                        notifyItemRangeChanged(currentPosition, records.size());
                        Toast.makeText(holder.itemView.getContext(), "Record deleted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Failed to delete record", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(holder.itemView.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return records != null ? records.size() : 0;
    }

    public void sortRecords(Comparator<TestRecord> comparator) {
        Collections.sort(records, comparator);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPatientInfo, tvTestDetails, tvResult;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatientInfo = itemView.findViewById(R.id.tvPatientInfo);
            tvTestDetails = itemView.findViewById(R.id.tvTestDetails);
            tvResult = itemView.findViewById(R.id.tvResult);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
