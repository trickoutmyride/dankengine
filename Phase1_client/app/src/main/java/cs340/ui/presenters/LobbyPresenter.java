package cs340.ui.presenters;

import android.app.Activity;

import cs340.client.services.StartGameService;
import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.ui.activities.interfaces.ILobbyActivity;
import cs340.ui.presenters.interfaces.ILobbyPresenter;

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
    public void onError(final String message) {
        ((Activity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onError(message);
            }
        });
    }

    @Override
    public void onGameStarted(final Game game) {
        ((Activity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onGameStarted(game);
            }
        });
    }

    @Override
    public void onGameUpdated(final Game game) {
        ((Activity)activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onGameUpdated(game);
            }
        });
    }

    @Override
    public void startGame() {
        StartGameService.startGame(ClientModel.getInstance().getCurrentPlayer());
    }
}
