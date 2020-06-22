package com.example.airconditionerremote.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.airconditionerremote.data.Record;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Record> record;

    public HomeViewModel() {
        record = new MutableLiveData<>();
    }

    public MutableLiveData<Record> getRecord() {
        return record;
    }

    public void setRecord(MutableLiveData<Record> record) {
        this.record = record;
    }
}