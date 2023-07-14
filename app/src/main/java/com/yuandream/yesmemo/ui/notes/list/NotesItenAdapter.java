package com.yuandream.yesmemo.ui.notes.list;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.yuandream.yesmemo.R;
import com.yuandream.yesmemo.data.mUtils1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotesItenAdapter extends BaseAdapter implements Filterable {

    private List<NotesItenBean> originalList; // 原始列表数据
    private List<NotesItenBean> filteredList; // 过滤后的列表数据
    private LayoutInflater inflater;
    private Context mcontext;
    private List<NotesItenBean> mlistBean;

    public NotesItenAdapter(Context context, List<NotesItenBean> itemList) {
        this.originalList = itemList;
        this.filteredList = itemList;
        this.inflater = LayoutInflater.from(context);
        this.mcontext = context;
        mlistBean = itemList;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.iten_notes, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.item_notes_title);
            viewHolder.title2 = convertView.findViewById(R.id.item_notes_title2);
            viewHolder.edittime = convertView.findViewById(R.id.item_notes_edittime);
            viewHolder.moreButton = convertView.findViewById(R.id.item_notes_moreButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NotesItenBean item = filteredList.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.title2.setText(item.getTitle2());
        viewHolder.edittime.setText(item.getEdittime());

        // 设置moreButton的单击事件
        viewHolder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里处理moreButton的单击事件，弹出菜单并设置相关单击事件
                showPopupMenu(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        // 标题
        TextView title;
        // 标题2
        TextView title2;
        // 编辑时间
        TextView edittime;
        // 更多按钮
        ImageView moreButton;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<NotesItenBean> filteredItems = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // 搜索条件为空，显示全部数据
                    filteredItems.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (NotesItenBean item : originalList) {
                        // 根据搜索条件过滤数据
                        if (item.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredItems.add(item);
                        }
                    }
                }

                results.values = filteredItems;
                results.count = filteredItems.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<NotesItenBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // 项目菜单
    private void showPopupMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_notes_iten, popupMenu.getMenu());

        // 设置删除菜单项的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_notes_iten_pin:
                        // 处理置顶菜单项的点击事件
                        togglePinItem(item, position);
                        return true;
                    case R.id.menu_notes_iten_rename:
                        // 处理重命名菜单项的点击事件
                        renameItem(item, position);
                        return true;
                    case R.id.menu_notes_iten_delete:
                        // 处理删除菜单项的点击事件
                        deleteItem(item, position);
                        return true;
                    case R.id.menu_notes_iten_properties:
                        // 处理属性菜单项的点击事件
                        showProperties(item, position);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    // 置顶功能
    private void togglePinItem(MenuItem item, int position) {
        String menu_notes_item_title_pin = mcontext.getString(R.string.menu_notes_item_title_pin);
        String menu_notes_item_title_unpin = mcontext.getString(R.string.menu_notes_item_title_unpin);

        NotesItenBean pinnedItem = filteredList.get(position);

        if (!pinnedItem.isPinned()) {
            item.setTitle(menu_notes_item_title_unpin);
            // 将项目设为置顶
            pinnedItem.setPinned(true);
            // 将置顶项目移到列表的最前面
            filteredList.remove(position);
            filteredList.add(0, pinnedItem);
        } else {
            item.setTitle(menu_notes_item_title_pin);
            // 取消项目置顶
            pinnedItem.setPinned(false);
            // 将取消置顶的项目移到列表的末尾
            filteredList.remove(position);
            filteredList.add(pinnedItem);
        }

        saveNoteList(position); // 保存笔记列表数据
        notifyDataSetChanged();
    }

    //保存Notes列表
    private void saveNoteList(int position) {
        NotesItenBean item = filteredList.get(position);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", item.getTitle());
            jsonObject.put("title2", item.getTitle2());
            jsonObject.put("content", item.getConent());
            jsonObject.put("edittime", item.getEdittime());
            jsonObject.put("createtime", item.getCreatetime());
            jsonObject.put("pinned", item.isPinned());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 保存到文件
        String jsonString = jsonObject.toString();
        // 根据您的需求选择适当的方式保存数据，这里仅作示例
        try {
            FileWriter fileWriter = new FileWriter(mUtils1.DOC_NOTES_DIR + item.getTitle() + ".json");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 重命名功能
    private void renameItem(MenuItem item, final int position) {
        NotesItenBean selectedItem = filteredList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle(R.string.menu_notes_item_titie_rename);
        builder.setMessage(R.string.alertDialog_notesadapter_renameItem_msg);

        final EditText input = new EditText(mcontext);
        input.setText(selectedItem.getTitle());
        builder.setView(input);

        builder.setPositiveButton(R.string.alertDialog_notesadapter_Item_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取旧项目名称
                NotesItenBean item = filteredList.get(position);
                String oldFileName = mUtils1.DOC_NOTES_DIR + item.getTitle() + ".json";
                // 更新项目
                String newName = input.getText().toString();
                selectedItem.setTitle(newName);
                renameSaveNoteList(position, oldFileName, newName);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(R.string.alertDialog_notesadapter_Item_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取消重命名，不进行任何操作
            }
        });

        builder.show();
    }

    // 保存文件
    private void renameSaveNoteList(int position, String oldFileName, String newName) {
        // 读取旧文件内容
        String fileContent = mUtils1.readTextFile(oldFileName,"UTF-8");
        JSONObject newContent;
        try {
            newContent = new JSONObject(fileContent);
            newContent.put("title", newName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 构建新文件路径
        String newFileName = mUtils1.DOC_NOTES_DIR + newName + ".json";
        // 重命名文件
        boolean renamed = mUtils1.renameFile(oldFileName, newFileName);
        if (!renamed) {
            throw new RuntimeException("Failed to rename file: " + oldFileName);
        }

        // 将内容写入新文件
        try {
            FileWriter writer = new FileWriter(newFileName);
            writer.write(newContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除功能
    private void deleteItem(MenuItem item, final int position) {
        if (position >= 0 && position < filteredList.size()) {
            NotesItenBean delitem = filteredList.get(position);
            String fileName = delitem.getTitle() + ".json";

            // 创建确认对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setTitle(R.string.alertDialog_notesadapter_deleteItem_title);
            builder.setMessage(R.string.alertDialog_notesadapter_deleteItem_msg);
            builder.setPositiveButton(R.string.alertDialog_notesadapter_Item_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 删除对应的文件
                    File file = new File(mUtils1.DOC_NOTES_DIR + fileName);
                    if (file.exists()) {
                        file.delete();
                    }

                    // 删除对应的数据项
                    filteredList.remove(position);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton(R.string.alertDialog_notesadapter_Item_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 取消删除，不进行任何操作
                }
            });

            // 显示对话框
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // 属性
    private void showProperties(MenuItem item, final int position) {
        NotesItenBean selectedItem = filteredList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle(R.string.alertDialog_notesadapter_attributeItem_title);

        String content = mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_title) + ": " + selectedItem.getTitle() + "\n" +
                mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_title2) + ": " + selectedItem.getTitle2() + "\n" +
                mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_content) + ": " + selectedItem.getConent() + "\n" +
                mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_edittime) + ": " + selectedItem.getEdittime() + "\n" +
                mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_createtime) + ": " + selectedItem.getCreatetime() + "\n" +
                mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_pinned) + ": " + (selectedItem.isPinned() ? mcontext.getString(R.string.alertDialog_notesadapter_attributeItem_content_sticky) : mcontext.getString(R.string.valertDialog_notesadapter_attributeItem_content_notsticky));

        builder.setMessage(content);

        builder.setPositiveButton(R.string.alertDialog_notesadapter_Item_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击确定按钮后关闭对话框
            }
        });

        builder.show();
    }


}
