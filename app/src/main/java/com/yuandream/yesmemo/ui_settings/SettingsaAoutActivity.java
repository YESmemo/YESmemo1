package com.yuandream.yesmemo.ui_settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuandream.yesmemo.R;
import com.yuandream.yesmemo.databinding.ActivitySettingsAboutBinding;

public class SettingsaAoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySettingsAboutBinding binding;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        backButton = binding.settingsAboutBackButton;

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == backButton) {
            overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
            finish();
        }
    }
}