package cs340.client.communication;

import com.google.gson.Gson;

import cs340.client.command.CommandManager;
import cs340.client.states.MyTurnState;
import cs340.client.states.NotMyTurnState;
import cs340.client.states.TurnState;
import cs340.client.command.ServerCommand;
import cs340.client.interfaces.IServer;
import cs340.client.message.ServerMessage;
import cs340.client.requests.ChatRequest;
import cs340.client.requests.ClaimRouteRequest;
import cs340.client.requests.CreateGameRequest;
import cs340.client.requests.DiscardDestinationRequest;
import cs340.client.requests.DrawDestinationRequest;
import cs340.client.requests.DrawFaceupRequest;
import cs340.client.requests.DrawTrainCardRequest;
import cs340.client.requests.EndTurnRequest;
import cs340.client.requests.JoinGameRequest;
import cs340.client.requests.SignInRequest;
import cs340.client.requests.StartGameRequest;

public class ServerProxy implements IServer {
	private static Gson gson = new Gson();
	private static TurnState turnState;

	/**
	 * Called by the TurnService when an EndTurn command is received informing us that it is now our turn.
	 * @param newTurnState
	 */
	public static void setTurnState(TurnState newTurnState) {
		turnState = newTurnState;
	}


	public void createGame(Object request) {
		turnState = new MyTurnState();
		CreateGameRequest createRequest = (CreateGameRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("createGame", request);
		ServerMessage message = new ServerMessage(createRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}


	public void joinGame(Object request) {
		turnState = new NotMyTurnState();
		JoinGameRequest joinRequest = (JoinGameRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("joinGame", request);
		ServerMessage message = new ServerMessage(joinRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}

	public void login(Object request, String address) {
		ClientCommunicator.initialize(address);
		SignInRequest loginRequest = (SignInRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("login", request);
		ServerMessage message = new ServerMessage(loginRequest.getUsername(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}

	public void register(Object request, String address) {
		ClientCommunicator.initialize(address);
		SignInRequest registerRequest = (SignInRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("register", request);
		ServerMessage message = new ServerMessage(((SignInRequest) request).getUsername(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}

	public void startGame(Object request) {
		StartGameRequest startRequest = (StartGameRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("startGame", request);
		ServerMessage message = new ServerMessage(startRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}

	public void claimRoute(Object request) {
		ClaimRouteRequest claimRequest = (ClaimRouteRequest) request;
		turnState = turnState.claimRoute();
		if (!turnState.isSuccess()) return;

		ServerCommand command = CommandManager.getInstance().makeCommand("claimRoute", request);
		ServerMessage message = new ServerMessage(claimRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);

		this.endTurn(new EndTurnRequest(claimRequest.getPlayer()));
	}

	public void drawDestination(Object request) {
		DrawDestinationRequest drawRequest = (DrawDestinationRequest) request;
		turnState = turnState.drawDestination();
		if (!turnState.isSuccess()) return;

		ServerCommand command = CommandManager.getInstance().makeCommand("drawDestination", request);
		ServerMessage message = new ServerMessage(drawRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);

		// It is no longer your turn per state pattern.
		if(turnState.getClass() == NotMyTurnState.class) {
			this.endTurn(new EndTurnRequest(drawRequest.getPlayer()));
		}
	}

	/**
	 * @param isDuringGame if true, change turnState, false for the first set you draw at start of game.
	 */
	public void discardDestination(Object request, boolean isDuringGame) {
		DiscardDestinationRequest discardRequest = (DiscardDestinationRequest) request;
		if (isDuringGame) {
			turnState = turnState.drawDestination();
			if (!turnState.isSuccess()) return;
		}

		ServerCommand command = CommandManager.getInstance().makeCommand("discardDestination", request);
		ServerMessage message = new ServerMessage(discardRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);

		// It is no longer your turn per state pattern. It should always be NotMyTurnState at this point
		if(turnState.getClass() == NotMyTurnState.class) {
			this.endTurn(new EndTurnRequest(discardRequest.getPlayer()));
		}
	}

	public void drawTrainCard (Object request) {
		DrawTrainCardRequest drawRequest = (DrawTrainCardRequest) request;
		turnState = turnState.drawTrainCard();
		if (!turnState.isSuccess()) return;

		ServerCommand command = CommandManager.getInstance().makeCommand("drawTrainCard", request);
		ServerMessage message = new ServerMessage(drawRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);

		// only send the endTurn if it is no longer your turn per state pattern.
		if(turnState.getClass() == NotMyTurnState.class) {
			this.endTurn(new EndTurnRequest(drawRequest.getPlayer()));
		}
	}

	public void drawFaceupCard (Object request) {
		System.out.println("drawFaceupCard");
		DrawFaceupRequest drawRequest = (DrawFaceupRequest) request;
		turnState = turnState.drawFaceupCard(drawRequest);
		if (!turnState.isSuccess()) return;

		ServerCommand command = CommandManager.getInstance().makeCommand("drawFaceup", request);
		ServerMessage message = new ServerMessage(drawRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);

		// only send the endTurn if it is no longer your turn per state pattern.
		if(turnState.getClass() == NotMyTurnState.class) {
			this.endTurn(new EndTurnRequest(drawRequest.getPlayer()));
		}
	}

	public void endTurn(Object request) {
		System.out.println("Sending endTurn");
		EndTurnRequest endRequest = (EndTurnRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("endTurn", request);
		ServerMessage message = new ServerMessage(endRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}

	public void chat(Object request) {
		ChatRequest chatRequest = (ChatRequest) request;
		ServerCommand command = CommandManager.getInstance().makeCommand("chat", request);
		ServerMessage message = new ServerMessage(chatRequest.getPlayer().getAuthToken(), command);
		ClientCommunicator.getInstance().sendMessage(message);
	}
}