package cs340.client.results;

import cs340.client.model.Game;

public class RejoinGameResult {
	private String username;
	private Game game;

	public RejoinGameResult(String username, Game g) {
		this.username = username;
		this.game = g;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game g) {
		this.game = g;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
