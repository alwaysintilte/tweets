package twitter.tweets;

import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatePointChecker {
    public static Polygon createPolygon(StatePolygon statePolygon){
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> stateCoordinates = new ArrayList<Coordinate>();
        for (List<Double> coordinates: statePolygon.getCoordinates()) {
            stateCoordinates.add(new Coordinate(coordinates.get(0),coordinates.get(1)));
        }
        return geometryFactory.createPolygon(stateCoordinates.toArray(new Coordinate[0]));
    }
    public static MultiPolygon createMultiPolygon(List<StatePolygon> statePolygons){
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Polygon> stateCoordinates = new ArrayList<Polygon>();
        for (StatePolygon statePolygon:statePolygons) {
            List<Coordinate> statePolygonCoordinates = new ArrayList<Coordinate>();
            for (List<Double> coordinates: statePolygon.getCoordinates()) {

                statePolygonCoordinates.add(new Coordinate(coordinates.get(0),coordinates.get(1)));
            }
            Polygon polygon=geometryFactory.createPolygon(statePolygonCoordinates.toArray(new Coordinate[0]));
            stateCoordinates.add(polygon);
        }
        return geometryFactory.createMultiPolygon(stateCoordinates.toArray(new Polygon[0]));
    }
    public static boolean ifPointIsInsideAState(double pointLongitude, double pointLatitude, State state) {
        boolean isInside=false;
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate pointCoordinate = new Coordinate(pointLongitude, pointLatitude);
        Point point = geometryFactory.createPoint(pointCoordinate);
        if(state.getStatePolygons().size()==1){
            Polygon statePolygon=createPolygon(state.getStatePolygons().get(0));
            isInside = statePolygon.contains(point);
        }
        else{
            MultiPolygon statePolygon=createMultiPolygon(state.getStatePolygons());
            isInside = statePolygon.contains(point);
        }
        return isInside;
    }

    public static String findStateForPoint(double pointLongitude, double pointLatitude, Map<String,State> states) {
        String statePostalCode=null;
        for (Map.Entry<String, State> entry:states.entrySet()) {
            if(ifPointIsInsideAState(pointLongitude, pointLatitude, entry.getValue())){
                statePostalCode=entry.getKey();
                break;
            }
        }
        return statePostalCode;
    }
}
