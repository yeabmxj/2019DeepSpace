package frc.team5115;

public class Konstanten {

    //Input
    public static final double TRIGGER_RATE = 0.005;
    public static final double ANALOG_RATE = 0.01;
    public static final double DEADBAND = 0.075;


    //Arm
    public static final int DART_ID = 2;
    public static final int TOP_SWITCH = 8;
    public static final int BOTTOM_SWITCH = 9;
    private static final double MAX_HEIGHT = 60;
    private static final double MAX_SCALED = 3;
    private static final double MIN_HEIGHT = -60;
    private static final double MIN_SCALED = 0;
    public static final double SLOPE = (MAX_SCALED - MIN_SCALED) / (MAX_HEIGHT - MIN_HEIGHT);
    public static final double Y_INTERCEPT = MAX_SCALED - (SLOPE * MAX_HEIGHT);

    //Climber
    public static final int FRONT_CLIMBER = 5;
    public static final int BACK_CLIMBER = 0;

    //Drivetrain
    public static final int FRONT_LEFT_DRIVE = 2;
    public static final int FRONT_RIGHT_DRIVE = 1;
    public static final int BACK_LEFT_DRIVE = 4;
    public static final int BACK_RIGHT_DRIVE = 3;

    //Vacuum
    public static final int VACUUM_SPARK = 9;
    public static final int SOLENOID_RELAY = 0;

    //Wrist
    public static final int WRIST_X = 1;
    public static final int WRIST_Y = 0;
    public static final int LEFT_SWITCH = 7;
    public static final int RIGHT_SWTICH = 6;
}
