package cs340.client.services;

import cs340.client.communication.ServerProxy;
import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.requests.StartGameRequest;

public class StartGameService {
	private static ServerProxy proxy = new ServerProxy();

	public static void onGameStarted(Game game) {
		ClientModel.getInstance().startGame(game);
	}

	public static void startGame(Player player) {
		proxy.startGame(new StartGameRequest(player));
	}
}
