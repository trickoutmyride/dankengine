package cs340.client.services;

import com.google.gson.Gson;

import cs340.client.interfaces.IClient;
import cs340.client.model.ClientModel;
import cs340.client.model.GameList;
import cs340.client.results.ClaimRouteResult;
import cs340.client.results.CreateGameResult;
import cs340.client.results.DiscardDestinationResult;
import cs340.client.results.DrawDestinationResult;
import cs340.client.results.DrawFaceupResult;
import cs340.client.results.DrawTrainCardResult;
import cs340.client.results.EndTurnResult;
import cs340.client.results.GameHistoryResult;
import cs340.client.results.GameResult;
import cs340.client.results.JoinGameResult;
import cs340.client.results.RejoinGameResult;
import cs340.client.results.SignInResult;

/**
 * Executes functions based on ClientCommands processed by CommandProcessor coming from the server.
 * I need things to fill this with.
 */
public class ClientFacade implements IClient {
	private static Gson gson = new Gson();
	private static ClientFacade singleton;

	public static ClientFacade getInstance() {
		if (singleton == null) singleton = new ClientFacade();
		return singleton;
	}

	public void createGame(String gameJson) {
		System.out.println("ClientFacade: createGame()");
		//Game game = gson.fromJson(gameJson, Game.class);
		CreateGameResult result = gson.fromJson(gameJson, CreateGameResult.class);
		CreateGameService.onGameCreated(result.getGame());
		UpdateGameListService.onUpdateGameList(result.getGames());
	}

	public void joinGame(String gameJson) {
		System.out.println("ClientFacade: joinGame()");
		//Game game = gson.fromJson(gameJson, Game.class);
		JoinGameResult result = gson.fromJson(gameJson, JoinGameResult.class);
		JoinGameService.onGameJoined(result.getGame());
	}

	public void rejoinGame(String rejoinJson) {
		System.out.println("ClientFacade: rejoinGame()");
		RejoinGameResult result = gson.fromJson(rejoinJson, RejoinGameResult.class);
		JoinGameService.onGameRejoined(result.getGame());
	}

	public void reconnect(String reconnectJson) {
		//intentionally do nothing.
	}

	public void login(String signInJson) {
		System.out.println("ClientFacade: login()");
		SignInResult result = gson.fromJson(signInJson, SignInResult.class);
		LoginService.onLogin(result.getPlayer());
		UpdateGameListService.onUpdateGameList(result.getGamelist());
	}

	public void register(String signInJson) {
		System.out.println("ClientFacade: register()");
		//Player player = gson.fromJson(playerJson, Player.class);
		SignInResult result = gson.fromJson(signInJson, SignInResult.class);
		RegisterService.onRegister(result.getPlayer());
		UpdateGameListService.onUpdateGameList(result.getGamelist());
	}

	public void startGame(String startGameJson) {
		System.out.println("ClientFacade: startGame() + startGameJson");
		GameResult result = gson.fromJson(startGameJson, GameResult.class);
		StartGameService.onGameStarted(result.getGame());
	}

	public void updateGameList(String gameListJson) {
		System.out.println("ClientFacade: updateGameList()" + gameListJson);
		GameList gameList = gson.fromJson(gameListJson, GameList.class);
		UpdateGameListService.onUpdateGameList(gameList.getGames());
	}

	public void drawDestination(String destinationJson) {
		System.out.println("ClientFacade: drawDestination()" + destinationJson);
		DrawDestinationResult result = gson.fromJson(destinationJson, DrawDestinationResult.class);
		DeckService.onDrawDestinationCards(result.getCards(), result.getPlayer());
	}

	public void discardDestination(String destinationJson) {
		System.out.println("ClientFacade: discardDestination()" + destinationJson);
		DiscardDestinationResult result = gson.fromJson(destinationJson, DiscardDestinationResult.class);
		DeckService.onDiscardDestinationCards(result.getPlayer());
	}

	public void drawFaceup(String trainJson) {
		System.out.println("ClientFacade: drawFaceup()" + trainJson);
		DrawFaceupResult result = gson.fromJson(trainJson, DrawFaceupResult.class);
		DeckService.onDrawTrainCards(result.getIndex(), result.getDrawnCard(), result.getNewCard(), result.getPlayer(), result.getFaceupCards());
	}

	public void drawTrainCard(String trainJson) {
		System.out.println("ClientFacade: drawTrainCard()" + trainJson);
		DrawTrainCardResult result = gson.fromJson(trainJson, DrawTrainCardResult.class);
		DeckService.onDrawDeckCard(result.getPlayer());
	}

	public void chat (String chat) {
		System.out.println("ClientFacade: chat()" + chat);
		ChatService.onChat(chat);
	}

	public void gameHistory(String gameHistoryJson) {
		System.out.println("ClientFacade: gameHistory()" + gameHistoryJson);
		GameHistoryResult result = gson.fromJson(gameHistoryJson, GameHistoryResult.class);
		ClientModel.getInstance().updateHistory(result.getType() + ": " + result.getContents());
	}

	public void endTurn(String endTurnJson) {
		System.out.println("ClientFacade: endTurn()" + endTurnJson);
		EndTurnResult result = gson.fromJson(endTurnJson, EndTurnResult.class);
		TurnService.nextTurn(result.getGame());
	}

	public void endGame(String endTurnJson) {
		System.out.println("ClientFacade: endTurn()" + endTurnJson);
		EndTurnResult result = gson.fromJson(endTurnJson, EndTurnResult.class); // has same info as EndTurnResult
		TurnService.endGame(result.getGame());
	}

	public void claimRoute(String routeJson) {
		System.out.println("ClientFacade: claimRoute() " + routeJson);
		ClaimRouteResult result = gson.fromJson(routeJson, ClaimRouteResult.class);
		MapService.onRouteClaimed(result.getUsername(), result.getRoute(), result.getPlayer());
	}

	public void error(String error) {
		System.out.println("ClientFacade: error(): " + error);
		ErrorService.onError(error);
	}
}