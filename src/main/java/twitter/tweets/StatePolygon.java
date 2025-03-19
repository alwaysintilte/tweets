package twitter.tweets;

import java.util.Date;
import java.util.List;

public class StatePolygon {
    public List<List<Double>> coordinates;

    public StatePolygon(List<List<Double>> coordinates){
        this.coordinates=coordinates;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
}
