package com.yuandream.yesmemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.yuandream.yesmemo.data.mUtils1;
import com.yuandream.yesmemo.databinding.ActivityMainBinding;
import com.yuandream.yesmemo.ui.notes.NotesFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private LinearLayout toolbar_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initApp();
        initView();

        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_notes, R.id.navigation_plans, R.id.navigation_recording)
                .build();

        NavigationUI.setupWithNavController(navView, navController);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_notes:
                    toolbar_title.setText(getString(R.string.main_notes_fragment_title_notes));
                    break;
                case R.id.navigation_plans:
                    toolbar_title.setText(getString(R.string.main_notes_fragment_title_plans));
                    break;
                case R.id.navigation_recording:
                    toolbar_title.setText(getString(R.string.main_notes_fragment_title_recording));
                    break;
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        toolbar_more.setOnClickListener(v -> {
            PopupMenu menu_more = new PopupMenu(MainActivity.this, toolbar_more);
            menu_more.getMenuInflater().inflate(R.menu.menu_main_more, menu_more.getMenu());

            // 获取宫格视图的当前列数
            int numColumns = NotesFragment.getDataListNumColumns();

            // 遍历菜单项，设置自定义布局
            for (int i = 0; i < menu_more.getMenu().size(); i++) {
                MenuItem menuItem = menu_more.getMenu().getItem(i);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                View customView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu_iten_layout1, null);
                ImageView iconView = customView.findViewById(R.id.menu_iten__layout1_icon);
                TextView titleView = customView.findViewById(R.id.menu_iten__layout1_title);
                iconView.setImageResource(getIconResource(menuItem.getItemId()));

                // 根据宫格视图的列数设置菜单项的名称
                if (menuItem.getItemId() == R.id.menu_main_pgv) {
                    if (numColumns == 1) {
                        menuItem.setTitle(R.string.menu_main_more_title_grid_view);
                    } else if (numColumns == 2) {
                        menuItem.setTitle(R.string.menu_main_more_title_list_view);
                    }
                }

                titleView.setText(menuItem.getTitle());
                menuItem.setActionView(customView);
            }

            menu_more.setOnMenuItemClickListener(item -> {
                // 处理菜单项点击事件
                NotesFragment notesFragment = NotesFragment.getCurrentFragment(this);
                switch (item.getItemId()) {
                    case R.id.menu_main_refresh:
                        if (notesFragment != null) {
                            notesFragment.refreshDataList(0);
                        }
                        break;
                    case R.id.menu_main_pgv:
                        if (notesFragment != null) {
                            notesFragment.toggleNumColumns();
                        }
                        break;
                    case R.id.menu_main_so:
                        Toast.makeText(MainActivity.this, "开发中...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_main_settings:
                        startActivity(new Intent(this, SettingsActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }
                return true;
            });
            menu_more.show();
        });
        ;
    }

    private int getIconResource(int itemId) {
        switch (itemId) {
            case R.id.menu_main_pgv:
                return R.drawable.ic_dashboard_black_24dp;
            case R.id.menu_main_so:
                return R.drawable.ic_more;
            case R.id.menu_main_settings:
                return R.drawable.ic_settings_24;
            default:
                return 0;
        }
    }

    private void initApp() {
        // 检查是否已授予存储权限
        if (checkStoragePermission()) {
            // 已经授予了权限，可以执行相关操作
            performStorageOperations();
        } else {
            // 未授予权限，请求权限
            requestStoragePermission();
        }

        File notesDir = new File(mUtils1.DOC_NOTES_DIR);
        File plansDir = new File(mUtils1.DOC_PLANS_DIR);
        File recordingsDir = new File(mUtils1.DOC_RECORDINGS_DIR);
        // 如果文件夹不存在，则创建多层文件夹
        if (!notesDir.exists()) {
            if (notesDir.mkdirs()) {
                // 文件夹创建成功
            } else {
                // 文件夹创建失败
            }
        }
        if (!plansDir.exists()) {
            if (plansDir.mkdirs()) {
                // 文件夹创建成功
            } else {
                // 文件夹创建失败
            }
        }
        if (!recordingsDir.exists()) {
            if (recordingsDir.mkdirs()) {
                // 文件夹创建成功
            } else {
                // 文件夹创建失败
            }
        }
        String[] folderPaths = {mUtils1.DOC_NOTES_DIR, mUtils1.DOC_PLANS_DIR, mUtils1.DOC_RECORDINGS_DIR};

        for (String folderPath : folderPaths) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    // 文件夹创建成功
                } else {
                    // 文件夹创建失败
                }
            }
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.main_toolbar);
        toolbar_title = findViewById(R.id.main_toolbar_title);
        toolbar_more = findViewById(R.id.main_toolbar_more);
    }

    private boolean checkStoragePermission() {
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_REQUEST_CODE
        );
    }

    private void performStorageOperations() {
        // 在这里执行需要访问存储的操作
        // 例如：写入文件、读取文件等
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，可以执行相关操作
                performStorageOperations();
            } else {
                // 权限被拒绝，无法执行相关操作
                Toast.makeText(this, "存储权限被拒绝，无法执行相关操作", Toast.LENGTH_SHORT).show();
            }
        }
    }

}