package cs340.client.results;

import java.util.ArrayList;

import cs340.shared.model.Player;
import cs340.shared.model.TrainCard;

public class DrawFaceupResult {
	private TrainCard card;
	private TrainCard newCard;
	private int index;
	private Player player;
	private ArrayList<String> auths;
	
	public DrawFaceupResult(TrainCard card, TrainCard newCard, int index, Player player, ArrayList<String> auths) {
		this.card = card;
		this.newCard = newCard;
		this.index = index;
		this.player = player;
		this.auths = auths;
	}

	public TrainCard getDrawnCard() {
		return card;
	}

	public void setDrawnCard(TrainCard card) {
		this.card = card;
	}

	public TrainCard getNewCard() {
		return newCard;
	}

	public void setNewCard(TrainCard newCard) {
		this.newCard = newCard;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<String> getAuths() {
		return auths;
	}

	public void setAuths(ArrayList<String> auths) {
		this.auths = auths;
	}
}
