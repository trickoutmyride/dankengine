package cs340.client.requests;

import cs340.client.model.Player;

public class DrawTrainCardRequest {
	private Player p;

	public DrawTrainCardRequest(Player p) {
		this.p = p;
	}

	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}
}
