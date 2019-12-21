package com.example.aa.ui.individuation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IndividuationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IndividuationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("本模块有待开发");
    }

    public LiveData<String> getText() {
        return mText;
    }
}