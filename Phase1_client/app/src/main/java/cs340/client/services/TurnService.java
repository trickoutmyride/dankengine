package cs340.client.services;

import cs340.client.communication.ServerProxy;
import cs340.client.states.MyTurnState;
import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.requests.EndTurnRequest;

/**
 * Created by Mark on 3/9/2018.
 */

public class TurnService {
	private static ServerProxy proxy = new ServerProxy();

	public static void endTurn(Player player) {
		proxy.endTurn(new EndTurnRequest(player));
	}

	public static void nextTurn(Game game) {
		ClientModel.getInstance().changeTurn(game);
		String usernameWithTurn = game.getPlayers().get(game.getTurn()).getUsername();
		String usernameOfClient = ClientModel.getInstance().getCurrentPlayer().getUsername();
		System.out.println("TurnService: My username is: " + usernameOfClient);
		System.out.println("TurnService: The next username is: " + usernameWithTurn);
		if (usernameWithTurn.equals(usernameOfClient)) {
			System.out.println("So I get to go next!");
			ServerProxy.setTurnState(new MyTurnState());
		}
		ClientModel.getInstance().replaceHistory(game.getHistory());
	}
}
