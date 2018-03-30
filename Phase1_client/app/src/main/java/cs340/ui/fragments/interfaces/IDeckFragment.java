package cs340.ui.fragments.interfaces;

import java.util.ArrayList;

import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.model.TrainCard;
import cs340.ui.activities.GameActivity;

public interface IDeckFragment {
    Game getCurrentGame();
    Player getCurrentPlayer();
    void onFaceUpCardUpdated(TrainCard card, int index);
    GameActivity getGameActivity();
    void initializeFaceUpCards(ArrayList<TrainCard> cards);
    void updateDeckCount(int count);
}
