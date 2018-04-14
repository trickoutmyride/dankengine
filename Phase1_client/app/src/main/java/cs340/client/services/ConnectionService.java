package cs340.client.services;

import cs340.client.model.ClientModel;

public class ConnectionService {

	public static void serverConnected() {
		ClientModel.getInstance().serverConnected();
	}

	public static void serverDisconnected() {
		ClientModel.getInstance().serverDisconnected();
	}
}
