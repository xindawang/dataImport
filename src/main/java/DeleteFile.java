package com;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by ACER on 2017/9/3.
 */
public class DeleteFile {

    static HashSet hashSet;
    public static void main(String[] args) {
        hashSet= readTxtFile("E://w.txt");
        File file = new File("E://Categorized");
        getAllFiles(file);
//        System.out.println(hashSet);
    }

    public static HashSet<String> readTxtFile(String filePath)
    {
        HashSet<String> hashSet = new HashSet<String>();
        try
        {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    hashSet.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return hashSet;
    }


    public static void getAllFiles(File dir)
    {
        File[] files=dir.listFiles();
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                getAllFiles(files[i]);
            }
            else {
//                isTobeDel(files[i]);
                if (isTobeDel(files[i]))
                files[i].delete();
            }
        }
    }

    public static boolean isTobeDel(File dir){
        if (dir.getName().equals("._.DS_Store")||dir.getName().equals(".DS_Store")){
            return true;
        }
        String []fileName = dir.getName().split("_");
        String name = fileName[0]+"_"+fileName[2];
        if (hashSet.contains(name)) return true;
        return false;
    }

}
