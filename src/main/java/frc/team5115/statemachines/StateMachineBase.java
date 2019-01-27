package frc.team5115.statemachines;

//think of this like a check list that every state machine will have
public class StateMachineBase {

    public static final int STOP = 0;

    public int state = 0;

    public StateMachineBase() {}

    protected void updateChildren() {}

    public void update() {}

    public void setState(int s) {
        state = s;
    }
}
