package dataImport.eachTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class deleteRedundantName {
    private static List<String> tableNameList = new ArrayList<String>();
    private static Queue<String> columnList = new LinkedList<String>();

    public static void main(String[] args) {

        getData();
    }

    private static void getEachElement(){

    }

    public static void getData(){
        try {
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\DataDicFinal.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {

                String current = str;
                if (current.contains("DATA_ELEMENT_NAME")){
                    String next = br.readLine();
                    String nextNext = br.readLine();
                    if (next.contains("TEI")||nextNext.contains("TEI")){
                        System.out.println(current);
                        System.out.println(next);
                        System.out.println(nextNext);
                    }else{
                        System.out.println(next);
                        System.out.println(nextNext);
                    }
                }else{
                    System.out.println(str);
                }
            }

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