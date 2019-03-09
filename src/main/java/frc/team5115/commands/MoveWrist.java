package frc.team5115.commands;
import frc.team5115.joysticks.InputManager;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Wrist;


public class MoveWrist extends Wrist {

    public MoveWrist()
    {
    }



    public void move() {


        if (Robot.im.debugPOV(-1))
        {
            System.out.println("not Moving");
            dontMove();
        } else if (Robot.im.debugPOV(90))
        {
            System.out.println("Moving left");
            moveLeft();
        } else if (Robot.im.debugPOV(180))
        {
            moveDown();
        } else if (Robot.im.debugPOV(270))
        {
            System.out.println("Moving right");
            moveRight();
        } else if (Robot.im.debugPOV(0))
        {
            System.out.println("moving up");
            moveUp();
        }
}}
