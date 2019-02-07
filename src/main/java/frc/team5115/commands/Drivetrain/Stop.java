package frc.team5115.commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class Stop extends Command {

    public Stop() {
    	super("Stop");
    }

    protected void initialize() {
        if(Robot.dt.currentState().equals("Driving")){
            if (Timer.getMatchTime() > 10){
                Robot.dt.setState("Transition");
            } else {
                Robot.dt.setState("Stop");
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }
}