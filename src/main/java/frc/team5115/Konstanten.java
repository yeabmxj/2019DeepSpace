package frc.team5115;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Konstanten {

    public static ShuffleboardTab tab = Shuffleboard.getTab("main");

    //Input
    public static final double TRIGGER_RATE = 0.005;
    public static final double ANALOG_RATE = 0.01;
    public static final double DEADBAND = 0.2;

    //Arm
    public static final int DART_ID = 2;
    public static final int TOP_SWITCH = 8;
    public static final int BOTTOM_SWITCH = 9;
    public static final double ARM_THRESHOLD = 0.0075;
    private static final double MAX_HEIGHT = 55;
    public static final double MAX_SCALED = 3;
    private static final double MIN_HEIGHT = -60;
    public static final double MIN_SCALED = 0;
    public static final double LEVEL1BOTTOM = 0.25;
    //actual level 1 .45
    public static final double LEVEL1TOP = 0.4;
    public static final double LEVEL2 = 0.75;
    public static final double BALL_PICKUP = 1.45;
    public static final double LEVEL3 = 1.6;
    public static final double LEVEL4 = 1.75;
    public static final double LEVEL5 = 2.65;
    public static final double LEVEL6 = 2.85;
    public static final double SLOPE = (MAX_SCALED - MIN_SCALED) / (MAX_HEIGHT - MIN_HEIGHT);
    public static final double Y_INTERCEPT = MAX_SCALED - (SLOPE * MAX_HEIGHT);

    //Climber
    //relative to the back of the bot(direction we climb)
    public static final int FRONT_CLIMBER = 5;
    public static final int BACK_CLIMBER = 6;

    //Drivetrain
    public static final int FRONT_LEFT_DRIVE = 2;
    public static final int FRONT_RIGHT_DRIVE = 1;
    public static final int BACK_LEFT_DRIVE = 4;
    public static final int BACK_RIGHT_DRIVE = 3;
    //meters
    public static final double BASE_WIDTH = .5842;
    public static final int TICK_COUNT = 1440;
    public static final double WHEEL_RADIUS = .1524;

    //Limelight
    public static final double CAMERA_HEIGHT = 7.5; // Height of limelight relative to the ground
    public static final double TARGET_HEIGHT = 36; //Height of the target(s) in IN relative to the ground
    public static final double CAMERA_ANGLE = 24; // Angle of limelight

    //Vacuum
    public static final int VACUUM_SPARK = 9;
    public static final int SOLENOID_RELAY = 3;

    //Wrist
    public static final int WRIST_X = 1;
    public static final int WRIST_Y = 0;
    public static final int LEFT_SWITCH = 7;
    public static final int RIGHT_SWTICH = 6;

    //Debug
    public static final int CAN_COUNT = 9;
    public static final double VOLTAGE_THRESHOLD = 2.5;
    public static final double MOTOR_VOLTAGE_THRESHOLD = 4;
}
