package frc.team5115.commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class Stop extends Command {

    public Stop() {
    	super("Stop");
    }

    protected void initialize() {
        if(DrivetrainLooper.dt.currentState().equals("Driving")){
            if (Timer.getMatchTime() > 10){
                DrivetrainLooper.dt.setState("Transition");
            } else {
                DrivetrainLooper.dt.setState("Stopped");
            }
        }
    }

    protected boolean isFinished() {
        return true;
    }
}