package cs340.ui.presenters;

import java.util.ArrayList;

import cs340.client.services.ChatService;
import cs340.client.model.ClientModel;
import cs340.client.model.DestinationCard;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.activities.interfaces.IGameActivity;
import cs340.ui.presenters.interfaces.IGamePresenter;

//GamePresenter implements HistoryObserver to update the history in the Activity when the dialog is not currently shown

public class GamePresenter implements IGamePresenter, ClientModel.HistoryObserver, ClientModel.ChatObserver, ClientModel.PlayersObserver {
    private IGameActivity gameActivity;

    public GamePresenter(IGameActivity gameActivity){
        this.gameActivity = gameActivity;
        ClientModel.getInstance().addChatObserver(this);
        ClientModel.getInstance().addHistoryObserver(this);
        ClientModel.getInstance().addGameObserver(this);
    }

    public void detach(){
        ClientModel.getInstance().removeChatObserver(this);
        ClientModel.getInstance().removeHistoryObserver(this);
        ClientModel.getInstance().addGameObserver(this);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onHistoryUpdated(String historyItem) {
        gameActivity.onHistoryItemUpdated(historyItem);
    }

    @Override
    public void onHistoryReplaced(ArrayList<String> history){
        gameActivity.onHistoryReplaced(history);
    }

    @Override
    public void onDrawnDestinationCards(ArrayList<DestinationCard> cards, Player player){
        if (ClientModel.getInstance().getCurrentPlayer().getUsername().equals(player.getUsername())) {
            gameActivity.onDrawnDestinationCards(cards, true);
        }
    }

    @Override
    public void onMessageUpdated(final String message) {
        gameActivity.onChatUpdated(message);
    }

    @Override
    public void sendMessage(String message) {
        ChatService.chat(gameActivity.getCurrentPlayer(), message);
    }

    @Override
    public void onDestinationCardsUpdated(Player player){
        gameActivity.onDestinationCardsUpdated(player);
    }

    @Override
    public void onTurnChanged(Game game) {
        gameActivity.onTurnUpdated(game);
        ClientModel.getInstance().setCurrentGame(game);

    }

    @Override
    public void onGameEnded(Game game) {
        gameActivity.onGameEnded(game);
    }

    @Override
    public void onPlayerUpdated(Player player) {
        if (player.getUsername().equals(ClientModel.getInstance().getCurrentPlayer().getUsername())){
            gameActivity.onPlayerUpdated(player);
            gameActivity.onPlayerCardsUpdated(player);
        }
    }

    @Override
    public void onPlayersUpdated(ArrayList<Player> players) {

    }
}
