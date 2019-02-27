package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Spark;

public class Vacuum {

    public Spark mrSucc;
    public Vacuum() {
        mrSucc = new Spark(0);
    }

    public void succ() {
        mrSucc.set(.5);
    }

    public void stop() {
        mrSucc.set(0);
    }

    /**TESTING PURPOSES ONLY**/
//    public void armDown() {
//        mrSucc.set(ControlMode.PercentOutput, -.3);
//    }
}