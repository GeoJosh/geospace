/*
 * Copyright (c) 2011, GeoJosh - https://github.com/GeoJosh
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
