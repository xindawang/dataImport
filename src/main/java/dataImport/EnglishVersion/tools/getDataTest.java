package dataImport.EnglishVersion.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class getDataTest {
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

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\DataDicFinal.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {

                String current = str;
//                System.out.println(current);
                if (current.contains("XML DATA TYPE")) {

                    String xmlNext = br.readLine();

                    String type = null;
                    String compound = null;
                    String minLen = null;
                    String maxLen = null;
                    String minVal = null;
                    String maxVal =null;
                    if (xmlNext.contains("simpleType, basic data type: ")) {
                        compound = "simpleType, basic data type";
                        type = xmlNext.replace("simpleType, basic data type: ", "");
                    }
                    if (xmlNext.contains("compound data element: ")) {
                        compound = "compound data element";
                        type = xmlNext.replace("compound data element: ", "");
                    }
                    xmlNext = br.readLine();
                    if (!xmlNext.contains("SUB DATA ELEMENTS")){
                        minLen = xmlNext.replace("minimum length: ","");
                        maxLen = br.readLine().replace("maximum length: ","");
                        xmlNext = br.readLine();
                        if (!xmlNext.contains("SUB DATA ELEMENTS")){
                            minVal = xmlNext.replace("minimum value: ","");
                            maxVal = br.readLine().replace("maximum value: ","");
                        }
                    }
                    System.out.println(compound+"   "+type+"   "+minLen+"   "+maxLen+"   "+minVal+"   "+maxVal);
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