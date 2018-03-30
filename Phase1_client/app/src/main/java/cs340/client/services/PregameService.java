package cs340.client.services;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;

public class PregameService {
    public static void onGameListUpdated(ArrayList<Game> games) {
        ClientModel.getInstance().setGameList(games);
    }
}
