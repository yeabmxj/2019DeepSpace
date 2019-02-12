package frc.team5115.commands.arm;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Arm;

import java.util.HashMap;
import java.util.Map;

public class ArmLooper extends Command {

    public static Arm arm;

    static NetworkTableEntry levelDisplay;
    Map<String, Object> settings = new HashMap<String, Object>();


    protected void initialize() {
        arm = new Arm();
        arm.setState("Stopped");
        settings.put("min", 0);
        settings.put("max", 3);
        levelDisplay = Robot.tab.add("Level", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(settings) // specify widget properties here
                .getEntry();
    }

    public static void addLevel(int level){
        levelDisplay.setNumber(levelDisplay.getNumber(0).intValue() + level);
    }

    public static int returnTarget(){
        return levelDisplay.getNumber(0).intValue();
    }

    protected void execute(){arm.update();}

    protected void interrupted(){arm.setState("Stopped");}

    protected boolean isFinished() { return false; }
}
