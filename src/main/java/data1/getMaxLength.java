package data1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class getMaxLength {

    public static void main(String[] args) {

        getData();
    }

    public static void getData(){
        try {

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\BUSINESS_RULE.txt");
            BufferedReader br = new BufferedReader(reader);

            String str;
            int count=0;
            while((str =br.readLine()) != null) {

                String [] info = str.split("\t");
//                if (info.length>16){
                    System.out.println(info[16]);
                    int i  = info[16].length();
                    if (i>count){
                        count = i;
                    }
//                }

            }
            System.out.println(count);

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