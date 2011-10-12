package geospace.states;

import geospace.GeoSpace;
import geospace.audio.AudioManager;
import geospace.gui.GUIManager;
import geospace.render.DrawManager;
import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import geospace.render.elements.ImageRender;
import geospace.states.runners.PauseRunner;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingState extends BasicGameState {

    private int stateId;
    private Thread pauseThread;
    private List<ImageRender> imageResources;

    public LoadingState(int id) {
        this.stateId = id;
        this.imageResources = new LinkedList<ImageRender>();
    }

    @Override
    public int getID() {
        return this.stateId;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(false);
        gc.setTargetFrameRate(60);

        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/font/monaco.ttf"));

            FontManager.getInstance().setFont(FontType.DEFAULT, new UnicodeFont(baseFont.deriveFont(14.0f)));
            FontManager.getInstance().setFont(FontType.WIDGET, new UnicodeFont(baseFont.deriveFont(14.0f)));
            FontManager.getInstance().setFont(FontType.TIMER, new UnicodeFont(baseFont.deriveFont(25.0f)));
        } catch (FontFormatException ex) {
            Logger.getLogger(LoadingState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadingState.class.getName()).log(Level.SEVERE, null, ex);
        }

        Image logoImage = new Image("./resources/images/logo.png");
        this.imageResources.add(new ImageRender(logoImage, (gc.getWidth() - logoImage.getWidth()) / 2, (gc.getHeight() - logoImage.getHeight()) / 2));

        DrawManager.getInstance().init();
        GUIManager.getInstance().init(gc);

        String[] tracks = (new File("./resources/sound/background")).list();
        for (String track : tracks) {
            if (track.endsWith("ogg")) {
                AudioManager.getInstance().addMusic(new Music("./resources/sound/background/" + track, true));
            }
        }

        AudioManager.getInstance().addSound(AudioManager.EffectType.SHOT, new Sound("./resources/sound/shot.ogg"));
        AudioManager.getInstance().addSound(AudioManager.EffectType.DEATH, new Sound("./resources/sound/death.ogg"));
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.enter(gc, sbg);

        this.pauseThread = new Thread(new PauseRunner(2000));
        this.pauseThread.start();
        DrawManager.getInstance().addImage(this.imageResources);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
        StateJanitor.cleanupState();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

        if (this.pauseThread.getState() == Thread.State.TERMINATED) {
            sbg.enterState(GeoSpace.MENU_STATE, new FadeOutTransition(new Color(0, 0, 0, 0), 500), new FadeInTransition());
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        DrawManager.getInstance().renderImages();
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);

        this.pauseThread.interrupt();
    }
}
