package cs340.client.results;

import java.util.ArrayList;

import cs340.client.model.Player;

public class DiscardDestinationResult {
	private Player p;
	private ArrayList<String> auths;
	private int discarded;
	
	public DiscardDestinationResult(Player p, int discarded, ArrayList<String> auths) {
		this.p = p;
		this.discarded = discarded;
		this.auths = auths;
	}
	public Player getPlayer() {
		return p;
	}
	public void setPlayer(Player p) {
		this.p = p;
	}
	public ArrayList<String> getAuths() {
		return auths;
	}
	public void setAuths(ArrayList<String> auths) {
		this.auths = auths;
	}
	public int getDiscarded() {
		return discarded;
	}
	public void setDiscarded(int discarded) {
		this.discarded = discarded;
	}
}
