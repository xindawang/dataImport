package dataImport.EnglishVersion;

import static dataImport.EnglishVersion.dataImportDao.*;

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
        String compoundAndType = info[1];
        switch (infoNum){
            case 2:
                if (compoundAndType.contains("simpleType, basic data type: ")) {
                    compound = "simpleType, basic data type";
                    type = compoundAndType.replace("simpleType, basic data type: ", "");
                }
                if (compoundAndType.contains("compound data element: ")) {
                    compound = "compound data element";
                    type = compoundAndType.replace("compound data element: ", "");
                }
               insertXml(name,compound,type);
                break;
            case 4:
                compound = "simpleType, basic data type";
                type = compoundAndType.replace("simpleType, basic data type: ", "");
                minLen = info[2].replace("minimum length: ","");
                maxLen = info[3].replace("maximum length: ","");
                insertXml(name,compound,type,Integer.valueOf(minLen),Integer.valueOf(maxLen));
                break;
            case 6:
                compound = "simpleType, basic data type";
                type = compoundAndType.replace("simpleType, basic data type: ", "");
                minLen = info[2].replace("minimum length: ","");
                maxLen = info[3].replace("maximum length: ","");
                minVal = info[4].replace("minimum value: ","");
                maxVal = info[5].replace("maximum value: ","");
                insertXml(name,compound,type,Integer.valueOf(minLen),Integer.valueOf(maxLen),Integer.valueOf(minVal),Integer.valueOf(maxVal));
                break;
            default:
                System.err.println("xml error!");
                break;
        }
    }

    public static String handleSubElement(String name, String subElement) {

        String subElementName = "--";
        String required = "--";
        String repeatable = "--";

        if (subElement.equals("--")) {
            return "--";
        } else {
            String info[] = subElement.split("•  ");
            int infoNum = info.length;
            for (int i = 0; i < infoNum; i++) {
                if (!info[i].equals("") && !info[i].equals(" ")) {
                    String[] sub = info[i].split(",");
                    int subNum = sub.length;
                    switch (subNum) {
                        case 1:
                            subElementName = sub[0];
                            break;
                        case 2:
                            subElementName = sub[0];
                            required = sub[1].replace("\\s","");
                            break;
                        case 3:
                            subElementName = sub[0];
                            required = sub[1].replace("\\s","");
                            repeatable = sub[2].replace("repeatable ","");
                            break;
                        default:
                            System.err.println("subElement error!!");
                    }
//                    System.out.println(subElementName + " " + required + "    " + repeatable);
                    subElementName = subElementName.replace("•","");
                    subElementName = subElementName.replace("\\s","");
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

        if (code.equals("--")){
            return "--";
        }else if(!code.contains("  ")){
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
                        info[j] = info[j]+" "+info[i];
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
                            System.err.println("code error!!");
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
