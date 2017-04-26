package data2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static data2.dao2.insertSON;


public class importdata2 {

    public static void main(String[] args) {

        getData();
    }

    public static void getData(){
        try {
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\STANDARD_OBSERVATION_NUMBERS.txt");
            BufferedReader br = new BufferedReader(reader);

            String str;
            int count=0;
            while((str =br.readLine()) != null) {

                String [] info = str.split("\t");
                if (info[0].contains("001")){
                    info[0] = "001";
                }
                insertSON(info[0],info[1],info[2]);
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