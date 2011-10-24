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

package geospace.control.agent;

import geospace.control.CurrentGameState;
import org.newdawn.slick.Input;

public class KeyboardAgent extends AbstractInputAgent {

    public KeyboardAgent() {
        super();

        this.agentName = "Keyboard Agent";
        this.agentDescription = "A test agent that uses input from the keyboard to control the player.";
    }

    @Override
    public void informGameState(final CurrentGameState cgs) {
        if(this.input != null) {
        
        this.agentController.setThrusting(this.input.isKeyDown(Input.KEY_UP));
        this.agentController.setTurningPort(this.input.isKeyDown(Input.KEY_LEFT));
        this.agentController.setTurningStarboard(this.input.isKeyDown(Input.KEY_RIGHT));
        this.agentController.setShielding(this.input.isKeyDown(Input.KEY_LSHIFT) || this.input.isKeyDown(Input.KEY_RSHIFT));
        this.agentController.setFiring(this.input.isKeyDown(Input.KEY_SPACE));
        this.agentController.setTurbo(this.input.isKeyDown(Input.KEY_TAB));
        }
    }
}
