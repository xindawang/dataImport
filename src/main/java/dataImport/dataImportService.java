package dataImport;

import static dataImport.dataImportDao.createNewCode;
import static dataImport.dataImportDao.createNewSubElement;
import static dataImport.dataImportDao.insertXml;

/**
 * Created by ACER on 2017/4/19.
 */
public class dataImportService {

    public static void handleXml(String xmlInfo){

        String type = null;
        String compound = null;
        String minLen = null;
        String maxLen = null;
        String minVal = null;
        String maxVal =null;

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
                break;
            case 4:
                compound = "simpleType, basic data type";
                type = compoundAndType.replace("simpleType, basic data type: ", "");
                minLen = info[2].replace("minimum length: ","");
                maxLen = info[3].replace("maximum length: ","");
                break;
            case 6:
                compound = "simpleType, basic data type";
                type = compoundAndType.replace("simpleType, basic data type: ", "");
                minLen = info[2].replace("minimum length: ","");
                maxLen = info[3].replace("maximum length: ","");
                minVal = info[4].replace("minimum value: ","");
                maxVal = info[5].replace("maximum value: ","");
                break;
            default:
                System.err.println("xml error!");
                break;
        }
        insertXml(compound,type,minLen,maxLen,minVal,maxVal);
    }

    public static String handleSubElement(String name, String subElement) {

        String subElementName = null;
        String required = null;
        String repeatable = null;

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
                            required = sub[1];
                            break;
                        case 3:
                            subElementName = sub[0];
                            required = sub[1];
                            repeatable = sub[2];
                            break;
                        default:
                            System.err.println("subElement error!!");
                    }
//                    System.out.println(subElementName + " " + required + "    " + repeatable);
                    createNewSubElement(name,subElementName,required,repeatable);
                }
            }
            return name+"SubElement";
        }
    }

    public static String handleCode(String name,String code){

        String codeName=null;
        String description =null;
        String specification =null;

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
                if (info[i].contains("  ")){
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
                }
            }
//            System.out.println(name+"   "+codeName+"   "+description+"   "+specification);
            createNewCode(name,codeName,description,specification);
            return name +"Code";
        }

    }
}
