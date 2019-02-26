package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class ManualDown extends Command {

    protected void initialize(){ArmLooper.system.setState("Manual Down");}

    protected void end(){ArmLooper.system.setState("Stopped"); }

    protected boolean isFinished(){return false;}

}
