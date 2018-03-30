package cs340.ui.presenters.interfaces;

import cs340.client.model.ClientModel;

public interface ILobbyPresenter extends ClientModel.GameLobbyObserver {
    void detach();
    void startGame();
}
