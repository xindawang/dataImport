package com;

import java.io.File;

/**
 * Created by ACER on 2017/9/3.
 */
public class CopyFile {
    public static void main(String[] args) {
        File file = new File("E:\\essays\\validate2");
        getAllFiles(file,0);
    }

    public static void getAllFiles(File dir,int level)
    {
        File[] files=dir.listFiles();
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                //这里面用了递归的算法
                getAllFiles(files[i],level);
            }
            else {
                String newPath = "E:\\essays\\allChosenFiles\\";
                File fnew = new File(newPath +files[i].getName());
                files[i].renameTo(fnew);
            }
        }
    }
}
