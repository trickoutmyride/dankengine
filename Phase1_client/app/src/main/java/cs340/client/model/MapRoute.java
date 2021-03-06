package cs340.client.model;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs340.ui.R;

public class MapRoute {
    public static final String TAG = MapRoute.class.getSimpleName();
    private static final Double DOUBLE_OFFSET = 0.1;
    private static final Map<Pair<String, String>, MapRoute> routeMap = new HashMap<>();
    private Integer color;
    private Boolean isClaimable;
    private Boolean isDouble;
    private Integer length;
    private City start;
    private City stop;

    static {
        List<MapRoute> routes = new ArrayList<>();
        routes.add(new MapRoute(City.ATLANTA, City.CHARLESTON, 2, R.color.train_grey));
        routes.add(new MapRoute(City.ATLANTA, City.MIAMI, 5, R.color.train_blue));
        routes.add(new MapRoute(City.ATLANTA, City.NASHVILLE, 1, R.color.train_grey));
        routes.add(new MapRoute(City.ATLANTA, City.NEW_ORLEANS, 4, R.color.train_orange)); // ORANGE
        routes.add(new MapRoute(City.ATLANTA, City.NEW_ORLEANS, 4, R.color.train_yellow, true));
        routes.add(new MapRoute(City.ATLANTA, City.RALEIGH, 2, R.color.train_grey));
        routes.add(new MapRoute(City.ATLANTA, City.RALEIGH, 2, R.color.train_grey, true));
        routes.add(new MapRoute(City.BOSTON, City.MONTREAL, 2, R.color.train_grey));
        routes.add(new MapRoute(City.BOSTON, City.MONTREAL, 2, R.color.train_grey, true));
        routes.add(new MapRoute(City.BOSTON, City.NEW_YORK, 2, R.color.train_red));
        routes.add(new MapRoute(City.BOSTON, City.NEW_YORK, 2, R.color.train_yellow, true));
        routes.add(new MapRoute(City.CALGARY, City.HELENA, 4, R.color.train_grey));
        routes.add(new MapRoute(City.CALGARY, City.SEATTLE, 4, R.color.train_grey));
        routes.add(new MapRoute(City.CALGARY, City.VANCOUVER, 3, R.color.train_grey));
        routes.add(new MapRoute(City.CALGARY, City.WINNIPEG, 6, R.color.train_white));
        routes.add(new MapRoute(City.CHARLESTON, City.MIAMI, 4, R.color.train_pink)); // pin
        routes.add(new MapRoute(City.CHARLESTON, City.RALEIGH, 2, R.color.train_grey));
        routes.add(new MapRoute(City.CHICAGO, City.DULUTH, 3, R.color.train_red));
        routes.add(new MapRoute(City.CHICAGO, City.OMAHA, 4, R.color.train_blue));
        routes.add(new MapRoute(City.CHICAGO, City.PITTSBURGH, 3, R.color.train_black));
        routes.add(new MapRoute(City.CHICAGO, City.PITTSBURGH, 3, R.color.train_orange, true)); // orange
        routes.add(new MapRoute(City.CHICAGO, City.ST_LOUIS, 2, R.color.train_green));
        routes.add(new MapRoute(City.CHICAGO, City.ST_LOUIS, 2, R.color.train_white, true));
        routes.add(new MapRoute(City.CHICAGO, City.TORONTO, 4, R.color.train_white));
        routes.add(new MapRoute(City.DALLAS, City.EL_PASO, 4, R.color.train_red));
        routes.add(new MapRoute(City.DALLAS, City.HOUSTON, 1, R.color.train_grey));
        routes.add(new MapRoute(City.DALLAS, City.HOUSTON, 1, R.color.train_grey, true));
        routes.add(new MapRoute(City.DALLAS, City.LITTLE_ROCK, 2, R.color.train_grey));
        routes.add(new MapRoute(City.DALLAS, City.OKLAHOMA_CITY, 2, R.color.train_grey));
        routes.add(new MapRoute(City.DALLAS, City.OKLAHOMA_CITY, 2, R.color.train_grey, true));
        routes.add(new MapRoute(City.DENVER, City.HELENA, 4, R.color.train_green));
        routes.add(new MapRoute(City.DENVER, City.KANSAS_CITY, 4, R.color.train_black));
        routes.add(new MapRoute(City.DENVER, City.KANSAS_CITY, 4, R.color.train_orange, true)); // orange
        routes.add(new MapRoute(City.DENVER, City.OKLAHOMA_CITY, 4, R.color.train_red));
        routes.add(new MapRoute(City.DENVER, City.OMAHA, 4, R.color.train_pink)); // pink
        routes.add(new MapRoute(City.DENVER, City.PHOENIX, 5, R.color.train_white));
        routes.add(new MapRoute(City.DENVER, City.SALT_LAKE_CITY, 3, R.color.train_red));
        routes.add(new MapRoute(City.DENVER, City.SALT_LAKE_CITY, 3, R.color.train_yellow, true));
        routes.add(new MapRoute(City.DENVER, City.SANTA_FE, 2, R.color.train_grey));
        routes.add(new MapRoute(City.DULUTH, City.HELENA, 6, R.color.train_orange)); // orange
        routes.add(new MapRoute(City.DULUTH, City.OMAHA, 2, R.color.train_grey));
        routes.add(new MapRoute(City.DULUTH, City.OMAHA, 2, R.color.train_grey, true));
        routes.add(new MapRoute(City.DULUTH, City.SAULT_ST_MARIE, 3, R.color.train_grey));
        routes.add(new MapRoute(City.DULUTH, City.TORONTO, 6, R.color.train_pink)); // pink
        routes.add(new MapRoute(City.DULUTH, City.WINNIPEG, 4, R.color.train_black));
        routes.add(new MapRoute(City.EL_PASO, City.HOUSTON, 6, R.color.train_green));
        routes.add(new MapRoute(City.EL_PASO, City.LOS_ANGELES, 6, R.color.train_black));
        routes.add(new MapRoute(City.EL_PASO, City.OKLAHOMA_CITY, 5, R.color.train_yellow));
        routes.add(new MapRoute(City.EL_PASO, City.PHOENIX, 3, R.color.train_grey));
        routes.add(new MapRoute(City.EL_PASO, City.SANTA_FE, 2, R.color.train_grey));
        routes.add(new MapRoute(City.HELENA, City.OMAHA, 5, R.color.train_red));
        routes.add(new MapRoute(City.HELENA, City.PORTLAND, 6, R.color.train_yellow));
        routes.add(new MapRoute(City.HELENA, City.SALT_LAKE_CITY, 3, R.color.train_pink)); // pink
        routes.add(new MapRoute(City.HELENA, City.WINNIPEG, 4, R.color.train_blue));
        routes.add(new MapRoute(City.HOUSTON, City.NEW_ORLEANS, 2, R.color.train_grey));
        routes.add(new MapRoute(City.KANSAS_CITY, City.OKLAHOMA_CITY, 2, R.color.train_grey));
        routes.add(new MapRoute(City.KANSAS_CITY, City.OKLAHOMA_CITY, 2, R.color.train_grey, true)); // pink
        routes.add(new MapRoute(City.KANSAS_CITY, City.OMAHA, 1, R.color.train_grey));
        routes.add(new MapRoute(City.KANSAS_CITY, City.OMAHA, 1, R.color.train_grey, true));
        routes.add(new MapRoute(City.KANSAS_CITY, City.ST_LOUIS, 2, R.color.train_blue));
        routes.add(new MapRoute(City.KANSAS_CITY, City.ST_LOUIS, 2, R.color.train_pink, true)); // pink
        routes.add(new MapRoute(City.LAS_VEGAS, City.LOS_ANGELES, 2, R.color.train_grey));
        routes.add(new MapRoute(City.LAS_VEGAS, City.SALT_LAKE_CITY, 3, R.color.train_orange)); //orange
        routes.add(new MapRoute(City.LITTLE_ROCK, City.NASHVILLE, 3, R.color.train_white));
        routes.add(new MapRoute(City.LITTLE_ROCK, City.NEW_ORLEANS, 3, R.color.train_green));
        routes.add(new MapRoute(City.LITTLE_ROCK, City.OKLAHOMA_CITY, 2, R.color.train_grey));
        routes.add(new MapRoute(City.LITTLE_ROCK, City.ST_LOUIS, 2, R.color.train_grey));
        routes.add(new MapRoute(City.LOS_ANGELES, City.PHOENIX, 3, R.color.train_grey));
        routes.add(new MapRoute(City.LOS_ANGELES, City.SAN_FRANCISCO, 3, R.color.train_pink)); // pink
        routes.add(new MapRoute(City.LOS_ANGELES, City.SAN_FRANCISCO, 3, R.color.train_yellow, true));
        routes.add(new MapRoute(City.MIAMI, City.NEW_ORLEANS, 6, R.color.train_red));
        routes.add(new MapRoute(City.MONTREAL, City.NEW_YORK, 3, R.color.train_blue));
        routes.add(new MapRoute(City.MONTREAL, City.SAULT_ST_MARIE, 5, R.color.train_black));
        routes.add(new MapRoute(City.MONTREAL, City.TORONTO, 3, R.color.train_grey));
        routes.add(new MapRoute(City.NASHVILLE, City.PITTSBURGH, 4, R.color.train_yellow));
        routes.add(new MapRoute(City.NASHVILLE, City.RALEIGH, 3, R.color.train_black));
        routes.add(new MapRoute(City.NASHVILLE, City.ST_LOUIS, 2, R.color.train_grey));
        routes.add(new MapRoute(City.NEW_YORK, City.PITTSBURGH, 2, R.color.train_green));
        routes.add(new MapRoute(City.NEW_YORK, City.PITTSBURGH, 2, R.color.train_white, true));
        routes.add(new MapRoute(City.NEW_YORK, City.WASHINGTON, 2, R.color.train_black));
        routes.add(new MapRoute(City.NEW_YORK, City.WASHINGTON, 2, R.color.train_orange, true));
        routes.add(new MapRoute(City.OKLAHOMA_CITY, City.SANTA_FE, 3, R.color.train_blue));
        routes.add(new MapRoute(City.PHOENIX, City.SANTA_FE, 3, R.color.train_grey));
        routes.add(new MapRoute(City.PITTSBURGH, City.RALEIGH, 2, R.color.train_grey));
        routes.add(new MapRoute(City.PITTSBURGH, City.ST_LOUIS, 5, R.color.train_green));
        routes.add(new MapRoute(City.PITTSBURGH, City.TORONTO, 2, R.color.train_grey));
        routes.add(new MapRoute(City.PITTSBURGH, City.WASHINGTON, 2, R.color.train_grey));
        routes.add(new MapRoute(City.PORTLAND, City.SALT_LAKE_CITY, 6, R.color.train_blue));
        routes.add(new MapRoute(City.PORTLAND, City.SAN_FRANCISCO, 5, R.color.train_green));
        routes.add(new MapRoute(City.PORTLAND, City.SAN_FRANCISCO, 5, R.color.train_pink, true)); // pink
        routes.add(new MapRoute(City.PORTLAND, City.SEATTLE, 1, R.color.train_grey));
        routes.add(new MapRoute(City.PORTLAND, City.SEATTLE, 1, R.color.train_grey, true));
        routes.add(new MapRoute(City.RALEIGH, City.WASHINGTON, 2, R.color.train_grey));
        routes.add(new MapRoute(City.RALEIGH, City.WASHINGTON, 2, R.color.train_grey, true));
        routes.add(new MapRoute(City.SALT_LAKE_CITY, City.SAN_FRANCISCO, 5, R.color.train_orange)); // orange
        routes.add(new MapRoute(City.SALT_LAKE_CITY, City.SAN_FRANCISCO, 5, R.color.train_white, true));
        routes.add(new MapRoute(City.SAULT_ST_MARIE, City.TORONTO, 2, R.color.train_grey));
        routes.add(new MapRoute(City.SAULT_ST_MARIE, City.WINNIPEG, 6, R.color.train_grey));
        routes.add(new MapRoute(City.SEATTLE, City.VANCOUVER, 1, R.color.train_grey));
        routes.add(new MapRoute(City.SEATTLE, City.VANCOUVER, 1, R.color.train_grey, true));

        for (MapRoute route : routes) {
            Pair<String, String> key = new Pair<>(route.getStart().getKey(), route.getStop().getKey());
            if (routeMap.containsKey(key)) {
                key = new Pair<>(route.getStop().getKey(), route.getStart().getKey());
            }
            routeMap.put(key, route);
        }
    }

