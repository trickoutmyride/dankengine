package cs340.ui.presenters;

import android.app.Activity;

import java.util.ArrayList;

import cs340.client.services.CreateGameService;
import cs340.client.services.JoinGameService;
import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.activities.interfaces.IPreGameActivity;
import cs340.ui.presenters.interfaces.IPregamePresenter;

public class PregamePresenter implements IPregamePresenter {
    private IPreGameActivity activity;

    public PregamePresenter(IPreGameActivity activity) {
        this.activity = activity;
        ClientModel.getInstance().addGameListObserver(this);
    }

    @Override
    public void createGame(String name, Player player, int capacity, String color) {
        CreateGameService.createGame(name, player, capacity, color);
    }

    @Override
    public void detach() {
        ClientModel.getInstance().removeGameListObserver(this);
    }

    @Override
    public void joinGame(int gameID, Player player, String color) {
        JoinGameService.joinGame(gameID, player, color);
    }

    @Override
    public void rejoinGame(Player player){
        JoinGameService.rejoinGame(player);
    }

    @Override
    public void onError(final String message) {
        ((Activity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onError(message);
            }
        });
    }

    @Override
    public void onCurrentGameSet(Game game) {
        final Game currentGame = game;
        ((Activity) activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onGameJoined(currentGame);
            }
        });
    }

    @Override
    public void onGameListUpdated(final ArrayList<Game> games) {
        System.out.println("onGameListUpdated Presenter");
        System.out.println("Games(0): " + games.get(0).getGameName());
        ((Activity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onGameListUpdated(games);
            }
        });
    }
}
