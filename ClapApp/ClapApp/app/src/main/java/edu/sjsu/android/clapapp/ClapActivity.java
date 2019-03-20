package edu.sjsu.android.clapapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClapActivity extends AppCompatActivity {
    SensorManager mySensorManager;
    Sensor proximitySensor;
    TextView data;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = (TextView)findViewById(R.id.data);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        /*if(proximitySensor == null){
            data.setText("No Proximity Sensor !!");
        }
        else
        {
            data.setText("Proximity Sensor present !!");
        }*/
    }

    @Override
    public void onResume(){
        super.onResume();
        mySensorManager.registerListener(proximityListener, proximitySensor, 2*1000*1000);
    }

    @Override
    public void onPause(){
        super.onPause();
        mySensorManager.unregisterListener(proximityListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



    private SensorEventListener proximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float dist = event.values[0];

            if(dist < 3f){
                mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.clapsound);
                mediaPlayer.start();
                data.setText("Near");
            }
            else{
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    data.setText("Away");

                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void finishClapActivity(View view){
        ClapActivity.this.finish();
    }
}
