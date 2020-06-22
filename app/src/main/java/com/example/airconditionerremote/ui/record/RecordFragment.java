package com.example.airconditionerremote.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airconditionerremote.HttpTask;
import com.example.airconditionerremote.R;
import com.example.airconditionerremote.data.Record;
import com.example.airconditionerremote.data.Records;
import com.example.airconditionerremote.ui.home.HomeFragment;

import java.io.IOException;
import java.util.Map;

public class RecordFragment extends Fragment {

    private RecyclerView rvRecords;


    private static FragmentManager manager;
    private static FragmentTransaction ft;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        ListAdapter listAdapter = new ListAdapter(Records.getRecords());
        rvRecords = root.findViewById(R.id.rv_records);
        rvRecords.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecords.setAdapter(listAdapter);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Record.saveRecord(getContext());
        Records.saveRecords(getContext());
    }

    static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        Records records;

        public ListAdapter(Records records) {
            this.records = records;
        }
        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
            final Record record = records.get(position);
            holder.tvTemp.setText(record.getTemputerString());
            holder.tvMode.setText(record.getModeString());
            holder.tvPower.setText(record.getPowerString());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    record.setRunning(Record.getMainRecord().isRunning());
                    Record.setMainRecord(record);
                    try {
                        HttpTask.sendGet(Record.getMainRecord().generateUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTemp;
            private TextView tvMode;
            private TextView tvPower;
            private CardView cardView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTemp = itemView.findViewById(R.id.tv_temp);
                tvMode = itemView.findViewById(R.id.tv_mode);
                tvPower = itemView.findViewById(R.id.tv_power);
                cardView = itemView.findViewById(R.id.cardView);
            }

            public TextView getTvTemp() {
                return tvTemp;
            }

            public void setTvTemp(TextView tvTemp) {
                this.tvTemp = tvTemp;
            }

            public TextView getTvMode() {
                return tvMode;
            }

            public void setTvMode(TextView tvMode) {
                this.tvMode = tvMode;
            }

            public TextView getTvPower() {
                return tvPower;
            }

            public void setTvPower(TextView tvPower) {
                this.tvPower = tvPower;
            }
        }
    }
}
