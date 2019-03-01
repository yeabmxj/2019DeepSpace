package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveX extends Command {

    String pos;

    public MoveX(String state){
        pos = state;
    }

    protected void initialize() {
        WristLooper.system.setState(pos);
    }

    protected void end(){
        WristLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return false; }

}