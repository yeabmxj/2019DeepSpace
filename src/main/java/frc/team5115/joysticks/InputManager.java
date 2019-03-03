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

import java.io.FileInputStream;


public class InputManager {

    public Joystick joystick;

    private int forwardAxis;
    private int turnAxis;

    private double throttle = 0.5;
    private String throttleMethod;
    private int throttleAxis;
    private int throttleIncreaseAxis;
    private int throttleDecreaseAxis;
    private int throttleIncrease;
    private int throttleDecrease;

    private int scanBind;
    private int moveUpBind;
    private int moveDownBind;
    private int succBind;
    private int moveLeftBind;
    private int moveRightBind;
    private int moveYBind;

    private JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/Controllers.json"));
        } catch (Exception e) {
            Debug.reportError("Controllers data file is not on the roborio!!!", e);
        }
    }
    
    public void findController() throws JSONException {
        joystick = new Joystick(0);
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
        scanBind = controller.getInt("Scan Bind");
        moveUpBind = controller.getInt("Move Up Bind");
        moveDownBind = controller.getInt("Move Down Bind");
        succBind = controller.getInt("Toggle Vacuum");
        moveLeftBind = controller.getInt("Move Left Bind");
        moveRightBind = controller.getInt("Move Right Bind");
        moveYBind = controller.getInt("Move Y Bind");
    }
    
    public void createBinds(){
        JoystickButton moveUp = new JoystickButton(joystick, moveUpBind);
        JoystickButton moveDown = new JoystickButton(joystick, moveDownBind);

        if(!ArmLooper.isManual()){
            System.out.println("using manual");
            moveUp.whileHeld(new ManualUp());
            moveDown.whileHeld(new ManualDown());
        } else {
            System.out.println("using automatic");
            moveUp.whenPressed(new MoveUp());
            moveDown.whenPressed(new MoveDown());
        }

        JoystickButton succ = new JoystickButton(joystick, succBind);
        succ.toggleWhenPressed(new ToggleSucc());

//        JoystickButton climb = new JoystickButton(joystick, scanBind);
//        climb.whileHeld(new StartClimb());

        POVButton moveLeft = new POVButton(joystick, moveLeftBind);
        moveLeft.whileHeld(new MoveX("Move Left"));

        POVButton moveRight = new POVButton(joystick, moveRightBind);
        moveRight.whileHeld(new MoveX("Move Right"));

        POVButton moveY = new POVButton(joystick, moveYBind);
        moveY.whileHeld(new MoveY());

    }

    public double processThrottle(){
        switch(throttleMethod){
            case "Dedicated Axis":
                throttle = (-joystick.getRawAxis(throttleAxis) + 1) / 2;
                break;
            case "Triggers":
                if(joystick.getRawButton(throttleIncrease)){
                    throttle += 0.005;
                } else if (joystick.getRawButton(throttleDecrease)){
                    throttle -= 0.005;
                }
                break;
            case "Analog Triggers":
                throttle += 0.01 *(joystick.getRawAxis(throttleIncreaseAxis) - joystick.getRawAxis(throttleDecreaseAxis));
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

    public double deadband(double val){
        if(val <= 0.075 && val >= -0.075){
            return 0;
        }

        return val;
    }

    public boolean debugRawButton(int button) { return joystick.getRawButton(button); }

}
