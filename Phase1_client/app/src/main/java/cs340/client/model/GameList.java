package cs340.client.model;

import java.util.ArrayList;

public class GameList {
	/* Fields */
	private ArrayList<Game> games;

	/* Methods */
	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}

	public String addGame(Game g){
		if(!this.existsGame(g)){
			this.games.add(g);
			return "";
		}else{
			return "Err: Game exists with that name";
		}
	}

	public boolean addPlayerToGame(Player p, int id, String color){
		//return this.games.get(id).addPlayer(p,color);
		return false;
	}

	public boolean existsGame(Game g){
		if(this.games == null){
			return false;
		}else{
			for(Game game : this.games){
				if(game.getGameName().equals(g.getGameName())){
					return true;
				}
			}
			return false;
		}
	}
}
