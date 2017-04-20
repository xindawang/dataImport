package dataImport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static dataImport.dataImportDao.insertDic;
import static dataImport.dataImportDao.insertXml;
import static dataImport.dataImportService.handleCode;
import static dataImport.dataImportService.handleSubElement;
import static dataImport.dataImportService.handleXml;


public class getDataFromTxt {
    private static List<String> tableNameList = new ArrayList<String>();
    private static Queue<String> columnList = new LinkedList<String>();

    public static void main(String[] args) {

        getData();
    }

    private static void getEachElement(){

    }

    static  String elementName;
    public static void getData(){
        try {
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\DataDicFinal.txt");
            BufferedReader br = new BufferedReader(reader);


            int num =0;
            String exampleNext =br.readLine();
            while(exampleNext != null) {

                String current = exampleNext;

                //插入列
                if (current.contains("DATA_ELEMENT_NAME")) {

                    //插入前两列，处理名称占两行情况
                    String name = current.replace("DATA_ELEMENT_NAME  ", "");
                    String acr = br.readLine();
                    if (acr.contains("TEI/ACRONYM")){
                        acr = acr.replace("TEI/ACRONYM  ", "");
                    }else{
                        name = name+acr;
                        acr = br.readLine();
                        acr = acr.replace("TEI/ACRONYM  ", "");
                    }
                    num++;
                    System.out.println(num);
                    name = name.replace(" ","");
//                    System.out.println(current);


                    //顺序插入format
                    String format = br.readLine().replace("FORMAT  ", "");

                    //插入xml表
                    String xmlNext = br.readLine();
                    String xmlInfo = "";
                    while (!xmlNext.contains("SUB_DATA_ELEMENTS")) {
                        xmlInfo = xmlInfo + xmlNext+"|";
                        xmlNext = br.readLine();
                    }
                    handleXml(xmlInfo);

                    //新建子元素表
                    String subElement = xmlInfo.replace("SUB_DATA_ELEMENTS ","");
                    String subElementNext = br.readLine();

                    while (!subElementNext.contains("ATTRIBUTE(S)")) {
                        subElement = subElement +"|"+ subElementNext;
                        subElementNext = br.readLine();
                    }
                   handleSubElement(subElement);

                    //顺序提取ATTRIBUTE(S)
                    String attribute = subElementNext.replace("ATTRIBUTE(S)  ","");

                    //顺序提取USAGE, usage占一行，因此需两次下一行
                    String usageTitle =br.readLine();
                    String usage =br.readLine();
                    String usageNext = br.readLine();;
                    while (!usageNext.contains("DESCRIPTION/PURPOSE")) {
                        usage = usage + "\n"+usageNext;
                        usageNext = br.readLine();
                    }

                    //顺序提取DESCRIPTION
                    String description = br.readLine();
                    String descriptionNext = br.readLine();
                    while (!descriptionNext.contains("CODE(S)")) {
                        description = description + "\n"+descriptionNext;
                        descriptionNext = br.readLine();
                    }

                    //新建代码表
                    String code = br.readLine();
                    String codeNext = br.readLine();
                    while (!codeNext.equals("REMARK(S)")&&!codeNext.equals("REMARK (S)")&&!codeNext.equals("REMARK(s)")) {
                        code = code +"|"+ codeNext;
                        codeNext = br.readLine();
                    }
                    handleCode(code);

                    //顺序提取REMARK
                    String remark = br.readLine();
                    String remarkNext = br.readLine();
                    while (!remarkNext.equals("EXAMPLE(S)")){
                        remark = remark + "\n"+remarkNext;
                        remarkNext = br.readLine();
                    }

                    //顺序提取EXAMPLE
                    String example = br.readLine();
                    exampleNext = br.readLine();
                    while (exampleNext!=null&&!exampleNext.contains("DATA_ELEMENT_NAME")) {
                        example = example + "\n"+exampleNext;
                        exampleNext = br.readLine();
                    }

                    insertDic(current, acr, format,attribute,usage,description,remark,example);
                    System.out.println(name+"|    "+acr+"|    "+ format+"|    "+attribute+"|    "+usage+"|    "+description+"|    "+remark+"|    "+example);
                    System.out.println();
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