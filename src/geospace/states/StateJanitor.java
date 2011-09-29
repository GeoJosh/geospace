package geospace.states;

import geospace.audio.AudioManager;
import geospace.entity.EntityManager;
import geospace.gui.GUIManager;
import geospace.render.DrawManager;
import geospace.render.EffectManager;

public class StateJanitor {
    public static void cleanupState() {
        AudioManager.getInstance().stopMusic();
        DrawManager.getInstance().clearImages();
        EntityManager.getInstance().clearEntities();
        EffectManager.getInstance().clearEffects();
        GUIManager.getInstance().clearWidgets();
    }
}
