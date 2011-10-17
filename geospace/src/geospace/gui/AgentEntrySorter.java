package geospace.gui;

import java.util.Comparator;
import java.util.Map.Entry;

/**
 *
 * @author jpenton
 */
public class AgentEntrySorter implements Comparator {
    public int compare(Object t, Object t1) {
        return ((Entry<String, String>)t).getValue().compareTo(((Entry<String, String>)t1).getValue());
    }    
}
