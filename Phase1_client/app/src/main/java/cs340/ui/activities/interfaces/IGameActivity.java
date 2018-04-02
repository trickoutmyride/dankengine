package cs340.ui.activities.interfaces;

import java.util.ArrayList;

import cs340.client.model.DestinationCard;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.model.TrainCard;

public interface IGameActivity {
    //stub
    void onHistoryItemUpdated(String historyItem);
    void onDrawnDestinationCards(ArrayList<DestinationCard> cards, boolean gameStarted);
    void onChatUpdated(String message);
    void onPlayerCardsUpdated(final int index, final TrainCard oldCard, final TrainCard newCard, final Player player, final ArrayList<TrainCard> trainCards);
    void onPlayerCardsUpdated(Player player);
    Player getCurrentPlayer();
    void onDestinationCardsUpdated(Player player);
    void onPlayerUpdated(final Player player);
    boolean myTurn();
    int getTurnIndex();
    void onTurnUpdated(final Game game);
    void onError(String message);
    void onGameEnded(Game game);
    void setCurrentGame(Game game);
    void onHistoryReplaced(ArrayList<String> history);
}
