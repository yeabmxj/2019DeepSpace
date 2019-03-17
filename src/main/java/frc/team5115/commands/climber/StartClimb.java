package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.command.Command;

public class StartClimb extends Command {

    protected void initialize(){
        ClimberLooper.system.setState("Going Up");
    }

    protected boolean isFinished(){ return true; }


}
