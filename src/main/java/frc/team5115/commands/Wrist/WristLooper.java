package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Wrist;

public class WristLooper extends Command {

    public static Wrist wr;

    protected void initialize(){
        wr = new Wrist();
        wr.setState("Stopped");
    }

    protected void execute() {
        wr.update();
    }

    protected void interrupted(){
        wr.setState("Stopped");
    }

    protected boolean isFinished() {
        return false;
    }

}
