package cs340.client.command;

import com.google.gson.Gson;

import cs340.client.communication.ClientCommunicator;
import cs340.shared.command.ServerCommand;
import cs340.shared.interfaces.ICommand;
import cs340.shared.message.Message;

public class CommandManager {
	private static CommandManager instance = new CommandManager();
	private static Gson gson = new Gson();

	private CommandManager() {
	}

	public static CommandManager getInstance() {
		return instance;
	}

	public ServerCommand makeCommand(String function, Object payload) {
		return new ServerCommand(function, gson.toJson(payload));
	}

	public void sendCommand(String senderAuth, ICommand command) {
		ClientCommunicator.getInstance().sendMessage(new Message(senderAuth, command));
	}
}