package usac.tesis.com.tesis.profileManagement;

/**
 * Created by root on 20/04/16.
 */
public class perfil {

    private String name;
    private boolean vibrate;
    private int brightness;
    private int volume;

    public perfil(String name, boolean vibrate, int volume, int brightness) {
        this.name = name.trim().replace(" ","_");
        this.vibrate = vibrate;
        this.volume = volume;
        this.brightness = brightness;
    }

    public perfil(String savedString){
        String[] arr = savedString.split("~~");
        this.name = arr[0];
        this.vibrate = Boolean.parseBoolean(arr[1]);
        this.volume = Integer.parseInt(arr[2]);
        this.brightness = Integer.parseInt(arr[3]);
    }

    public String getSaveString(){
        return name + "~~" + vibrate + "~~" + volume + "~~" + brightness;
    }

    public String getDescription(){
        return "Modo ["+getMode()+"] , Brillo: ["+brightness+"%] , Volumen: ["+volume+"%]";
    }

    private String getMode(){
        if(vibrate){
            return "Vibracion";
        }else{
            return "Sonido";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
