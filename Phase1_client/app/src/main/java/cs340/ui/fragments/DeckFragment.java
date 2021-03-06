package cs340.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.model.TrainCard;
import cs340.ui.R;
import cs340.ui.activities.GameActivity;
import cs340.ui.fragments.adapters.DeckCardAdapter;
import cs340.ui.fragments.interfaces.IDeckFragment;
import cs340.ui.presenters.DeckPresenter;
import cs340.ui.presenters.interfaces.IDeckPresenter;


public class DeckFragment extends Fragment implements IDeckFragment {

    private IDeckPresenter deckPresenter;
    private RecyclerView faceUpCardsView;
    private Button deckButton;
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

        //Deck button to get a random card from the top of the deck
        deckButton = v.findViewById(R.id.face_down_deck);
        updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());

        deckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deckPresenter.drawFromDeck();
            }
        });

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

    @Override
    public void onFaceUpCardUpdated(TrainCard card, int index){
        if (card == null){
            currentFaceUpCards.remove(index);
            deckCardAdapter = new DeckCardAdapter(currentFaceUpCards, getContext());
            faceUpCardsView.setAdapter(deckCardAdapter);
        }
        else {
            System.out.println("Old Card: " + currentFaceUpCards.get(index).getColor() + ", New Card: " + card.getColor() + ".");

            //REPLACE SINGLE CARD IN ARRAY
            currentFaceUpCards.remove(index);
            currentFaceUpCards.add(index, card);

            //Replace cards
            deckCardAdapter = new DeckCardAdapter(currentFaceUpCards, getContext());
            faceUpCardsView.setAdapter(deckCardAdapter);
            updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());
        }
    }

    @Override
    public void initializeFaceUpCards(ArrayList<TrainCard> cards){
        if (cards == null){
            currentFaceUpCards = cards;
            deckCardAdapter = new DeckCardAdapter(new ArrayList<TrainCard>(), getContext());
            faceUpCardsView.setAdapter(deckCardAdapter);
            updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());
        }
        else {
            currentFaceUpCards = cards;
            deckCardAdapter = new DeckCardAdapter(currentFaceUpCards, getContext());
            faceUpCardsView.setAdapter(deckCardAdapter);
            updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());
        }
    }

    //Tell the deck presenter that we've selected a card
    public void cardSelected(int index){
        System.out.println("deckFragment.cardSelected(): card = " + currentFaceUpCards.get(index).getColor());
        deckPresenter.cardSelected(index);
    }

    @Override
    public void updateDeckCount(int count) {
        String cardsInDeck = String.valueOf(count);
        deckButton.setText(cardsInDeck);
    }

    public GameActivity getGameActivity(){
        return (GameActivity)getActivity();
    }
}
