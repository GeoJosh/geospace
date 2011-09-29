package geospace.states;

import geospace.audio.AudioManager;
import geospace.render.EffectManager;
import geospace.control.agent.AbstractAgent;
import geospace.control.CurrentGameState;
import geospace.control.agent.service.ServiceAgentManager;
import geospace.entity.Point;
import geospace.entity.Ship;
import geospace.render.elements.Stage;
import geospace.render.DrawManager;
import geospace.entity.EntityManager;
import geospace.gui.GUIManager;
import geospace.render.elements.EnergyBar;
import geospace.render.elements.ImageRender;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayingState extends BasicGameState {

    private int stateId;
    private Stage gameStage;
    private List<ImageRender> imageResources;
    private static final int TITLE_HEIGHT = 50;
    private static final int BORDER_SIZE = 10;
    
    private List<Player> players;
    private CurrentGameState currentGameState;
    private GameMode currentGameMode;
    
    public enum GameMode {
        DUEL,
        BATTLE_ROYALE
    }
    
    public PlayingState(int stateId) {
        this.stateId = stateId;
        this.imageResources = new LinkedList<ImageRender>();
        
        this.players = new LinkedList<Player>();
        this.currentGameState = new CurrentGameState();
        
        this.currentGameMode = GameMode.DUEL;
    }

    public void setPlayerControllers(List<AbstractAgent> agents) {
        this.players.clear();
        for(AbstractAgent agent : agents) {
            Player player = new Player();
            player.setAgent(agent);
            players.add(player);
        }
    }

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
    }

    @Override
    public int getID() {
        return this.stateId;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        Image logoImage = new Image(PlayingState.class.getResourceAsStream("/resources/images/logo-small.png"), "Small Logo", false);
        this.imageResources.add(new ImageRender(logoImage, (gc.getWidth() - logoImage.getWidth()) / 2, (PlayingState.TITLE_HEIGHT + PlayingState.BORDER_SIZE - logoImage.getHeight()) / 2));

    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.enter(gc, sbg);

        DrawManager.getInstance().addImage(this.imageResources);

        this.gameStage = new Stage(new Point(BORDER_SIZE, BORDER_SIZE + TITLE_HEIGHT), gc.getWidth() - (2 * BORDER_SIZE), gc.getHeight() - (2 * BORDER_SIZE) - TITLE_HEIGHT);
        Random randomGenerator = new Random();

        AudioManager.getInstance().playRandomMusic(false);
        
        for(Player player : this.players) {
            player.setShip(new Ship(player.getAgent(), 50 + (randomGenerator.nextFloat() * this.gameStage.getStageWidth() - 100), 50 + (randomGenerator.nextFloat() * this.gameStage.getStageHeight() - 100), randomGenerator.nextFloat() * 360, new Color(randomGenerator.nextInt() | 0xFF000000)));
            EffectManager.getInstance().renderSpawn(player.getShip().getCenter().getX(), player.getShip().getCenter().getY());
            EntityManager.getInstance().addEntity(player.getShip());
        }
        
        initializeGameMode();
    }

    
    @Override
    public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
        StateJanitor.cleanupState();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.gameStage.update(gc, delta);

        this.gameStage.setStageClipping(gc.getGraphics(), true);
        EffectManager.getInstance().update(gc, delta);
        this.gameStage.setStageClipping(gc.getGraphics(), false);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        DrawManager.getInstance().renderImages();

        for(Player player : this.players) {
            DrawManager.getInstance().renderPlayerInfo(graphics, player);
        }
        
        this.gameStage.setStageClipping(graphics, true);
        this.gameStage.render(graphics);
        if (!DrawManager.getInstance().isEnableWireframe()) {
            EffectManager.getInstance().setOffset(this.gameStage.getStageOffset());
            EffectManager.getInstance().render(graphics);
        }
        this.gameStage.setStageClipping(graphics, false);
        
        graphics.resetTransform();
        GUIManager.getInstance().render();

        this.currentGameState.updateState();
        ServiceAgentManager.getInstance().setLastGameState(currentGameState);

        for(Player player : this.players) {
            player.getAgent().informGameState(this.currentGameState);
        }
    }
    
    private void initializeGameMode() {
        switch(this.currentGameMode) {
            case DUEL:
                this.players.get(0).setEnergyBar(new EnergyBar(this.players.get(0).getShip(), new Point(BORDER_SIZE, BORDER_SIZE), (this.gameStage.getStageWidth() / 2) - 50, TITLE_HEIGHT - BORDER_SIZE));
                this.players.get(1).setEnergyBar(new EnergyBar(this.players.get(1).getShip(), new Point((this.gameStage.getStageWidth() / 2) + 50 + BORDER_SIZE, BORDER_SIZE), (this.gameStage.getStageWidth() / 2) - 50, TITLE_HEIGHT - BORDER_SIZE));
                break;
        }        
        
    }
}
