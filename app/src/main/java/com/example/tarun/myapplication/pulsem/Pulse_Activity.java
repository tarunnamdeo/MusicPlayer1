package com.example.tarun.myapplication.pulsem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tarun.myapplication.R;
import com.gigamole.library.PulseView;

/**
 * Created by tarun on 3/30/2018.
 */

public class Pulse_Activity extends AppCompatActivity {
    PulseView pulseView;
    Button btnStart,btnStop;

    @Override
    protected void onCreate(Bundle savedIntenceState){
        super.onCreate(savedIntenceState);
        setContentView(R.layout.pulse);



        pulseView =(PulseView)findViewById(R.id.pv);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStop=(Button)findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulseView.startPulse();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulseView.finishPulse();
            }
        });
    }
}
