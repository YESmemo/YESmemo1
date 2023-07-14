package com.yuandream.yesmemo.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuandream.yesmemo.NoteConentActivity;
import com.yuandream.yesmemo.R;
import com.yuandream.yesmemo.databinding.FragmentNoteBinding;
import com.yuandream.yesmemo.ui.notes.list.NotesItenAdapter;
import com.yuandream.yesmemo.ui.notes.list.NotesItenBean;
import com.yuandream.yesmemo.data.mUtils1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesFragment extends Fragment implements View.OnClickListener {

    private FragmentNoteBinding binding;

    private FloatingActionButton button_add;
    public static GridView datalist;
    private LinearLayout note_layout_search;
    private SearchView searchView;
    private NotesItenAdapter mnotesItenAdapter;
    private List<NotesItenBean> mlistBean;

    public static NotesFragment getCurrentInstance(FragmentManager fragmentManager) {
        return (NotesFragment) fragmentManager.findFragmentByTag("androidx-nav-graph:navigation_notes");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotesViewModel notesViewModel =
                new ViewModelProvider(this).get(NotesViewModel.class);

        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNote;
        notesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        initView();
        initListData();
        initListEvent();

        return root;
    }

    private void initView() {
        button_add = binding.noteAdd;
        datalist = binding.noteList;
        searchView = binding.noteSearch;
//        note_layout_search = binding.noteLayoutSearch;

        // 设置列表列数为1
        datalist.setNumColumns(1);

        button_add.setOnClickListener(this);
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                note_layout_search.setVisibility(View.GONE);
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mnotesItenAdapter.getFilter().filter(newText);
                return true;
            }
        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                //note_layout_search.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });
    }

    private void initListEvent() {
        mnotesItenAdapter = new NotesItenAdapter(getContext(), mlistBean);
        datalist.setAdapter(mnotesItenAdapter);
        datalist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotesItenBean notesItenBean = mlistBean.get(i);
                String title = notesItenBean.getTitle();
                String title2 = notesItenBean.getTitle2();
                String conent = notesItenBean.getConent();

                Intent intent = new Intent(getContext(), NoteConentActivity.class);
                intent.putExtra("data", notesItenBean);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }

    private void initListData() {
        mlistBean = new ArrayList<>();

        for (int i = 0; i < mUtils1.getFileCount(mUtils1.DOC_NOTES_DIR); i++) {
            ArrayList<String> mFileDirLists = mUtils1.getFileList(mUtils1.DOC_NOTES_DIR);
            JSONObject fc1;
            try {
                fc1 = new JSONObject(mUtils1.getFileContent(mFileDirLists.get(i), "UTF-8"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                boolean pinned = fc1.getBoolean("pinned");
                if (pinned) {
                    // 处理 pinned 值为 true 的项目
                    // 您可以根据需要进行处理，例如添加到特定的列表中等
                    // 注意：此处为待处理部分，您可以根据需求进行相应的逻辑处理
                    continue;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            NotesItenBean notesItenBean1 = new NotesItenBean();
            try {
                notesItenBean1.setTitle(fc1.getString("title"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                notesItenBean1.setTitle2(fc1.getString("title2"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                notesItenBean1.setConent(fc1.getString("content"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                notesItenBean1.setEdittime(fc1.getString("edittime"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                notesItenBean1.setCreatetime(fc1.getString("createtime"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                notesItenBean1.setPinned(fc1.getBoolean("pinned"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            mlistBean.add(notesItenBean1);
        }
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
            String fileName = "未命名的笔记";
            File file = new File(mUtils1.DOC_NOTES_DIR + fileName + ".json");
            int count = 1;
            while (file.exists()) {
                fileName = "未命名的笔记" + count;
                file = new File(mUtils1.DOC_NOTES_DIR + fileName + ".json");
                count++;
            }

            String currentTime;
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // 获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                currentTime = sdf.format(new Date());

                bufferedWriter.write("{\"title\": \"" + fileName + "\",\"title2\": \"\",\"content\": \"\",\"edittime\": \"" + currentTime + "\",\"createtime\": \"" + currentTime + "\",\"pinned\": " + false + "}");

                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            NotesItenBean notesItenBean1 = new NotesItenBean();
            notesItenBean1.setTitle(fileName);
            notesItenBean1.setTitle2("");
            notesItenBean1.setConent("");
            notesItenBean1.setEdittime(currentTime);
            notesItenBean1.setCreatetime(currentTime);
            notesItenBean1.setPinned(false);
            mlistBean.add(notesItenBean1);

            Intent intent = new Intent(getContext(), NoteConentActivity.class);
            intent.putExtra("data", notesItenBean1);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            mnotesItenAdapter.notifyDataSetChanged();

        }
    }

    public void toggleNumColumns() {
        int numColumns = datalist.getNumColumns();
        datalist.setNumColumns(numColumns == 1 ? 2 : 1);
    }

    public void refreshDataList(int refreshType) {
        final int RefreshAdapter = 0;
        final int InitDate = 1;

        if (refreshType == RefreshAdapter) {
            mnotesItenAdapter.notifyDataSetChanged();
        } else if (refreshType == InitDate) {
            mlistBean.clear();
            initListData();
        }
    }

    public static int getDataListNumColumns() {
        return datalist.getNumColumns();
    }

    public static NotesFragment getCurrentFragment(FragmentActivity activity) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main); // 替换为你的 NavHostFragment 的 ID
        if (navHostFragment != null) {
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
            if (currentFragment instanceof NotesFragment) {
                return (NotesFragment) currentFragment;
            }
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mnotesItenAdapter.notifyDataSetChanged();
    }
}
