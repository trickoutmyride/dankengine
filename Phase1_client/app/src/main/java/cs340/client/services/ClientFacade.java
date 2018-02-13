package cs340.client.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cs340.shared.interfaces.IClient;
import cs340.shared.model.Game;
import cs340.shared.model.Player;

/**
 * Executes functions based on ClientCommands processed by CommandProcessor coming from the server.
 * I need things to fill this with.
 */
public class ClientFacade implements IClient {
	private static Gson gson = new Gson();
	private static ClientFacade singleton;

	public static ClientFacade getInstance(){
		if (singleton == null) singleton = new ClientFacade();
		return singleton;
	}

	public void createGame(String gameJson) {
		System.out.println("ClientFacade: createGame()");
		Game game = gson.fromJson(gameJson, Game.class);
		CreateGameService.onGameCreated((Game) game);
	}

	public void joinGame(String gameJson) {
		System.out.println("ClientFacade: joinGame()");
		Game game = gson.fromJson(gameJson, Game.class);
		JoinGameService.onGameJoined((Game) game);
	}

	public void login(String playerJson) {
		System.out.println("ClientFacade: login()");
		Player player = gson.fromJson(playerJson, Player.class);
		LoginService.onLogin((Player) player);
	}

	public void register(String playerJson) {
		System.out.println("ClientFacade: register()");
		Player player = gson.fromJson(playerJson, Player.class);
		RegisterService.onRegister((Player) player);
	}

	public void startGame() {
		System.out.println("ClientFacade: startGame()");
		StartGameService.onGameStarted();
	}
	
	public void updateGameList(String gameListJson) {
		System.out.println("ClientFacade: updateGameList()");
		ArrayList<Game> gameList = gson.fromJson(gameListJson, new TypeToken<List<Game>>(){}.getType());
		UpdateGameListService.onUpdateGameList(gameList);
	}

	public void error(String error) {
		System.out.println("ClientFacade: error(): " + error);
		ErrorService.onError(error);
	}
}