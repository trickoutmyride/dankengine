package cs340.ui.presenters;

import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cs340.client.services.MapService;
import cs340.shared.model.ClientModel;
import cs340.shared.model.GameMap;
import cs340.shared.model.MapRoute;
import cs340.shared.model.Player;
import cs340.shared.model.TrainCard;
import cs340.ui.R;
import cs340.ui.activities.ClaimRouteActivity;
import cs340.ui.fragments.interfaces.IMapFragment;
import cs340.ui.presenters.interfaces.IMapPresenter;

public class MapPresenter implements GameMap.Observer, IMapPresenter {
    private static Map<Integer, String> colors = new HashMap<>();
    private GameMap map = ClientModel.getInstance().getCurrentGame().getGameMap();
    private IMapFragment mapFragment;

    static {
        colors.put(R.color.train_black, "black");
        colors.put(R.color.train_blue, "blue");
        colors.put(R.color.train_grey, "grey");
        colors.put(R.color.train_green, "green");
        colors.put(R.color.train_orange, "orange");
        colors.put(R.color.train_pink, "pink");
        colors.put(R.color.train_red, "red");
        colors.put(R.color.train_white, "white");
        colors.put(R.color.train_yellow, "yellow");
    }

    public MapPresenter(IMapFragment mapFragment){
        this.mapFragment = mapFragment;
        map.addObserver(this);
    }

    public void claimRoute(Intent data) {
        String[] cards = ClaimRouteActivity.getCards(data);
        String color = ClaimRouteActivity.getColor(data);
        String start = ClaimRouteActivity.getStart(data);
        String stop = ClaimRouteActivity.getStop(data);
        Player player = ClientModel.getInstance().getCurrentPlayer();
        ArrayList<TrainCard> trainCards = new ArrayList<>();

        for (String card : cards) {
            trainCards.add(new TrainCard(card));
        }
        MapService.claimRoute(player, start, stop, color, trainCards);
    }

    public void claimRoute(MapRoute route) {
        String color = colors.get(route.getColor());
        Integer size = route.getLength();
        String start = route.getStart().getKey();
        String stop = route.getStop().getKey();
        ArrayList<String> trainCards = new ArrayList<>();
        for (TrainCard card : ClientModel.getInstance().getCurrentPlayer().getCards()) {
            trainCards.add(card.getColor());
        }
        Log.d("MapPresenter", "Claiming " + start + " -> " + stop);
        Log.d("MapPresenter", "Card count: " + Integer.toString(trainCards.size()));
//        Log.d("MapPresenter", "Cards: " + Arrays.toString(trainCards.toArray(new String[trainCards.size()])));
        ClaimRouteActivity.IntentFactory factory = new ClaimRouteActivity.IntentFactory()
//                .cards(trainCards.toArray(new String[trainCards.size()]))
                .cards(trainCards.toArray(new String[0]))
                .color(color)
                .size(size)
                .start(start)
                .stop(stop);
        mapFragment.claimRoute(factory);
    }

    public void detach() {
        map.removeObserver(this);
    }

    @Override
    public void onRouteClaimed(Map<Pair<String, String>, MapRoute> routes) {
        mapFragment.onRouteClaimed(routes);
    }
}
