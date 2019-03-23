package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.commands.drivetrain.DrivetrainLooper;

public class StartClimb extends Command {

    protected void initialize(){
        ClimberLooper.system.setState("Going Up");
        DrivetrainLooper.system.setState("Climber Start");
    }

    protected boolean isFinished(){ return true; }


}
