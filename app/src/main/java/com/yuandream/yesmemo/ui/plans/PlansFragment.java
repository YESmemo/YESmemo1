package com.yuandream.yesmemo.ui.plans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuandream.yesmemo.databinding.FragmentPlansBinding;

public class PlansFragment extends Fragment implements View.OnClickListener {

    private FragmentPlansBinding binding;

    private FloatingActionButton button_add;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlansViewModel plansViewModel =
                new ViewModelProvider(this).get(PlansViewModel.class);

        binding = FragmentPlansBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        final TextView textView = binding.textPlan;
        plansViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        button_add = binding.planAdd;
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