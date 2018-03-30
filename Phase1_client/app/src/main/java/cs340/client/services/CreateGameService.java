package cs340.client.services;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.requests.CreateGameRequest;
import cs340.client.communication.ServerProxy;

public class CreateGameService {
	private static ServerProxy proxy = new ServerProxy();

	public static void createGame(String name, Player player, int capacity, String color) {
		proxy.createGame(new CreateGameRequest(name, player, capacity, color));
	}

	public static void onGameCreated(Game game) {
		ClientModel.getInstance().setCurrentGame(game);
	}
}
