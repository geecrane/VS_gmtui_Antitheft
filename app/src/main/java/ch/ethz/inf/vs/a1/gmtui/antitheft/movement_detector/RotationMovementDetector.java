package ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector;

import ch.ethz.inf.vs.a1.gmtui.antitheft.AlarmCallback;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * Created by george on 11.10.16.
 */

public class RotationMovementDetector extends AbstractMovementDetector {
    float[] rotValues = null;
    public RotationMovementDetector(AlarmCallback callback, float sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
        // TODO, insert your logic here
        if(rotValues == null){
            rotValues = values.clone();
            return false;
        }
        else{
            float diffX = rotValues[0] - values[0];
            float diffY = rotValues[1] - values[1];
            float diffZ = rotValues[2] - values[2];
            return (abs(diffX) + abs(diffY) + abs(diffZ)) >= fSensitivity;
        }

    }
}
