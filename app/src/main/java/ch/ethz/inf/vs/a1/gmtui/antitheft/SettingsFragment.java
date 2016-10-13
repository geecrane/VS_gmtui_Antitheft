package ch.ethz.inf.vs.a1.gmtui.antitheft;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Map;

/**
 * Created by george on 11.10.16.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        Map<String,?> prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()).getAll();
        for (String key : prefs.keySet()) {
            Preference pref = findPreference(key);
            if(pref != null)
                pref.setOnPreferenceChangeListener(this);

        }
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(MainActivity.locked){
            Toast.makeText(getActivity(), "Settings NOT saved!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
