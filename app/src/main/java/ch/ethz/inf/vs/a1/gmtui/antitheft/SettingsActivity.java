package ch.ethz.inf.vs.a1.gmtui.antitheft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {
    public static final String DETECTION_METHOD = "pref_detection_method";
    public static final String ACCELERATION_SENSITIVITY = "pref_accel_sensitivity";
    public static final String ROTATION_SENSITIVITY = "pref_rotation_sensitivity";
    public static final String ALARM_DELAY = "pref_delay";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
