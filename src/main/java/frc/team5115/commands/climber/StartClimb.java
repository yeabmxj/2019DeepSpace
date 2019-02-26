package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class StartClimb extends Command {

    boolean climb = false;
    double time;

    protected void initialize() {
        time = Timer.getFPGATimestamp();
    }

    protected void execute(){
        if(Timer.getFPGATimestamp() > time + 3){
            ClimberLooper.system.getTimestamp();
            ClimberLooper.system.setState("Moving Up");
            climb = true;
        }
    }

    protected void interrupted(){
        ClimberLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return climb; }


}
