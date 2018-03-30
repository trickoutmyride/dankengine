package cs340.client.requests;

import java.util.ArrayList;

import cs340.client.model.DestinationCard;
import cs340.client.model.Player;

public class DiscardDestinationRequest {
	private int gameID;
	private ArrayList<DestinationCard> cards;
	private Player p;
	
	public DiscardDestinationRequest(int gameID, ArrayList<DestinationCard> cards, Player p) {
		this.gameID = gameID;
		this.cards = cards;
		this.p = p;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public ArrayList<DestinationCard> getCards() {
		return cards;
	}
	public void setCards(ArrayList<DestinationCard> cards) {
		this.cards = cards;
	}
	public Player getPlayer() {
		return p;
	}
	public void setPlayer(Player p) {
		this.p = p;
	}
}
