package com.study.food.ui.snack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class SnackViewModel extends ViewModel {

    private final MediatorLiveData<String> mText;

    public SnackViewModel() {
        mText = new MediatorLiveData<>();
        mText.setValue("点单页面");
    }

    public LiveData<String> getText() {
        return mText;
    }
}