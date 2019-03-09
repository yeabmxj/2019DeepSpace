package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Wrist {

    public static TalonSRX x;
    public static TalonSRX y;

    public static DigitalInput leftxLimit;
    public static DigitalInput rightxLimit;


    public Wrist(){
        x = new TalonSRX(2);
        y = new TalonSRX(1);

        leftxLimit = new DigitalInput(0);
        rightxLimit = new DigitalInput(1);

    }

    public void moveLeft()
    {

        if (leftxLimit.get());
        {
            x.set(ControlMode.PercentOutput, 0);
        }
        x.set(ControlMode.PercentOutput, .5);

    }

    public void moveRight()
    {

        if (rightxLimit.get())
        {
            x.set(ControlMode.PercentOutput, 0);
        }

        x.set(ControlMode.PercentOutput, -.5);

    }

    public void moveUp()
    {

        y.set(ControlMode.PercentOutput, .5);
    }

    public void moveDown()
    {
        y.set(ControlMode.PercentOutput, -.5);
    }

    public void dontMove(){
        x.set(ControlMode.PercentOutput,0);
        y.set(ControlMode.PercentOutput,0);
    }
}
