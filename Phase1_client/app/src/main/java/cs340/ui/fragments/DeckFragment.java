package cs340.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cs340.shared.model.Game;
import cs340.shared.model.Player;
import cs340.shared.model.TrainCard;
import cs340.ui.R;
import cs340.ui.activities.GameActivity;
import cs340.ui.presenters.DeckPresenter;
import cs340.ui.presenters.IDeckPresenter;


public class DeckFragment extends Fragment implements IDeckFragment {

    private IDeckPresenter deckPresenter;
    private RecyclerView faceUpCardsView;
    private ImageButton deckButton;
    private DeckCardAdapter deckCardAdapter;
    private ArrayList<TrainCard> currentFaceUpCards;


    public DeckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        deckPresenter = new DeckPresenter(this);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deck, container, false);

        //Set up RecyclerView, but don't put anything in it yet.
        faceUpCardsView = v.findViewById(R.id.face_up_deck_list);
        faceUpCardsView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public Player getCurrentPlayer() {
        return ((GameActivity)getActivity()).getCurrentPlayer();
    }

    @Override
    public Game getCurrentGame(){
        return ((GameActivity)getActivity()).getCurrentGame();
    }

    public void onFaceUpCardsUpdated(ArrayList<TrainCard> cards){
        currentFaceUpCards = cards;
        //Replace cards
        deckCardAdapter = new DeckCardAdapter(cards, getContext());
        faceUpCardsView.setAdapter(deckCardAdapter);
    }

    public void cardSelected(TrainCard card){
        deckPresenter.cardSelected(card);
    }


}