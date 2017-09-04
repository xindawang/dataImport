package com;

import java.io.*;
import java.util.HashSet;

/**
 * Created by ACER on 2017/9/3.
 */
public class FindAll {

    public static void main(String[] args) {
        File file = new File("E://Categorized");
        getAllFiles(file,0);
    }


    //获取层级的方法
    public static String getLevel(int level)
    {
        //A mutable sequence of characters.
        StringBuilder sb=new StringBuilder();
        for(int l=0;l<level;l++)
        {
            sb.append("|--");
        }
        return sb.toString();
    }
    public static void getAllFiles(File dir,int level)
    {
        System.out.println(getLevel(level)+dir.getName());
        level++;
        File[] files=dir.listFiles();
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                //这里面用了递归的算法
                getAllFiles(files[i],level);
            }
            else {
                System.out.println(getLevel(level)+files[i].getName());
            }
        }
    }

}

