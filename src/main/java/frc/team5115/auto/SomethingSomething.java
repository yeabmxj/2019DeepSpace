package frc.team5115.auto;

import frc.team5115.statemachines.StateMachineBase;
import frc.team5115.subsystems.Drivetrain;

public class SomethingSomething extends StateMachineBase {

    public static final int DRIVING = 1;

    Drivetrain driving;
    TAS_James TAS;

    String[] values;

    public SomethingSomething(){
        driving = new Drivetrain();
        TAS = new TAS_James("/media/sda1/test.csv");
    }

    public void update(){
        switch(state){
            case DRIVING:
                if(TAS.isFinished()){
                    setState(STOP);
                }
                values = TAS.getValues();
                driving.drive(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]));
                break;
            case STOP:
                driving.drive(0,0, 0);
                System.out.println("finished");
                break;
        }
    }
}
