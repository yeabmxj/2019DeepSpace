package frc.team5115.lib.drivers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class TalonWrapper extends TalonSRX{

    //stolen from https://github.com/Team254/FRC-2018-Public

    protected double mLastSet = Double.NaN;
    protected ControlMode mLastControlMode = null;

    private DigitalInput upper;
    private DigitalInput lower;

    public TalonWrapper(int deviceNumber) {
        super(deviceNumber);
    }

    public TalonWrapper(int deviceNumber, DigitalInput upper, DigitalInput lower){
        super(deviceNumber);
        this.upper = upper;
        this.lower = lower;
    }

    public double getLastSet() {
        return mLastSet;
    }

    @Override
    public void set(ControlMode mode, double value) {
        double temp = value;
        if((upper != null) && (lower != null)){
            DigitalInput controller = Math.signum(value) == 1 ? upper : lower;
            if(!controller.get()){
                temp = 0;
            }
        }
        if (value != mLastSet || mode != mLastControlMode) {
            mLastSet = value;
            mLastControlMode = mode;
            super.set(mode, temp);
        }
    }

}
