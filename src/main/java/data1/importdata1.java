package data1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static data1.dao1.insertSON;


public class importdata1 {

    public static void main(String[] args) {

        getData();
    }

    public static void getData(){
        try {

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\BUSINESS_RULE.txt");
            BufferedReader br = new BufferedReader(reader);

            String str;
            String des;
            String description;
            while((str =br.readLine()) != null) {

                String [] info = str.split("\t");
//                if (info[0].contains("001")){
//                    info[0] = "001";
//                }
                if (info.length<=18) description = "--";
                else description = info[18];

                if (info.length<=17) des = "--";
                else des = info[17];

                insertSON(info[0],info[1],info[2],info[3],info[4],
                        info[5],info[6],info[7],info[8],info[9],
                        info[10],info[11],info[12],info[13],info[14],
                        info[15],info[16],des,description);
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