    private MapRoute(City start, City stop, Integer length, Integer color) {
        this(start, stop, length, color, false);
    }

    private MapRoute(City start, City stop, Integer length, Integer color, Boolean isDouble) {
        this(start, stop, length, color, isDouble, true);
    }

    private MapRoute(City start, City stop, Integer length, Integer color, Boolean isDouble, Boolean isClaimable) {
        this.color = color;
        this.isClaimable = isClaimable;
        this.isDouble = isDouble;
        this.length = length;
        this.start = start;
        this.stop = stop;
    }

    private MapRoute copy() {
        return new MapRoute(start, stop, length, color, isDouble, isClaimable);
    }

    public static Map<Pair<String, String>, MapRoute> copyRouteMap() {
        Map<Pair<String, String>, MapRoute> routeMapCopy = new HashMap<>();
        for (Map.Entry<Pair<String, String>, MapRoute> entry : routeMap.entrySet()) {
            routeMapCopy.put(entry.getKey(), entry.getValue().copy());
        }
        return routeMapCopy;
    }

    public Integer getBackground() {
        switch (color) {
            case R.color.train_white:
            case R.color.train_yellow:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }

    public Integer getColor() {
        return color;
    }

    public Boolean getIsClaimable() {
        return isClaimable;
    }

    public Integer getLength() {
        return length;
    }

    public LatLng getMidpoint() {
        LatLng start = getStartLatLng();
        LatLng stop = getStopLatLng();
        Double latitude = (start.latitude + stop.latitude) / 2;
        Double longitude = (start.longitude + stop.longitude) / 2;
        return new LatLng(latitude, longitude);
    }

    private LatLng getOffset() {
        Double dLat = stop.getLatitude() - start.getLatitude();
        Double dLong = stop.getLongitude() - start.getLongitude();
        Double magnitude = Math.sqrt(Math.pow(dLat, 2) + Math.pow(dLong, 2)) / DOUBLE_OFFSET;
        return new LatLng(dLong / magnitude, -dLat / magnitude);
    }

    public static Map<Pair<String, String>, MapRoute> getRouteMap() {
        return routeMap;
    }

    public City getStart() {
        return start;
    }

    public LatLng getStartLatLng() {
        Log.d(TAG, isDouble.toString());
        return isDouble ? offset(start.getLatLng()) : start.getLatLng();
    }

    public City getStop() {
        return stop;
    }

    public LatLng getStopLatLng() {
        return isDouble ? offset(stop.getLatLng()) : stop.getLatLng();
    }

    private LatLng offset(LatLng original) {
        LatLng offset = getOffset();
        Double latitude = original.latitude + offset.latitude;
        Double longitude = original.longitude + offset.longitude;
        return new LatLng(latitude, longitude);
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public void setIsClaimable(Boolean isClaimable) {
        this.isClaimable = isClaimable;
    }
}
