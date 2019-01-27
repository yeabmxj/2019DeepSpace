package frc.team5115.auto;

import com.opencsv.CSVWriter;
import edu.wpi.first.wpilibj.Timer;
import frc.team5115.robot.Robot;
import frc.team5115.statemachines.StateMachineBase;

import java.io.FileWriter;
import java.io.IOException;

public class CSVRecorder extends StateMachineBase {

    public static final int INIT = 1;
    public static final int RECORDING = 2;
    public static final int FINISHED = 3;

    CSVWriter writer;
    double time;
    String x;
    String y;

    public void update(){
        switch (state) {
            case INIT:
                try {
                    writer = new CSVWriter(new FileWriter("/media/sda1/test.csv"));
                } catch (IOException e) {
                    System.out.println("could not create file!");
                    e.printStackTrace();
                    setState(FINISHED);
                }
                time = Timer.getFPGATimestamp();
                setState(RECORDING);
                break;
            case RECORDING:
                if(Timer.getFPGATimestamp() > time + 15){
                    setState(FINISHED);
                }
                writer.writeNext(new String[] {String.valueOf(0), String.valueOf(0), String.valueOf(0)});
                break;
            case FINISHED:
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("flush failed!");
                }
                System.out.println("recording finished... 15 seconds have passed!");
                setState(STOP);
                break;

            case STOP:
                break;
        }
    }

}
