package ch.ethz.inf.vs.a1.gmtui.antitheft;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    private final String TOGGLE_PREF = "toggle_state";
    private Intent antiTheftService;
    public static Boolean locked;

    @Override
    protected void onStop() {

        SharedPreferences.Editor editor = getPreferences(this.MODE_PRIVATE).edit();
        editor.putBoolean(TOGGLE_PREF, locked);
        editor.apply();

        super.onStop();
    }

    @Override
    protected void onStart() {
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        tb.setChecked(getPreferences(this.MODE_PRIVATE).getBoolean(TOGGLE_PREF,false));
        locked = tb.isChecked();

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        antiTheftService = new Intent(this, AntiTheftService.class);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;
    }
    public void onClickLock(View v){
        ToggleButton tb = (ToggleButton) v;
        locked = tb.isChecked();
        if(locked){
            Toast.makeText(this, "Locked", Toast.LENGTH_SHORT).show();
            startService(antiTheftService);

        }else {
            Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show();
            stopService(antiTheftService);
        }
    }

}
