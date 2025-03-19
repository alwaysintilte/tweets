package twitter.tweets;

import java.util.List;
import java.util.Map;

public class State {
    public List<StatePolygon> statePolygons;

    public State(List<StatePolygon> statePolygons){
        this.statePolygons=statePolygons;
    }

    public List<StatePolygon> getStatePolygons() {
        return statePolygons;
    }
}
