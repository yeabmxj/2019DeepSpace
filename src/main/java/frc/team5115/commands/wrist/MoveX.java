package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveX extends Command {

    String direction;

    public MoveX(String direction){
        direction = this.direction;
    }

    protected void initialize() {
        WristLooper.system.currentState();
        WristLooper.system.setState("Move Right");
    }

    protected void end(){
        WristLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return false; }

}
