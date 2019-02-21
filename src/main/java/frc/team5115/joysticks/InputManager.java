package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.arm.MoveDown;
import frc.team5115.commands.arm.MoveUp;
import frc.team5115.commands.climber.StartClimb;
import frc.team5115.commands.succ.ToggleSucc;
import frc.team5115.commands.wrist.MoveLeft;
import frc.team5115.commands.wrist.MoveRight;
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

    JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("home/lvuser/Controllers.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(tries != 10){
            Timer.delay(1);
            try {
                checkControllers();
                Debug.getInstance().reportWarning("Controllers found at ports " + primaryPort + " and " + secondaryPort, false);
                tries = 0;
                break;
            } catch (JSONException e){
                Debug.getInstance().reportWarning("Controllers not found, try no. " + tries, false);
                tries++;
            } finally {
                if (tries == 10){
                    Debug.getInstance().reportWarning("Nothing found, assuming controller exists at port 0 with preset binds", false);

                }
            }
        }
    }

    private void findControllers(){
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
        System.out.println(primaryPort);
        System.out.println(secondaryPort);
    }

    public void checkControllers() throws JSONException {
        primary = null;
        secondary = null;
        findControllers();
            primary = new Controller(primaryPort, controllerData.getJSONObject(new Joystick(primaryPort).getName()));
            if(secondaryPort == -1){
                secondary = primary;
            } else {
                secondary = new Controller(secondaryPort, controllerData.getJSONObject(new Joystick(secondaryPort).getName()));
            }

            JoystickButton test = new JoystickButton(primary.returnInstance(), 4);
            test.whenPressed(new MoveUp());

            JoystickButton test2 = new JoystickButton(primary.returnInstance(), 2);
            test2.whenPressed(new MoveDown());

            JoystickButton succ = new JoystickButton(primary.returnInstance(), 3);
            succ.toggleWhenPressed(new ToggleSucc());

            JoystickButton climb = new JoystickButton(primary.returnInstance(), 5);
            climb.whenPressed(new StartClimb());

            POVButton moveLeft = new POVButton(primary.returnInstance(), 90);
            moveLeft.whileHeld(new MoveLeft());

            POVButton moveRight = new POVButton(primary.returnInstance(), 270);
            moveRight.whileHeld(new MoveRight());

            POVButton moveY = new POVButton(primary.returnInstance(), 0);
            moveY.whenPressed(new MoveY());

    }

    public void failsafe(){
        primary = new Controller(1);
        secondary = primary;
    }

    public Controller getPrimary(){
        return primary;
    }

    public Controller getSecondary(){
        return secondary;
    }

}
