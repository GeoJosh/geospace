package geospace.control.agent.remote;

import geospace.control.agent.AbstractAgent;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteAgent {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java -jar RemoteAgent.jar [Remote Agent Class Name]");
            System.exit(1);
        }

        try {
            Class agentClass = Class.forName(args[0]);
            AbstractAgent agentImpl = (AbstractAgent) agentClass.getConstructor().newInstance();
            
            if (agentImpl == null) {
                System.out.println("[ERROR] Unable to construct agent: " + args[0]);
                System.exit(1);
            }
            
            RemoteAgentThread agentThread = new RemoteAgentThread(agentImpl);
            Runtime.getRuntime().addShutdownHook(agentThread);

            agentThread.start();
            while (agentThread.isAlive()) {
            }
            
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("[ERROR] Unexpected error.");
        System.exit(2);
    }
}
