package frc.team5115.statemachines;

import frc.team5115.robot.Robot;

public class VacuumStateMachine extends StateMachineBase {

    public static final int BLOWING = 2;
    public static final int SUCKING = 1;
    public static final int OFF = 0;

    public void update() {
        switch(state) {
            /**FOR TESTING**/
//            case BLOWING:
//                Robot.vacSubsystem.armDown();
//                break;
            case SUCKING:
                Robot.vacSubsystem.succ();
                break;
            case OFF:
                Robot.vacSubsystem.solonoidOPen();
                Robot.vacSubsystem.stop();
                if(compareTime(1)) {
                    Robot.vacSubsystem.solonoidClosed();
                }
                break;
        }
    }
}