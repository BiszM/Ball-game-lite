package com.weem.ballgame.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.weem.ballgame.R;

/**
 * Created by shailendra on 11/12/17.
 */





public class MusicService extends Service {
    MediaPlayer player;








    public MusicService() {

    }


//
//public class MyServiceBinder extends Binder{
//        public MusicService getService(){
//            return MusicService.this;
//
//        }
//
//    }







//
//    private IBinder mBinder= new MyServiceBinder();
//
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

         player= MediaPlayer.create(getApplicationContext(), R.raw.music);
        player.setLooping(true);
        player.start();



        return START_NOT_STICKY;
    }


    @Override
    public boolean stopService(Intent name) {
        player.stop();
player.release();


        return super.stopService(name);
    }

    @Override
    public void onDestroy() {   //this was very important currently
        super.onDestroy();

        player.stop();
    }
}
