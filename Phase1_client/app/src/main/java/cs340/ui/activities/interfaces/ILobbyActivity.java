package cs340.ui.activities.interfaces;

import cs340.client.model.Game;

public interface ILobbyActivity {
    void onError(String message);
    void onGameStarted(Game game);
    void onGameUpdated(Game game);
}
