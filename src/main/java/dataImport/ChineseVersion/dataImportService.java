package dataImport.ChineseVersion;

import static dataImport.ChineseVersion.dataImportDao.*;

/**
 * Created by ACER on 2017/4/19.
 */
public class dataImportService {

    public static void handleXml(String name, String xmlInfo){

        String type = "--";
        String compound = "--";
        String minLen;
        String maxLen;
        String minVal;
        String maxVal;

        String info[]= xmlInfo.split("\\|");
        int infoNum = info.length;
        String compoundAndType = info[0];
        switch (infoNum){
            case 1:
                if (compoundAndType.contains("简单")||compoundAndType.contains("普通")) {
                    compound = "普通类型，基本数据类型";
                    type = compoundAndType.replaceAll("普通类型，基本数据类型：", "");
                    type = type.replaceAll(" |\t|　*", "");
                }
                if (compoundAndType.contains("复合数据元素")) {
                    compound = "复合数据元素";
                    type = compoundAndType.replace("复合数据元素", "");
                    type = type.replaceAll("名称|:|：| |\t|　*", "");
                }
                insertXml(name,compound,type);
                break;
            case 3:
                compound = "普通类型，基本数据类型";
                type = compoundAndType.replaceAll("普通类型，基本数据类型：", "");
                type = type.replaceAll(" |\t|　*", "");
                minLen = info[1].replace("最小长度：","");
                maxLen = info[2].replace("最大长度：","");
                insertXml(name,compound,type,Integer.valueOf(minLen),Integer.valueOf(maxLen));
                break;
            case 5:
                compound = "普通类型，基本数据类型";
                type = compoundAndType.replaceAll("普通类型，基本数据类型：", "");
                type = type.replaceAll(" |\t|　*", "");
                minLen = info[1].replace("最小长度：","");
                maxLen = info[2].replace("最大长度：","");
                minVal = info[3].replace("最小值：","");
                maxVal = info[4].replace("最大值：","");

//                System.out.println(name);
                insertXml(name,compound,type,Integer.valueOf(minLen),Integer.valueOf(maxLen),Integer.valueOf(minVal),Integer.valueOf(maxVal));
                break;
            default:
                System.out.println(name);
                break;
        }
//        System.out.println(compound+"   "+type+"   "+minLen+"   "+maxLen+"   "+minVal+"   "+maxVal);
    }

    public static String handleSubElement(String name, String subElement) {

        String subElementName = "--";
        String required = "--";
        String repeatable = "--";

        if (subElement.contains("--")) {
            return "--";
        } else {
            String info[] = subElement.split("·");
            int infoNum = info.length;
            for (int i = 0; i < infoNum; i++) {
                if (!info[i].equals("") && !info[i].equals(" ")) {
                    String[] sub = info[i].split(",|，");
                    int subNum = sub.length;
                    switch (subNum) {
                        case 1:
                            subElementName = sub[0].replaceAll("\\s","");
                            break;
                        case 2:
                            subElementName = sub[0].replaceAll("\\s","");
                            required = sub[1].replaceAll("\\s","");
                            break;
                        case 3:
                            subElementName = sub[0].replaceAll("\\s","");
                            required = sub[1].replaceAll("\\s","");
                            repeatable = sub[2].replaceAll("\\s","");
                            break;
                        default:
                            System.err.println("subElement error!!");
                    }
//                    System.out.println(subElementName + " " + required + "    " + repeatable);

                    insertSubElement(name,subElementName,required,repeatable);
                }
            }
            return "DATA_DIC_ENG_SUB";
        }
    }

    public static String handleCode(String name,String code){

        String codeName="--";
        String description ="--";
        String specification ="--";

//        System.out.println(code);
        if (code.equals("--")){
//            System.out.println();
            return "--";
        }else if(!code.contains("  ")){
//            System.out.println();
            return code;
        }else{
            String [] info = code.split("\\n");
            int infoNum = info.length;
            int count  = infoNum;

            //将跨行信息并入上一行，以免影响判断
            for (int i = 1; i<infoNum;i++){
                if (!info[i].contains("  ")){
                    int j=i-1;
                    while (j>0&&info[j]==""){
                        j--;
                    }
                    if (j>=0){
                        info[j] = info[j]+info[i];
                        info[i] ="";
                        count--;
                    }
                }
            }

            //删除上一步所剩空行
            String [] newInfo = new String[count];
            int o = 0;
            for (int i = 0; i<infoNum;i++){
                if (!info[i].equals("")){
                    newInfo[o] = info[i];
                    o++;
                }
            }

            for (int i = 0; i<newInfo.length;i++){
                if (newInfo[i].contains("  ")){
                    String [] codeInfo = newInfo[i].split("  ");
                    switch (codeInfo.length){
                        case 2:
                            codeName = codeInfo[0];
                            description = codeInfo[1];
                            break;
                        case 3:
                            codeName = codeInfo[0];
                            description = codeInfo[1];
                            specification = codeInfo[2];
                            break;
                        default:
//                            System.err.println("code error!!");
                            break;
                    }
                    insertCode(name,codeName,description,specification);
                }
            }
//            System.out.println(name+"   "+codeName+"   "+description+"   "+specification);

            return "DATA_DIC_ENG_CODE";
        }

    }
}
