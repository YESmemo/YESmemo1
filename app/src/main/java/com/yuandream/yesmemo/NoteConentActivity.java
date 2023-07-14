package com.yuandream.yesmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.yuandream.yesmemo.databinding.ActivityNoteConentBinding;
import com.yuandream.yesmemo.ui.notes.list.NotesItenBean;

public class NoteConentActivity extends AppCompatActivity {

    private ActivityNoteConentBinding binding;
    private NotesItenBean mNotesItenBean;

    private TextView tTitle;
    private EditText tContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_conent);

        Intent intent = getIntent();
        mNotesItenBean = (NotesItenBean) intent.getSerializableExtra("data");

        initView();
    }

    private void initView() {
        tTitle = findViewById(R.id.note_content_tTitle);
        tContent = findViewById(R.id.note_content_tContent);
        if (mNotesItenBean != null) {
            String t0 = (mNotesItenBean.getTitle() + "\n" + mNotesItenBean.getTitle2() + "\n" + mNotesItenBean.getConent());
            tTitle.setText(mNotesItenBean.getTitle());
            tContent.setText(mNotesItenBean.getConent());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            this.overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        super.onBackPressed();
    }
}