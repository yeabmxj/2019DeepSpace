package frc.team5115.commands.arm;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.Konstanten;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

import java.util.HashMap;
import java.util.Map;

public class ArmLooper extends Command {

    static NetworkTableEntry levelDisplay;

    public static Subsystem system;
    private boolean kill = false;
    public static int lastTarget;


    protected void initialize() {
        system = Robot.arm;
        levelDisplay = Konstanten.tab.add("Level", 0).getEntry();
        levelDisplay.setNumber(0);
    }

    protected void execute(){
        Robot.arm.update();
    }

    public static void addLevel(double level){
        lastTarget = returnTarget();
        if(!(returnTarget() + level > 7) && !(returnTarget() + level < 0)){
            levelDisplay.setNumber(returnTarget() + level);
        }
    }

    public static int returnTarget(){
        return levelDisplay.getNumber(0).intValue();
    }

    public static int returnLastTarget(){return lastTarget;}

    protected boolean isFinished() { return kill; }
}
