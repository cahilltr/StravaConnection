import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Point;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GPXSample {

    //https://developers.google.com/maps/documentation/roads/intro
    //https://developers.google.com/maps/documentation/directions/
    //https://github.com/googlemaps/google-maps-services-java
    public static void main(String[] args) throws Exception {

        GPX gpx = GPX.read(GPXSample.class.getClassLoader().getResourceAsStream("Morning_Run_Devil_s_Turn_SCMS_Track.gpx"));

        List<TrackSegment> segmentList = gpx.getTracks().get(0).segments().collect(Collectors.toList());
        List<WayPoint> wayPoints = segmentList.get(0).getPoints();

        List<ChangeDirection> changeDirectionList = IntStream.range(1, wayPoints.size() - 1).mapToObj(i -> {
            Point p1 = wayPoints.get(i - 1);
            Point p2 = wayPoints.get(i);
            DirectionChange change = GPXSample.determineDirectionChange(p1, p2, 0.0000003);
            return new ChangeDirection(p1, p2, change);
        }).filter(c -> c.change.compareTo(DirectionChange.NOCHANGE) != 0).collect(Collectors.toList());

        WayPoint firstWayPoint = wayPoints.get(0);
        WayPoint secondWayPoint = wayPoints.get(1);
        WayPoint lastWayPoint = wayPoints.get(wayPoints.size() - 1);

        System.out.println("Distance between First and Second: " + Geoid.WGS84.distance(firstWayPoint, secondWayPoint));
        System.out.println("Distance between First and Last: " + Geoid.WGS84.distance(firstWayPoint, lastWayPoint));

//        GeoApiContext geoApiContext = new GeoApiContext().setQueryRateLimit(10);


//        GeocodingApi.geocode(geoApiContext,)
//        GeocodingResult[] result = GeocodingApi.reverseGeocode(geoApiContext, new LatLng(firstWayPoint.getLatitude().doubleValue(), firstWayPoint.getLongitude().doubleValue())).await();

//[lat=35.077659, lon=-80.817397, ele=171.8 m]
    }

    public enum DirectionChange {NORTH, EAST, SOUTH, WEST, NOCHANGE}

    public static DirectionChange determineDirectionChange(Point p1, Point p2, double minChange) {
        double latDiff = p1.getLatitude().doubleValue() - p2.getLatitude().doubleValue();
        double latDiffABS = Math.abs(latDiff);
        double longDiff = p1.getLongitude().doubleValue() - p2.getLongitude().doubleValue();
        double longDiffABS = Math.abs(longDiff);

        if (latDiffABS < minChange) return DirectionChange.NOCHANGE;
        if (longDiffABS < minChange) return DirectionChange.NOCHANGE;

        if (Math.abs(longDiffABS - latDiffABS) < minChange)
            return DirectionChange.NOCHANGE;

        if (latDiff > longDiff) { //lat Diff
            if (latDiff > 0.0)
                return DirectionChange.NORTH;
            else
                return DirectionChange.SOUTH;
        } else {
            if (longDiff > 0.0)
                return DirectionChange.WEST;
            else
                return DirectionChange.EAST;
        }
    }

    public static class ChangeDirection {
        Point p1;
        Point p2;
        DirectionChange change;

        ChangeDirection(Point p1, Point p2, DirectionChange change) {
            this.p1 = p1;
            this.p2 = p2;
            this.change = change;
        }

    }

}
