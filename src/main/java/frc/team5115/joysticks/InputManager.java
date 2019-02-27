package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.arm.*;
import frc.team5115.commands.climber.StartClimb;
import frc.team5115.commands.succ.ToggleSucc;
import frc.team5115.commands.wrist.MoveX;
import frc.team5115.commands.wrist.MoveY;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;


public class InputManager {

    public static Controller primary;
    public static Controller secondary;

    private int primaryPort = 0;
    private int secondaryPort = -1;

    private int tries = 0;

    private JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/Controllers.json"));
        } catch (Exception e) {
            Debug.reportError("Controllers data file is not on the roborio!!!", e);
        }
        findControllers();
        createBinds();
    }

    public void findControllers(){
        while(tries != 10 && !checkControllers()){
            Timer.delay(1);
            Debug.reportWarning("Controllers not found! try no. " + tries);
            tries++;
            if (tries == 10){
                Debug.reportWarning("Nothing found, assuming controller exists at port 0 with preset binds");
                primary = new Controller(0);
                secondary = primary;
            }
        }
    }

    private boolean checkControllers() {
        primary = null;
        secondary = null;
        for(int i = 0; i < 5; i++){
            if((!new Joystick(i).getName().equals("")) && (new Joystick(i).getButtonCount() > 0)){
                primaryPort = i;
                break;
            }
        }
        for(int i = primaryPort + 1; i < 5; i++){
            if((!new Joystick(i).getName().equals("") || !(new Joystick(primaryPort).getName().equals("MAYFLASH GameCube Controller Adapter"))) && (new Joystick(i).getButtonCount() > 0)){
                secondaryPort = i;
                break;
            }
        }
        try {
            primary = new Controller(primaryPort, controllerData.getJSONObject(new Joystick(primaryPort).getName()));
            if(secondaryPort == -1){
                secondary = primary;
            } else {
                secondary = new Controller(secondaryPort, controllerData.getJSONObject(new Joystick(secondaryPort).getName()));
            }
        } catch (JSONException e) {
            return false;
        }
        Debug.reportWarning("Controllers found at ports: " + primaryPort + " and " + secondaryPort);
        return true;
    }
    
    public void createBinds(){
        JoystickButton moveUp = new JoystickButton(secondary.returnInstance(), 4);
        JoystickButton moveDown = new JoystickButton(secondary.returnInstance(), 2);

        if(ArmLooper.isManual()){
            System.out.println("using manual");
            moveUp.whileHeld(new ManualUp());
            moveDown.whileHeld(new ManualDown());
        } else {
            System.out.println("using automatic");
            moveUp.whenPressed(new MoveUp());
            moveDown.whenPressed(new MoveDown());
        }

        JoystickButton succ = new JoystickButton(secondary.returnInstance(), 3);
        succ.toggleWhenPressed(new ToggleSucc());

        JoystickButton climb = new JoystickButton(secondary.returnInstance(), 5);
        climb.whenPressed(new StartClimb());

        POVButton moveLeft = new POVButton(secondary.returnInstance(), 90);
        moveLeft.whileHeld(new MoveX("Move Left"));

        POVButton moveRight = new POVButton(secondary.returnInstance(), 270);
        moveRight.whileHeld(new MoveX("Move Right"));

        POVButton moveY = new POVButton(secondary.returnInstance(), 0);
        moveY.whenPressed(new MoveY());

    }

}
