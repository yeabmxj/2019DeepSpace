package frc.team5115.commands.arm;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.Debug;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

import java.util.HashMap;
import java.util.Map;

public class ArmLooper extends Command {

    static NetworkTableEntry levelDisplay;
    Map<String, Object> settings = new HashMap<>();

    public static Subsystem system;
    private boolean kill = false;
    private static boolean manual;


    protected void initialize() {
        system = Robot.arm;
        if(!Robot.arm.verifyGyro()){
            Debug.reportWarning("Gyro not plugged in, switching to manual control!");
            manual = true;
        }
        settings.put("min", 0);
        settings.put("max", 7);
        levelDisplay = Robot.tab.add("Level", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(settings) // specify widget properties here
                .getEntry();
        levelDisplay.setNumber(Math.round(Robot.arm.getCurrentPosition()));
        //system.setState("Moving Up");
    }

    protected void execute(){
        Robot.arm.update();
    }

    public static void addLevel(double level){
        if(!(returnTarget() + level > 7) && !(returnTarget() + level < 0)){
            levelDisplay.setNumber(returnTarget() + level);
        }
    }

    public static int returnTarget(){
        return levelDisplay.getNumber(0).intValue();
    }

    public static boolean isManual(){ return manual;}

    protected boolean isFinished() { return kill; }
}
