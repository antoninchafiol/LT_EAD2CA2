package com.example.labtestappfront;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TestRecordRVAdapter extends RecyclerView.Adapter<TestRecordRVAdapter.ViewHolder> {

    private List<TestRecord> records;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public TestRecordRVAdapter(List<TestRecord> records) {
        this.records = records;
    }

    public void setRecords(List<TestRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestRecord record = records.get(position);
        holder.tvPatientName.setText(record.getPatientName());
        holder.tvTestType.setText(record.getTestType());
        holder.tvTestDate.setText(dateFormat.format(record.getTestDate()));
        holder.tvResult.setText(record.getResult());
    }

    @Override
    public int getItemCount() {
        return records != null ? records.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPatientName, tvTestType, tvTestDate, tvResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvTestType = itemView.findViewById(R.id.tvTestType);
            tvTestDate = itemView.findViewById(R.id.tvTestDate);
            tvResult = itemView.findViewById(R.id.tvResult);
        }
    }
}
