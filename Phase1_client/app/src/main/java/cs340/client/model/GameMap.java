package cs340.client.model;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs340.ui.R;
import cs340.ui.presenters.MapPresenter;

public class GameMap {
    public static final String TAG = GameMap.class.getSimpleName();
    private List<Observer> observers = new ArrayList<>();
    private Map<Pair<String, String>, MapRoute> routes = MapRoute.copyRouteMap();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private MapRoute getRoute(String start, String end, String routeColor) {
        List<Pair<String, String>> keys = new ArrayList<>();
        keys.add(new Pair<>(start, end));
        keys.add(new Pair<>(end, start));
        for (Pair<String, String> key : keys) {
            if (routes.containsKey(key)) {
                MapRoute route = routes.get(key);
                if (routeColor.equals(MapPresenter.colors.get(route.getColor()))) return route;
            }
        }
        return null;
    }

    private void markAllUnclaimable(String start, String end) {
        Pair<String, String> forwards = new Pair<>(start, end);
        Pair<String, String> backwards = new Pair<>(end, start);
        if (routes.containsKey(forwards)) routes.get(forwards).setIsClaimable(false);
        if (routes.containsKey(backwards)) routes.get(backwards).setIsClaimable(false);
    }

    public void onRouteClaimed(String username, String start, String end, String routeColor) {
        String colorName = ClientModel.getInstance().getCurrentGame().getColors().get(username);
        Integer color;
        switch (colorName) {
            case "black":
                color = R.color.player_black;
                break;
            case "blue":
                color = R.color.player_blue;
                break;
            case "green":
                color = R.color.player_green;
                break;
            case "red":
                color = R.color.player_red;
                break;
            case "yellow":
                color = R.color.player_yellow;
                break;
            default:
                color = 0;
        }
        MapRoute route = getRoute(start, end, routeColor);
        if (ClientModel.getInstance().getCurrentGame().getCapacity() < 4) markAllUnclaimable(start, end);
        if (route != null) {
            Log.d(TAG, route.getStart().getKey() + " -> " + route.getStop().getKey() + " claimed with " + colorName);
            route.setColor(color);
            route.setIsClaimable(false);
            for (Observer observer : observers) observer.onRouteClaimed(routes);
        } else {
            Log.d(TAG, "could not find route");
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public interface Observer {
        void onRouteClaimed(Map<Pair<String, String>, MapRoute> routes);
    }
}
