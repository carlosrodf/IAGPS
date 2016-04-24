package usac.tesis.com.tesis.utils;

import android.content.ContentResolver;
import android.media.AudioManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import usac.tesis.com.tesis.profileManagement.perfil;

/**
 * Created by root on 23/04/16.
 */
public class configApplier {

    private perfil config;
    private ContentResolver resolver;
    private Window window;
    private AudioManager audio;

    public configApplier(perfil perfil,ContentResolver resolver,
                         Window window, AudioManager audio){
        this.config = perfil;
        this.resolver = resolver;
        this.window = window;
        this.audio = audio;
    }

    public void apply(){
        applyBrightness();
        applyVolume();
        applyVibrate();
    }

    private void applyBrightness(){
        float bright = config.getBrightness()*255/(float)100;
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,(int)bright);
        WindowManager.LayoutParams layoutparams = window.getAttributes();
        layoutparams.screenBrightness = bright;
        window.setAttributes(layoutparams);
    }

    private void applyVolume(){
        int max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int target = config.getVolume() * max / 100;
        audio.setStreamVolume(AudioManager.STREAM_MUSIC,target,0);
    }

    private void applyVibrate(){
        if(config.isVibrate()){
            audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else{
            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
}
