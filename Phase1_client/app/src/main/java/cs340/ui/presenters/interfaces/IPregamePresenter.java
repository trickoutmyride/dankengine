package cs340.ui.presenters.interfaces;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;

public interface IPregamePresenter extends ClientModel.GameListObserver {

    void detach();
    void createGame(String name, Player player, int capacity, String color);
    void joinGame(int gameID, Player player, String color);
    void onGameListUpdated(ArrayList<Game> games);
}
