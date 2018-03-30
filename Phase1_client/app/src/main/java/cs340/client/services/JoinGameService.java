package cs340.client.services;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.requests.JoinGameRequest;
import cs340.client.communication.ServerProxy;

public class JoinGameService {
	private static ServerProxy proxy = new ServerProxy();

	public static void joinGame(int gameID, Player player, String color) {
		proxy.joinGame(new JoinGameRequest(gameID, player, color));
	}

	public static void onGameJoined(Game game) {
		ClientModel.getInstance().setCurrentGame(game);
	}
}
