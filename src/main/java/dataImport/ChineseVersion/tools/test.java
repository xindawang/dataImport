package dataImport.ChineseVersion.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class test {
    private static List<String> tableNameList = new ArrayList<String>();
    private static Queue<String> columnList = new LinkedList<String>();

    public static void main(String[] args) {

        getData();
//        for (String s : tableNameList){
//            System.out.println(s);
//        }
    }

    private static void getEachElement(){

    }

    static  String elementName;
    public static void getData(){
        try {
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\数据字典.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;
            int count =0;
            while((str = br.readLine()) != null) {

                String current = str;

//                if (current.equals("REMARK(S)")||current.equals("REMARK (S)")||current.equals("REMARK(s)")) {
//                    System.out.println(current);
//                    count++;
//                }

                if (current.equals("示例")) {
                    if (br.readLine().contains("数据字典")){
                        br.readLine();
                        System.out.println(br.readLine());
                    }

                    count++;
//                    System.out.println(count);
                }
            }
            System.out.println(count);

            br.close();
            reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}