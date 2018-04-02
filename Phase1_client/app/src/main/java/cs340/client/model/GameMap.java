package cs340.client.model;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs340.ui.R;

public class GameMap {
    public static final String TAG = GameMap.class.getSimpleName();
    private List<Observer> observers = new ArrayList<>();
    private Map<Pair<String, String>, MapRoute> routes = MapRoute.copyRouteMap();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void onRouteClaimed(String username, String start, String end) {
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
        Log.d(TAG, "onRouteClaimed with " + Integer.toString(observers.size()) + " observers");
        Pair<String, String> key = new Pair<>(start, end);
        if (!routes.containsKey(key)) key = new Pair<>(end, start);
        routes.get(key).setColor(color);
        for (Observer observer : observers) observer.onRouteClaimed(routes);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public interface Observer {
        void onRouteClaimed(Map<Pair<String, String>, MapRoute> routes);
    }
}
