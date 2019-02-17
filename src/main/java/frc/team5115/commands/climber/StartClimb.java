package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.command.Command;

public class StartClimb extends Command {

    protected void initialize() {
        ClimberLooper.returnSystem().getTimestamp();
        ClimberLooper.returnSystem().setState("Moving Up");
    }

    protected boolean isFinished(){ return true; }


}
