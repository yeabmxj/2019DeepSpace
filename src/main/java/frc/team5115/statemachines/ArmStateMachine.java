package frc.team5115.statemachines;

import frc.team5115.robot.Robot;
import frc.team5115.statemachines.StateMachineBase;

public class ArmStateMachine extends StateMachineBase {
    public static final int MOVING_UP = 1;
    public static final int MOVING_DOWN = 2;
    public static final int NEUTRAL = 0;
    public double target = Robot.arm.level;

    public void update(){
        System.out.println("target " + target);
        System.out.println("angle " + Robot.arm.navX.getRoll());
        System.out.println("position " + Robot.arm.level);
        Robot.arm.getPosition();
        switch(state) {
            case NEUTRAL:
                Robot.arm.moveArm(0);
                break;

            case MOVING_UP:
                if(target < Robot.arm.level){
                    state = MOVING_DOWN;
                }
                if(Robot.arm.level == target){
                    state = NEUTRAL;
                }
                Robot.arm.moveArm(.5);
                break;

            case MOVING_DOWN:
                if(target > Robot.arm.level){
                    state = MOVING_UP;
                }
                if(Robot.arm.level == target){
                    state = NEUTRAL;
                }
                Robot.arm.moveArm(-.5);
                break;
        }
    }
}