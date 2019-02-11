package frc.team5115.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.subsystems.Arm;

public class ArmLooper extends Command {

    public static Arm arm;

    protected void initialize(){
        arm = new Arm();
        arm.setState("Stopped");
    }

    protected void execute(){arm.update();}

    protected void interrupted(){arm.setState("Stopped");}

    protected boolean isFinished() { return false; }
}
