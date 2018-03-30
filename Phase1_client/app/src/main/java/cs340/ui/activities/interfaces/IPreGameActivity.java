package cs340.ui.activities.interfaces;

import java.util.ArrayList;

import cs340.client.model.Game;

public interface IPreGameActivity {

    void onError(String message);

    void onGameListUpdated(ArrayList<Game> games);

    void onGameJoined(Game game);
}