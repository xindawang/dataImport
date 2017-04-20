package dataImport.tools;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ACER on 2017/4/17.
 */
public class initColumnList {
    public static Queue<String> getColumnList(){
        Queue<String> columnList = new LinkedList<String>();
        columnList.add("DATA ELEMENT NAME");
        columnList.add("TEI / ACRONYM");
        columnList.add("FORMAT");
        columnList.add("XML DATA TYPE");
        columnList.add("SUB DATA ELEMENTS");
        columnList.add("ATTRIBUTE(S)");
        columnList.add("USAGE");
        columnList.add("DESCRIPTION/PURPOSE");
        columnList.add("CODE(S)");
        columnList.add("REMARK(S)");
        columnList.add("EXAMPLE(S)");
        return columnList;
    }

}
