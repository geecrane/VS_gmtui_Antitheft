package ch.ethz.inf.vs.a1.gmtui.antitheft;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import android.os.Process;

import ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector.AbstractMovementDetector;
import ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector.RotationMovementDetector;
import ch.ethz.inf.vs.a1.gmtui.antitheft.movement_detector.SpikeMovementDetector;


public class AntiTheftService extends Service implements AlarmCallback {
    private SensorManager sensorMgr;
    private AbstractMovementDetector detector;
    private Sensor sensor;
    private MediaPlayer mp = null;
    private int linearSensitivity;
    private float rotationSensitivity;
    private int delay;
    private int detection;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private HandlerThread thread;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {


                try {
                    Thread.sleep(delay*1000);
                    mp.start();
                } catch (InterruptedException e) {
                    // Restore interrupt status.
                    Thread.currentThread().interrupt();
                }
            //stopSelf(msg.arg1);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onCreate() {

        thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        linearSensitivity = Integer.parseInt(sharedPref.getString(SettingsActivity.ACCELERATION_SENSITIVITY, "0"));
        rotationSensitivity = Float.parseFloat(sharedPref.getString(SettingsActivity.ROTATION_SENSITIVITY, "0"));
        delay = Integer.parseInt(sharedPref.getString(SettingsActivity.ALARM_DELAY, "0"));
        detection = Integer.parseInt(sharedPref.getString(SettingsActivity.DETECTION_METHOD, "0"));

        if(detection == 0) {
            sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            detector = new SpikeMovementDetector(this, linearSensitivity);
        }else{
            sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            detector = new RotationMovementDetector(this, rotationSensitivity);
        }

        sensorMgr.registerListener(detector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        mp = MediaPlayer.create(this, R.raw.woop);
        mp.setLooping(true);
        mp.setVolume(1.0f, 1.0f);

        Notification myNotification = createNotification();
        startForeground(001, myNotification);
    }


    @Override
    public void onDestroy() {
        sensorMgr.unregisterListener(detector);
        if (mp.isPlaying()) {
            mp.stop();
        }
        thread.interrupt();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onDelayStarted() {
        if (!mp.isPlaying()) {
            Message msg = mServiceHandler.obtainMessage();
            mServiceHandler.sendMessage(msg);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    private Notification createNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.notification_icon);
        mBuilder.setContentTitle("Anti Theft");
        mBuilder.setContentText("Anti-Theft protection activated!");

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder.build();
    }
}
