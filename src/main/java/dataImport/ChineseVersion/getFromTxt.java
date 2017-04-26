package dataImport.ChineseVersion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static dataImport.ChineseVersion.dataImportDao.insertDic;

/**
 * Created by ACER on 2017/4/20.
 */
public class getFromTxt {

        public static void main(String[] args) {

            getData();
        }

        public static void getData(){
            try {
                StringBuffer sb= new StringBuffer("");

                FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\数据字典.txt");
                BufferedReader br = new BufferedReader(reader);

                String exampleNext =br.readLine();

                while(exampleNext != null) {

                    String current = exampleNext;

                    //插入列
                    if (current.contains("数据字典")||current.contains("数据词典")){

                        //插入前两列，处理名称占两行情况
                        br.readLine();
                        String name = br.readLine();
                        name = name.replace("数据元素名称", "");
                        String acr = br.readLine();
                        if (acr.contains("TEI/缩写")||acr.contains("TEI/首字母缩写词")){
                            acr = acr.replace("TEI/缩写", "");
                            acr = acr.replace("TEI/首字母缩写词","");
                            acr = acr.replaceAll(" |\t|　*", "");;
                        }else{
                            name = name+acr;
                            acr = br.readLine();
                            acr = acr.replace("TEI/缩写", "");
                            acr = acr.replace("TEI/首字母缩写词","");
                            acr = acr.replaceAll(" |\t|　*", "");
                        }
                        //删除三种形式的空格
                        name = name.replaceAll(" |\t|　*", "");
//                        System.out.println(name);

                        //顺序插入format
                        String format = br.readLine().replace("格式", "");
                        format = format.replaceAll(" |\t|　*", "");

                        //插入xml表
                        String xmlInfo = br.readLine().replace("XML数据类型","");
                        String xmlNext = br.readLine();
                        while (!xmlNext.contains("子数据元素")) {
                            xmlInfo = xmlInfo +"|"+ xmlNext;
                            xmlNext = br.readLine();
                        }
                        dataImportService.handleXml(name,xmlInfo);

                        //新建subElement表
                        String subElement = br.readLine();
                        String subElementNext = br.readLine();

                        while (!subElementNext.contains("属性")) {
                            subElement = subElement + subElementNext;
                            subElementNext = br.readLine();
                        }
                        String subElementTable = dataImportService.handleSubElement(name, subElement);


                        //顺序提取ATTRIBUTE(S)
                        String attribute = subElementNext.replaceAll("属性\\s*","");

                        //顺序提取USAGE, usage占一行，因此需两次下一行
                        String usageTitle =br.readLine();

                        String usage =br.readLine();
                        String usageNext = br.readLine();;
                        while (!usageNext.contains("描述/目的")) {
                            usage = usage + "\n"+usageNext;
                            usageNext = br.readLine();
                        }


                        //顺序提取DESCRIPTION
                        String description = br.readLine();
                        String descriptionNext = br.readLine();
                        while (!descriptionNext.contains("代码")) {
                            description = description + "\n"+descriptionNext;
                            descriptionNext = br.readLine();
                        }

                        //新建CODE表
                        String code = br.readLine();
                        String codeNext = br.readLine();
                        while (!codeNext.equals("备注")) {
                            code = code +"\n"+ codeNext;
                            codeNext = br.readLine();
                        }
                        String codeTableName = dataImportService.handleCode(name, code);

                        //顺序提取REMARK
                        String remark = br.readLine();
                        String remarkNext = br.readLine();
                        while (!remarkNext.equals("示例")){
                            remark = remark + "\n"+remarkNext;
                            remarkNext = br.readLine();
                        }

                        //顺序提取EXAMPLE
                        String example = br.readLine();
                        exampleNext = br.readLine();
                        while (exampleNext!=null&&!exampleNext.contains("数据字典")&&!exampleNext.contains("数据词典")){
                            example = example + "\n"+exampleNext;
                            exampleNext = br.readLine();
                        }

                        insertDic(name, acr, format,"DATA_DIC_CHN_XML",subElementTable,attribute,usage,description,codeTableName,remark,example);
//                      System.out.println(name+"|    "+acr+"|    "+ format+"|    "+subElement+"|    "+attribute+"|    "+usage+"|    "+description+"|    "+code+"|    "+remark+"|    "+example);
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