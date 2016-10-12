package ch.ethz.inf.vs.a1.gmtui.antitheft;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by george on 11.10.16.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
