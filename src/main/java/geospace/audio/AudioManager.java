package geospace.audio;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.Sound;

public class AudioManager implements MusicListener {
    private boolean managerEnabled;
    
    private List<Music> musicResources;
    private Map<EffectType, Sound> soundResources;
    private Music currentMusic;
    private boolean loopMusic;
    
    
    public enum EffectType {
        SHOT,
        DEATH,
    }
    
    private AudioManager() {
        this.managerEnabled = true;
        this.musicResources = new LinkedList<Music>();
        this.soundResources = new EnumMap<EffectType, Sound>(EffectType.class);
    }

    private static class AudioManagerSingleton {
        private static final AudioManager instance = new AudioManager();
    }

    public static AudioManager getInstance() {
        return AudioManagerSingleton.instance;
    }

    public void setManagerEnabled(boolean managerEnabled) {
        this.managerEnabled = managerEnabled;
        if(!managerEnabled && this.currentMusic != null) {
            this.currentMusic.stop();
        }
    }

    public boolean isManagerEnabled() {
        return managerEnabled;
    }

    public void addMusic(Music music) {
        this.musicResources.add(music);
    }

    public void addSound(EffectType effectType, Sound sound) {
        this.soundResources.put(effectType, sound);
    }

    public boolean isPlayingMusic() {
        return !(this.currentMusic == null);
    }
    
    public void playRandomMusic(boolean loop) {
        if(this.managerEnabled) {
            if(this.currentMusic != null) {
                this.currentMusic.stop();
            }

            if(this.musicResources.size() > 0) {
                this.loopMusic = loop;
                this.currentMusic = this.musicResources.get(new Random().nextInt(this.musicResources.size()));
                this.currentMusic.addListener(this);
                this.currentMusic.play();
            }
        }
    }

    public void stopMusic() {
        if(this.currentMusic != null) {
            this.currentMusic.stop();
            this.currentMusic = null;
        }
    }

    public void musicEnded(Music music) {
        if(this.loopMusic) {
            this.currentMusic.play();
        }
    }

    public void musicSwapped(Music music, Music music1) {
    }
    
    public void playEffect(EffectType effectType) {
        if(this.managerEnabled) {
            this.soundResources.get(effectType).play();
        }
    }
}
