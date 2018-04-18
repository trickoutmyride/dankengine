package cs340.client.services;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.requests.JoinGameRequest;
import cs340.client.communication.ServerProxy;
import cs340.client.requests.RejoinGameRequest;
import cs340.client.states.MyTurnState;

public class JoinGameService {
	private static ServerProxy proxy = new ServerProxy();

	public static void joinGame(int gameID, Player player, String color) {
		proxy.joinGame(new JoinGameRequest(gameID, player, color));
	}

	public static void rejoinGame(Player player) {
		proxy.rejoinGame(new RejoinGameRequest(player));
	}

	public static void onGameJoined(Game game) {
		ClientModel.getInstance().setCurrentGame(game);
	}
	public static void onGameRejoined(Game game) {
		String usernameWithTurn = game.getPlayers().get(game.getTurn()).getUsername();
		String usernameOfClient = ClientModel.getInstance().getCurrentPlayer().getUsername();
		System.out.println("TurnService: My username is: " + usernameOfClient);
		System.out.println("TurnService: The next username is: " + usernameWithTurn);
		if (usernameWithTurn.equals(usernameOfClient)) {
			System.out.println("So I get to go next!");
			ServerProxy.setTurnState(new MyTurnState());
		}
		ClientModel.getInstance().rejoinGame(game);
	}
}
