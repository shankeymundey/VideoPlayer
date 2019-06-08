package com.example.yogesh.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
VideoView vv;
SeekBar sk;
int videolnth;
TextView t1,t2;
int t,dur;
boolean ispause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vv=(VideoView)findViewById(R.id.vv);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        sk=(SeekBar)findViewById(R.id.sk);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            t=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
             vv.seekTo(t);
            }
        });
    }

    public void playbtn(View view) {
    if(ispause==false){
        String path="android.resource://"+getPackageName()+"/"+R.raw.soch;
        Uri uri=Uri.parse(path);
        vv.setVideoURI(uri);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videolnth=vv.getDuration();
                sk.setMax(videolnth);
                t2.setText(""+videolnth);
            }
        });
    }

        vv.start();
        CountDownTimer cd=new CountDownTimer(1000000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
               int dur=vv.getCurrentPosition();
              t1.setText(""+dur);
               sk.setProgress(dur);
            }

            @Override
            public void onFinish() {

            }
        };
        cd.start();



    }

    public void pausebtn(View view) {
        vv.pause();
        ispause=true;
    }

    public void stopbtn(View view) {
        vv.pause();
        ispause=false;
    }

    public void getbtn(View view) {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("*/*");
        Intent myintent=Intent.createChooser(intent,"select video");
        startActivityForResult(myintent,1);

    }
    @Override
    public  void onActivityResult(int requestcode,int resultcode,Intent data)
    {
         if(resultcode==RESULT_OK){
             Uri myuri=data.getData();
             vv.setVideoURI(myuri);
             vv.start();
         }



    }


}
