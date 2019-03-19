package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class ToggleMode extends Command {

    protected void initialize(){
        Robot.arm.toggleManual();
    }

    protected boolean isFinished(){
        return true;
    }
}
