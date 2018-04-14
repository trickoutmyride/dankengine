package cs340.ui.presenters.interfaces;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.DestinationCard;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.fragments.ChatFragment;

public interface IGamePresenter extends ClientModel.GameObserver, ChatFragment.ChatFragmentListener {

    void onDrawnDestinationCards(ArrayList<DestinationCard> cards, Player player);
    void onTurnChanged(Game game);
    void onHistoryReplaced(ArrayList<String> history);
}
