package cs340.ui.presenters;

import java.util.ArrayList;

import cs340.client.services.StartGameService;
import cs340.shared.model.ClientModel;
import cs340.shared.model.Game;
import cs340.shared.model.Player;
import cs340.ui.activities.ILobbyActivity;

public class LobbyPresenter implements ILobbyPresenter {
    private ILobbyActivity activity;

    public LobbyPresenter(ILobbyActivity activity) {
        this.activity = activity;
        ClientModel.getInstance().addGameLobbyObserver(this);
    }

    @Override
    public void detach() {
        ClientModel.getInstance().removeGameLobbyObserver(this);
    }

    @Override
    public void onError(String message) {
        activity.onError(message);
    }

    @Override
    public void onGameStarted() {
        activity.onGameStarted();
    }

    @Override
    public void onGameUpdated(Game game) {
        activity.onGameUpdated(game);
    }

    @Override
    public void startGame() {
        StartGameService.startGame(ClientModel.getInstance().getCurrentPlayer());
    }
}