package cs340.client.services;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;

public class UpdateGameListService {
	//private static final UpdateGameListService singleton = new UpdateGameListService();

	public static void onUpdateGameList(ArrayList<Game> gameList){
		ClientModel.getInstance().setGameList(gameList);
	}
}
