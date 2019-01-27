package frc.team5115.auto;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TAS_James {

    boolean finished;
    CSVReader csvReader;
    String[] nextRecord;

    public TAS_James(String pathname){
        try {
            csvReader = new CSVReader(new FileReader(pathname));
            finished = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finished = true;
        }
    }
    public String[] getValues(){
        try {
            nextRecord = csvReader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
            if(nextRecord != null){
                //System.out.println("valid");
                //System.out.println(nextRecord);
                //System.out.println(nextRecord[0] + " " + nextRecord[1]);
                return nextRecord;
            } else {
                System.out.println("null found... possibly end of recording!");
                finished = true;
            }
        return new String[]{"0", "0"};
    }

    public boolean isFinished(){
        return finished;
    }

}
