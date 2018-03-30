package cs340.client.requests;

import cs340.client.model.Player;

public class EndTurnRequest {
	private Player player;

	public EndTurnRequest(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
