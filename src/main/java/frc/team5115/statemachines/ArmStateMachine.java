package frc.team5115.statemachines;

import frc.team5115.robot.Robot;
import frc.team5115.statemachines.StateMachineBase;

public class ArmStateMachine extends StateMachineBase {
    public static final int MOVING_UP = 1;
    public static final int MOVING_DOWN = 2;
    public static final int NEUTRAL = 0;
    public double target = Robot.armSubsystem.level;

    public void update(){
        System.out.println("target " + target);
        System.out.println("angle " + Robot.armSubsystem.navX.getRoll());
        System.out.println("position " + Robot.armSubsystem.level);
        Robot.armSubsystem.getPosition();
        switch(state) {
            case NEUTRAL:
                Robot.armSubsystem.moveArm(0);
                break;

            case MOVING_UP:
                if(target < Robot.armSubsystem.level){
                    state = MOVING_DOWN;
                }
                if(Robot.armSubsystem.level == target){
                    state = NEUTRAL;
                }
                Robot.armSubsystem.moveArm(.5);
                break;

            case MOVING_DOWN:
                if(target > Robot.armSubsystem.level){
                    state = MOVING_UP;
                }
                if(Robot.armSubsystem.level == target){
                    state = NEUTRAL;
                }
                Robot.armSubsystem.moveArm(-.5);
                break;
        }
    }
}