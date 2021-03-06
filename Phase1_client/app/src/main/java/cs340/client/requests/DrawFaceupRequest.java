package cs340.client.requests;

import cs340.client.model.Player;

public class DrawFaceupRequest {
	private Player p;
	private int index;
	
	public DrawFaceupRequest(Player p, int index) {
		this.p = p;
		this.index = index;
	}

	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
