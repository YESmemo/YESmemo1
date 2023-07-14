package com.yuandream.yesmemo.ui.recordings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecordingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecordingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("录音");
    }

    public LiveData<String> getText() {
        return mText;
    }
}