package ch.ethz.inf.vs.a1.gmtui.antitheft;


import ch.ethz.inf.vs.a1.gmtui.antitheft.AlarmCallback;
import ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector.SpikeMovementDetector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 11.10.16.
 */

public class SpikeMovementDetectorTest implements AlarmCallback {

    SpikeMovementDetector movementDetector;
    boolean result;

    @Before
    public void setup() {
        result = false;
        movementDetector = new SpikeMovementDetector(this, 10);
    }

    @Test
    public void test1() throws Exception {
        float[] values1 = {0.0f, 0.0f, 0.0f};
        result = movementDetector.doAlarmLogic(values1);
        assertFalse(result);
    }

    @Test
    public void test2() throws Exception {
        float[] values1 = {1.1f, 0.5f, 1.0f};
        result = movementDetector.doAlarmLogic(values1);
        assertFalse(result);
    }

    @Test
    public void test3() throws Exception {
        float[] values1 = {5.0f, 5.0f, 0.0f};
        result = movementDetector.doAlarmLogic(values1);
        assertTrue(result);
    }

    @Test
    public void test4() throws Exception {
        float[] values1 = {5.0f, 5.0f, -5.0f};
        result = movementDetector.doAlarmLogic(values1);
        assertTrue(result);
    }

    @Test
    public void test5() throws Exception {
        float[] values1 = {5.0f, 5.0f, -5.5f};
        result = movementDetector.doAlarmLogic(values1);
        assertTrue(result);
    }

    @Test
    public void test6() throws Exception {
        float[] values1 = {-5.0f, -5.0f, -5.0f};
        result = movementDetector.doAlarmLogic(values1);
        assertTrue(result);
    }

    @Test
    public void test7() throws Exception {
        float[] values1 = {3.3f, 3.3f, 3.3f};
        result = movementDetector.doAlarmLogic(values1);
        assertFalse(result);
    }

//    @Override
//    public void onTimeoutStarted() {
//        // Do not do anything
//    }

    @Override
    public void onDelayStarted() {
        //no idea what to do here
    }
}