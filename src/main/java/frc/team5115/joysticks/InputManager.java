package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.ArmCommandDown;
import frc.team5115.commands.ArmCommandUp;
import frc.team5115.commands.VacuumSucc;
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
            controllerData = Debug.readJSON(new FileInputStream("home/lvuser/Controllers.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        findControllers();
    }

    public void findControllers(){
        while(tries != 10 && !checkControllers()){
            Timer.delay(1);
            tries++;
            if (tries == 10){
                Debug.reportWarning("Nothing found, assuming controller exists at port 0 with preset binds");
                failsafe();
            }
        }
        createBinds();
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
        return true;
    }

    private void failsafe(){
        primary = new Controller(1);
        secondary = primary;
    }

    public void createBinds(){
        JoystickButton armCommandUp;
        JoystickButton armCommandDown;
        armCommandUp = new JoystickButton(primary.returnInstance(), 1);
        armCommandUp.whenPressed(new ArmCommandUp());
        armCommandDown = new JoystickButton(primary.returnInstance(), 2);
        armCommandDown.whenPressed(new ArmCommandDown());

        JoystickButton nine = new JoystickButton(primary.returnInstance(), 9);
        nine.toggleWhenPressed(new VacuumSucc());
    }

}
