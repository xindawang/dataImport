package data2;

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
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("D:\\航空工业\\数据库\\TABLE\\STANDARD_OBSERVATION_NUMBERS.txt");
            BufferedReader br = new BufferedReader(reader);

            String str;
            int count=0;
            while((str =br.readLine()) != null) {

                String [] info = str.split("\t");
                System.out.println(info[0]);
                int i  = info[0].length();
                if (i>count){
                    count = i;
                }
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