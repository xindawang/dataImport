package dataImport;

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
                System.err.println("error!");
                break;
        }
        insertXml(compound,type,minLen,maxLen,minVal,maxVal);
//        System.out.println(compound+"   "+type+"   "+minLen+"   "+maxLen+"   "+minVal+"   "+maxVal);
    }

    public static void handleSubElement(String subElement){

    }

    public static void handleCode(String code){

    }
}
