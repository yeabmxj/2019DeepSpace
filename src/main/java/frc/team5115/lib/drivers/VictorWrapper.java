package frc.team5115.lib.drivers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class VictorWrapper extends VictorSPX {

    //stolen from https://github.com/Team254/FRC-2018-Public

    protected double mLastSet = Double.NaN;
    protected ControlMode mLastControlMode = null;

    private DigitalInput upper;
    private DigitalInput lower;

    public VictorWrapper(int deviceNumber) {
        super(deviceNumber);
    }

    public VictorWrapper(int deviceNumber, int upperID, int lowerID){
        super(deviceNumber);
        this.upper = new DigitalInput(upperID);
        this.lower = new DigitalInput(lowerID);
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
