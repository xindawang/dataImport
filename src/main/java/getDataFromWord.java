
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class getDataFromWord {
    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream(new File("2003.doc"));
            WordExtractor ex = new WordExtractor(is);
            String text2003 = ex.getText();
            System.out.println(text2003);

            OPCPackage opcPackage = POIXMLDocument.openPackage("2007.docx");
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String text2007 = extractor.getText();
            System.out.println(text2007);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}