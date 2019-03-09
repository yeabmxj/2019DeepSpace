package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.Debug;
import frc.team5115.Konstanten;
import frc.team5115.commands.arm.*;
import frc.team5115.commands.succ.ToggleSucc;
import frc.team5115.commands.wrist.MoveX;
import frc.team5115.commands.wrist.MoveY;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class InputManager {

    public Joystick joystick;
    public String lastName = "";

    private int forwardAxis;
    private int turnAxis;

    private double throttle = 0.5;
    private String throttleMethod;
    private int throttleAxis;
    private int throttleIncreaseAxis;
    private int throttleDecreaseAxis;
    private int throttleIncrease;
    private int throttleDecrease;

    private int moveUpBind;
    private int moveDownBind;
    private int succBind;
    private int moveLeftBind;
    private int moveRightBind;
    private int moveYBind;

    private JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/deploy/Controllers.json"));
        } catch (FileNotFoundException file){
            Debug.reportWarning("Controller file not found on roborio!");
        } catch (IOException io){
            Debug.reportWarning("Controller file corrupted!");
        } catch (JSONException json){
            Debug.reportWarning("Controller file not formatted properly!");
        }
    }
    
    public void findController() throws JSONException {
        joystick = new Joystick(0);
        if(!sameStick()){
            System.out.println("last joystick name doesn't match, resetting...");
            lastName = joystick.getName();
            JSONObject controller = controllerData.getJSONObject(joystick.getName());

            forwardAxis = controller.getInt("Forward");
            turnAxis = controller.getInt("Turn");
            throttleMethod = controller.getString("Throttle Method");
            switch(throttleMethod){
                case "Dedicated Axis":
                    throttleAxis = controller.getInt("Throttle Axis");
                    break;
                case "Triggers":
                    throttleIncrease = controller.getInt("Throttle Increase");
                    throttleDecrease = controller.getInt("Throttle Decrease");
                    break;
                case "Analog Triggers":
                    throttleIncreaseAxis = controller.getInt("Throttle Increase Axis");
                    throttleDecreaseAxis = controller.getInt("Throttle Decrease Axis");
                    break;
            }
            moveUpBind = controller.getInt("Move Up Bind");
            moveDownBind = controller.getInt("Move Down Bind");
            succBind = controller.getInt("Toggle Vacuum");
            moveLeftBind = controller.getInt("Move Left Bind");
            moveRightBind = controller.getInt("Move Right Bind");
            moveYBind = controller.getInt("Move Y Bind");
            createBinds();
        }
    }

    public boolean sameStick(){
        return lastName.equals(joystick.getName());
    }
    
    public void createBinds(){
        System.out.println("buttons rebound!");
        ButtonWrapper moveUp = new ButtonWrapper(joystick, moveUpBind);
        ButtonWrapper moveDown = new ButtonWrapper(joystick, moveDownBind);

        if(ArmLooper.isManual()){
            System.out.println("using manual");
            moveUp.whileHeld(new ManualUp());
            moveDown.whileHeld(new ManualDown());
        } else {
            System.out.println("using automatic");
            moveUp.whenPressed(new MoveUp());
            moveDown.whenPressed(new MoveDown());
        }

        ButtonWrapper succ = new ButtonWrapper(joystick, succBind);
        succ.toggleWhenPressed(new ToggleSucc());

//        JoystickButton climb = new JoystickButton(joystick, scanBind);
//        climb.whileHeld(new StartClimb());

        //TEMPORARY, TO BE SWITCHED
        ButtonWrapper moveLeft = new ButtonWrapper(joystick, moveLeftBind);
        moveLeft.whileHeld(new MoveX("Move Left"));

        ButtonWrapper moveRight = new ButtonWrapper(joystick, moveRightBind);
        moveRight.whileHeld(new MoveX("Move Right"));

        ButtonWrapper moveY = new ButtonWrapper(joystick, moveYBind);
        moveY.whileHeld(new MoveY());

    }

    public double processThrottle(){
        switch(throttleMethod){
            case "Dedicated Axis":
                throttle = (-joystick.getRawAxis(throttleAxis) + 1) / 2;
                break;
            case "Triggers":
                if(joystick.getRawButton(throttleIncrease)){
                    throttle += Konstanten.TRIGGER_RATE;
                } else if (joystick.getRawButton(throttleDecrease)){
                    throttle -= Konstanten.TRIGGER_RATE;
                }
                break;
            case "Analog Triggers":
                throttle += Konstanten.ANALOG_RATE *(joystick.getRawAxis(throttleIncreaseAxis) - joystick.getRawAxis(throttleDecreaseAxis));
                break;
        }

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

    public double getLeft(){
        return deadband(-joystick.getRawAxis(forwardAxis) + joystick.getRawAxis(turnAxis));
    }

    public double getRight(){
        return deadband(-joystick.getRawAxis(forwardAxis) - joystick.getRawAxis(turnAxis));
    }

    private double deadband(double val){
        if(val <= Konstanten.DEADBAND && val >= Konstanten.DEADBAND){
            return 0;
        }

        return val;
    }

    public boolean debugRawButton(int button) { return joystick.getRawButton(button); }

}
