package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class Stupidd extends Command {

    protected void initialize() {
        ArmLooper.system.setState("Moving Up");
        System.out.println("command received");
    }

    protected void end(){
        ArmLooper.system.setState("Neutral");
        System.out.println("command ended");
    }

    protected boolean isFinished(){ return true; }

}
