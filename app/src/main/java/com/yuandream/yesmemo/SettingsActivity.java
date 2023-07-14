package com.yuandream.yesmemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuandream.yesmemo.databinding.ActivitySettingsBinding;
import com.yuandream.yesmemo.ui_settings.SettingsaAoutActivity;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySettingsBinding binding;
    private TextView aboutButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();

    }

    private void initView() {
        backButton = binding.settingsBackButton;
        aboutButton = binding.settingsAboutButton;

        backButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == backButton) {
            overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
            finish();
        }
        if (view == aboutButton) {
            startActivity(new Intent(this,SettingsaAoutActivity.class));
            Objects.requireNonNull(this).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }



    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        super.onBackPressed();
    }
}