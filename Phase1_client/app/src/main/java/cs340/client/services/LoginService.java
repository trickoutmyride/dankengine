package cs340.client.services;

import cs340.client.communication.ServerProxy;
import cs340.client.model.ClientModel;
import cs340.client.model.Player;
import cs340.client.requests.SignInRequest;

public class LoginService {
    private static ServerProxy proxy = new ServerProxy();

    public static void login(String username, String password, String address) {
        proxy.login(new SignInRequest(username, password), address);
    }

    public static void onLogin(Player player) {
        ClientModel.getInstance().setCurrentPlayer(player);
    }
}
