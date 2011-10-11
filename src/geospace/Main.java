package geospace;

import geospace.audio.AudioManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    private final static String ARG_NOSOUND = "--nosound";
    private final static String ARG_FULLSCREEN = "--fullscreen";
    private final static String ARG_ALWAYSRENDER= "--alwaysrender";
    
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        
        try {
            WrapperAwareGame wag = new GeoSpace("GeoSpace");
            AppGameContainer container = new AppGameContainer(wag);
            parseArgs(container, argsList);
            wag.setGameWrapper(container);
            
            String containerWidth = null;
            String containerHeight = null;

            try {
            Properties props = new Properties();
                props.load(new FileInputStream("./resources/geospace.properties"));
                containerWidth = props.getProperty("width");
                containerHeight = props.getProperty("height");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            container.setDisplayMode(containerWidth == null ? container.getScreenWidth() : Integer.valueOf(containerWidth), containerHeight == null ? container.getScreenHeight() : Integer.valueOf(containerHeight), argsList.contains(ARG_FULLSCREEN));
            container.start();
        }
        catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void parseArgs(AppGameContainer container, List<String> args) {
        for(String arg : args) {
            if(ARG_NOSOUND.equalsIgnoreCase(arg)) {
                AudioManager.getInstance().setManagerEnabled(false);
            }
            
            if(ARG_ALWAYSRENDER.equalsIgnoreCase(arg)) {
                container.setAlwaysRender(true);
            }
        }
    }
}
