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

package geospace;

import geospace.audio.AudioManager;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    private final static String ARG_ALWAYSRENDER= "--alwaysrender";
    private final static String ARG_FULLSCREEN = "--fullscreen";
    private final static String ARG_NOSOUND = "--nosound";
    
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        
        try {
            WrapperAwareGame wag = new GeoSpace("GeoSpace");
            AppGameContainer container = new AppGameContainer(wag);
            parseArgs(container, argsList);
            wag.setGameWrapper(container);
            
            container.setDisplayMode(
                    PropertyManager.getInstance().getInteger("game.width", container.getScreenWidth()),
                    PropertyManager.getInstance().getInteger("game.height", container.getScreenHeight()),
                    argsList.contains(ARG_FULLSCREEN));
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
