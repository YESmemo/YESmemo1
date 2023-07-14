package com.yuandream.yesmemo.data;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class mUtils1 {

    /*
     * 变量
     */

    // 笔记存储目录
    public static String DOC_NOTES_DIR = "/data/user/0/com.yuandream.yesmemo/files/notes/";

    // 计划存储目录
    public static String DOC_PLANS_DIR = "/data/user/0/com.yuandream.yesmemo/files/plans/";

    // 录音存储目录
    public static String DOC_RECORDINGS_DIR = "/data/user/0/com.yuandream.yesmemo/files/recordings/";


    /*
     * 方法
     */


    public static int getFileCount(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                return files.length;
            }
        }
        return 0;
    }

    public static ArrayList<String> getFileList(String FilePath) {
        ArrayList<File> folderList = new ArrayList<>();
        ArrayList<File> fileList = new ArrayList<>();
        File[] files = new File(FilePath).listFiles();

        // 将文件夹和文件的完整路径添加到对应的列表
        for (File file : files) {
            if (file.isDirectory()) {
                folderList.add(file);
            } else {
                fileList.add(file);
            }
        }

        // 使用自定义比较器对文件夹列表进行排序
        Collections.sort(folderList, new Comparator<File>() {
            @Override
            public int compare(File folder1, File folder2) {
                return folder1.getName().compareToIgnoreCase(folder2.getName());
            }
        });

        // 使用自定义比较器对文件列表进行排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return file1.getName().compareToIgnoreCase(file2.getName());
            }
        });

        ArrayList<String> sortedList = new ArrayList<>();

        // 添加文件夹完整路径到排序列表
        for (File folder : folderList) {
            sortedList.add(folder.getAbsolutePath());
        }

        // 添加文件完整路径到排序列表
        for (File file : fileList) {
            sortedList.add(file.getAbsolutePath());
        }

        return sortedList;
    }

    public static String getFileContent(String filePath, String encoding) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
            boolean first = true;
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    content.append(line);
                } else {
                    content.append('\n').append(line);
                }
            }
            br.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // 重命名文件
//    public static boolean renameFile(String originalPath, String newPath) {
//        if (newPath.equals(originalPath)) {
//            return true;
//        }
//        File oldfile = new File(originalPath);
//        if (!oldfile.exists()) {
//            return false;
//        }
//        File newfile = new File(newPath);
//        if (newfile.exists()) {
//            return false;
//        }
//        if (oldfile.renameTo(newfile)) {
//            return true;
//        }
//        return false;
//    }
    public static boolean renameFile(String originalPath, String newPath) {
        if (newPath.equals(originalPath)) {
            return true;
        }
        File oldFile = new File(originalPath);
        if (!oldFile.exists()) {
            return false;
        }
        File newFile = new File(newPath);
        if (newFile.exists()) {
            return false;
        }
        return oldFile.renameTo(newFile);
    }

    // 读入文本文件       "UTF-8"
    public static String readTextFile(String path, String encoding) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
            boolean first = true;
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    content.append(line);
                } else {
                    content.append('\n').append(line);
                }
            }
            br.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void insert(String name, Object value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void writeTextFile(String path, String content) {
        try {
            write(new File(path), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(File file, String content) throws IOException {
        if (!file.exists()) {
            createFile(file);
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public static boolean createFile(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




}
