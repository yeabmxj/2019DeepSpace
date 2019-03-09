package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class ButtonWrapper extends Button {

    private final GenericHID m_joystick;
    private final int m_bind;


    public ButtonWrapper(GenericHID joystick, int bind){
        m_joystick = joystick;
        m_bind = bind;
    }

    @Override
    public boolean get() {
        boolean comparator;
        switch(m_bind) {
            case 0:
            case 45:
            case 90:
            case 135:
            case 180:
            case 225:
            case 270:
            case 315:
                comparator = m_joystick.getPOV() == m_bind;
                break;
            default:
                comparator = m_joystick.getRawButton(m_bind);
                break;
        }
        return comparator;
    }

}
