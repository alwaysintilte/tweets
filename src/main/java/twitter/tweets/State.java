package twitter.tweets;

import java.util.List;

public class State {
    public List<StatePolygon> statePolygons;

    public State(List<StatePolygon> statePolygons){
        this.statePolygons=statePolygons;
    }

    public List<StatePolygon> getStatePolygons() {
        return statePolygons;
    }
}
