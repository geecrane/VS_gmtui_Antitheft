package ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector;

/**
 * Created by george on 10.10.16.
 */
import ch.ethz.inf.vs.a1.gmtui.antitheft.AlarmCallback;

import static java.lang.Math.abs;

public class SpikeMovementDetector extends AbstractMovementDetector {
    public SpikeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
        // TODO, insert your logic here

        return (abs(values[0]) + abs(values[1]) + abs(values[2])) >= sensitivity;
    }
}
