package frc.team5115.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.joysticks.InputManager;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Wrist;



public class MoveWrist extends Command {

protected boolean isFinished(){
        return false;
    }

    public void move() {


        if (Robot.im.debugPOV(-1))
        {
            System.out.println("not Moving");
            Robot.wrist.dontMove();
        } else if (Robot.im.debugPOV(90))
        {
            System.out.println("Moving left");
            Robot.wrist.moveLeft();
        } else if (Robot.im.debugPOV(180))
        {
            Robot.wrist.moveDown();
        } else if (Robot.im.debugPOV(270))
        {
            System.out.println("Moving right");
            Robot.wrist.moveRight();
        } else if (Robot.im.debugPOV(0))
        {
            System.out.println("moving up");
            Robot.wrist.moveUp();
        }

}
    protected void execute()
    {
        move();
    }


}
