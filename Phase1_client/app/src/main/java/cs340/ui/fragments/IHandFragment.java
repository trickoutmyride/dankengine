package cs340.ui.fragments;

import java.util.ArrayList;

import cs340.shared.model.TrainCard;

public interface IHandFragment {
    void onTrainCardsUpdated(ArrayList<TrainCard> cards);
}