package cs340.shared.requests;

import cs340.shared.model.Player;

public class DrawDestinationRequest {
	private Player player;

	public DrawDestinationRequest(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}

