package frc.team5115.commands.arm;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

import java.util.HashMap;
import java.util.Map;

public class ArmLooper extends Command {

    static NetworkTableEntry levelDisplay;
    Map<String, Object> settings = new HashMap<String, Object>();

    public static Subsystem system;
    boolean kill = false;

    public static Subsystem returnSystem(){
        return system;
    }

    protected void execute(){
        system.update();
    }

    protected void end(){kill = true;}

    protected boolean isFinished() { return kill; }

    protected void initialize() {
        system = Robot.arm;
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
