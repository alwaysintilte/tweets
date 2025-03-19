package twitter.tweets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonDeserializerMethods {
    public static Map<String,State> JsonDeserializer(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        Map<String, List<List<List<Double>>>> rawData = mapper.readValue(file, Map.class);
        Map<String, State> states = new HashMap<>();
        for (Map.Entry<String, List<List<List<Double>>>> entry : rawData.entrySet()) {
            String stateAbbreviation = entry.getKey(); // Аббревиатура штата (например, "WA")
            List<List<List<Double>>> rawPolygons = entry.getValue();
            List<StatePolygon> statePolygons = new ArrayList<>();
            for (List<List<Double>> polygonCoordinates : rawPolygons) {
                statePolygons.add(new StatePolygon(polygonCoordinates));
            }
            states.put(stateAbbreviation, new State(statePolygons));
        }
        return states;
    }
}