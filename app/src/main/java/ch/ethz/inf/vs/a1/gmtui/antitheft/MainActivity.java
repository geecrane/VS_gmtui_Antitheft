package ch.ethz.inf.vs.a1.gmtui.antitheft;


import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    public static ToggleButton tb;
    private Intent antiTheftService;

    @Override
    protected void onStart() {
        super.onStart();
        tb.setChecked(AntiTheftService.running);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        antiTheftService = new Intent(this, AntiTheftService.class);
        tb = (ToggleButton) findViewById(R.id.toggleButton);
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
        if(tb.isChecked()){
            startService(antiTheftService);
        }else {
            stopService(antiTheftService);
        }
    }

}
