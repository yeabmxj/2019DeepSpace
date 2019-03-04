package frc.team5115.statemachines;

import edu.wpi.first.wpilibj.Timer;

public class StateMachineBase {

    public static final int STOP = 0;

    public int state = 0;

    public StateMachineBase() {}

    protected void updateChildren() {}

    public void update() {}

    public void setState(int s) {
        state = s;
    }

    public double time;

    public void getTimeStamp() {
        time = Timer.getFPGATimestamp();
    }

    public boolean compareTime(double time) {
        return time > Timer.getFPGATimestamp() + this.time;
    }
}