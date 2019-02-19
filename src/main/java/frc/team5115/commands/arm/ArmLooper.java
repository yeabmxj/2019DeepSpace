package frc.team5115.commands.arm;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.Debug;
import frc.team5115.commands.Looper;
import frc.team5115.robot.Robot;

import java.util.HashMap;
import java.util.Map;

public class ArmLooper extends Looper {

    static NetworkTableEntry levelDisplay;
    Map<String, Object> settings = new HashMap<String, Object>();


    protected void initialize() {
        system = Robot.arm;
        if(!Robot.arm.gyroStatus()){
            Debug.getInstance().reportError("Gyro unplugged, disabling arm!", false);
            this.cancel();
        }
        system.setState("Stopped");
        settings.put("min", 0);
        settings.put("max", 3);
        levelDisplay = Robot.tab.add("Level", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(settings) // specify widget properties here
                .getEntry();
        levelDisplay.setNumber(Robot.arm.getCurrentPosition());
    }

    public static void addLevel(int level){
        if(!(returnTarget() + level > 3) && !(returnTarget() + level < 0)){
            levelDisplay.setNumber(returnTarget() + level);
        }
    }

    public static int returnTarget(){
        return levelDisplay.getNumber(0).intValue();
    }
}
