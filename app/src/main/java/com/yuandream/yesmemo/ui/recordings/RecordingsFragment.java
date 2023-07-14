package com.yuandream.yesmemo.ui.recordings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.yuandream.yesmemo.
import com.yuandream.yesmemo.databinding.FragmentRecordingsBinding;

public class RecordingsFragment extends Fragment implements View.OnClickListener {

    private FragmentRecordingsBinding binding;

    private FloatingActionButton button_add;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecordingsViewModel notesViewModel =
                new ViewModelProvider(this).get(RecordingsViewModel.class);

        binding = FragmentRecordingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textRecording;
        notesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        button_add = binding.recordingAdd;
        button_add.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view == button_add) {
            //add
        }
    }
}