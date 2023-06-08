package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor s;
    private SensorEventListener evento;
    private int mov =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer sonidoFondo = MediaPlayer.create(this, R.raw.fondo);
        sonidoFondo.start();
        sonidoFondo.setLooping(true);
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE); // Acceso a sensores
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Utilización del Acelerometro
        if(s == null){
            finish();
        }
        evento = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                sonidoFondo.setLooping(false);
                if(sensorEvent.values[0]<-4&&mov==0){
                    mov++;
                }else{
                    if(sensorEvent.values[0]>4&&mov==1){
                        mov++;
                    }
                }
                if(mov==2){
                    mov=0;
                    sonidoFondo.stop();
                    sonidoVictoria();
                    Toast.makeText(MainActivity.this, "Has ganado!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sm.registerListener(evento, s, SensorManager.SENSOR_DELAY_NORMAL);

    }
    public void sonidoVictoria(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.victoria);
        mp.start();
    }
    public void sonidoDerrota(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.derrota);
        mp.start();
    }
}