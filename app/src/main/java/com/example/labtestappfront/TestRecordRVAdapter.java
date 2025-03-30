package com.example.labtestappfront;

import android.view.LayoutInflater;
import android.view.View;
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
        String newstringID = "ID: " + record.getId() + " - ";
        holder.tvId.setText(newstringID);
        holder.tvPatientName.setText(record.getPatientName());

        String newTestType = "Type: " + record.getTestType();
        String newDate = "Date: " + dateFormat.format(record.getTestDate());
        String newResult = "Result: " + record.getResult();

        holder.tvTestType.setText(newTestType);
        holder.tvTestDate.setText(newDate);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPatientName, tvTestType, tvTestDate, tvResult, tvId;
        public Button btnUpdate, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvTestType = itemView.findViewById(R.id.tvTestType);
            tvTestDate = itemView.findViewById(R.id.tvTestDate);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvId = itemView.findViewById(R.id.tvPatientId);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
