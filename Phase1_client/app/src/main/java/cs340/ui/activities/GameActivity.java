package cs340.ui.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import cs340.client.services.DeckService;
import cs340.client.model.ClientModel;
import cs340.client.model.DestinationCard;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.client.model.TrainCard;
import cs340.ui.R;
import cs340.ui.activities.interfaces.IGameActivity;
import cs340.ui.fragments.ChatFragment;
import cs340.ui.fragments.DeckFragment;
import cs340.ui.fragments.DestinationCardFragment;
import cs340.ui.fragments.GameMapFragment;
import cs340.ui.fragments.adapters.DestinationCardSelectionAdapter;
import cs340.ui.fragments.HistoryFragment;
import cs340.ui.fragments.interfaces.IHandFragment;
import cs340.ui.fragments.interfaces.IPlayersFragment;
import cs340.ui.presenters.GamePresenter;
import cs340.ui.presenters.interfaces.IGamePresenter;

/**
 * GameActivity
 * This Activity controls the main UI for the actual game.
 *
 * Persistent fragments:
 *      GameMapFragment       displays the current map
 *      PlayersFragment   displays information about the game's players
 *      HandFragment      displays cards in the current player's hand
 *      DeckFragment      displays deck, face up cards that can be drawn on a turn
 *
 * Buttons:
 *      Destination Card Button     displays the current player's destination cards (DestinationCardFragment)
 *      Chat Button                 displays the chat (ChatFragment)
 *      History Button              displays the game history (HistoryFragment)
 *      Test Driver                 runs phase 2 test driver
 *
 */
public class GameActivity extends AppCompatActivity implements IGameActivity, DestinationCardFragment.DestinationCardDialogListener {
    //Data Members
    private IGamePresenter gamePresenter;
    private ImageButton chatButton, historyButton, destinationCardButton;
    private IHandFragment handFragment;
    private IPlayersFragment playersFragment;
    private HistoryFragment historyFragment;
    private GameMapFragment mapFragment;
    private DeckFragment deckFragment;
    private ChatFragment chatFragment;
    private DestinationCardFragment destinationCardFragment;
    private Player currentPlayer;
    private Game currentGame;
    private ArrayList<String> currentHistory;
    private ArrayList<String> currentChat;
    private RelativeLayout reconnectLayout;


    //Initialize Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get current player, game from previous activity, initialize presenter
        Gson gson = new Gson();
        currentPlayer = gson.fromJson(getIntent().getStringExtra("currentPlayer"), Player.class);
        currentGame = gson.fromJson(getIntent().getStringExtra("currentGame"), Game.class);

        boolean gameRejoined = getIntent().getBooleanExtra("rejoinGame", false);

        ClientModel.getInstance().setCurrentPlayer(currentPlayer);
        ClientModel.getInstance().setCurrentGame(currentGame);
        gamePresenter = new GamePresenter(this);
        currentHistory = new ArrayList<>();
        currentChat = new ArrayList<>();
        reconnectLayout = findViewById(R.id.reconnect_layout);
        reconnectLayout.setVisibility(View.GONE);

        //Pop up destination card selection
        if (!gameRejoined) { onDrawnDestinationCards(currentPlayer.getDestinations(), false); }

        //Initialize the rest of the fragments

        //Populate the player's hand
        handFragment = (IHandFragment)getFragmentManager().findFragmentById(R.id.handFragment);
        handFragment.onTrainCardsUpdated(currentPlayer);

        //Populate the other player's stuff
        playersFragment = (IPlayersFragment)getFragmentManager().findFragmentById(R.id.playersFragment);
        playersFragment.initiatePlayers(currentGame.getPlayers());

        //Populate the face up cards
        deckFragment = (DeckFragment)getFragmentManager().findFragmentById(R.id.deckFragment);
        deckFragment.initializeFaceUpCards(currentGame.getTrainFaceup());

        //Get chat, history, destination card buttons
        chatButton = findViewById(R.id.chatButton);
        historyButton = findViewById(R.id.historyButton);
        destinationCardButton = findViewById(R.id.destinationCardButton);

        //Chat button listener - open Chat dialog fragment
        chatButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Pop up Chat dialog
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("chat", currentChat);
                chatFragment = new ChatFragment();
                FragmentManager fm = getFragmentManager();
                chatFragment.setArguments(bundle);
                chatFragment.show(fm, "chatfragment");
            }
        });

        //History button listener - open History dialog fragment
        historyButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Pop up History dialog
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("history", currentHistory);
                historyFragment = new HistoryFragment();
                FragmentManager fm = getFragmentManager();
                historyFragment.setArguments(bundle);
                historyFragment.show(fm, "historyfragment");
            }
        });

        //Destination button listener - open Destination dialog fragment in Display mode
        destinationCardButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pop up Destination Card dialog
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                bundle.putString("player", gson.toJson(currentPlayer));
                bundle.putBoolean("selection", false);
                destinationCardFragment = new DestinationCardFragment();
                FragmentManager fm = getFragmentManager();
                destinationCardFragment.setArguments(bundle);
                destinationCardFragment.show(fm, "destinationfragment");
            }
        });

        mapFragment = (GameMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        ClientModel.getInstance().setCurrentGame(currentGame);
    }



    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Callbacks

    /**
     * onHistoryItemUpdated()
     * Adds new history item to fragment, log
     * @param item history item to be added
     */
    @Override
    public void onHistoryItemUpdated(final String item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentHistory.add(item);
                if (historyFragment != null)
                {
                    historyFragment.updateHistory(item);
                }
            }
        });
    }

    /**
     * onHistoryReplaced()
     * Replace entire history log
     * @param history arraylist of history items
     */
    @Override
    public void onHistoryReplaced(final ArrayList<String> history) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentHistory = history;
                if (historyFragment != null)
                {
                    historyFragment.replaceHistory(history);
                }
            }
        });
    }

    /**
     * onDialogPositiveClick()
     * Destination Card Fragment selection confirmed - send discarded to server
     * @param dialog the dialog fragment whose button was clicked
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DestinationCardFragment dcf = (DestinationCardFragment)dialog;

        DestinationCardSelectionAdapter dcsa = dcf.getDestinationCardSelectionAdapter();
        ArrayList<DestinationCard> unselected = dcsa.getUnselectedCards();
        //If a card was discarded, send it to the server
        DeckService.discardDestination(currentGame.getGameID(), unselected, currentPlayer, dcf.getGameStarted());
        for(DestinationCard card : unselected){
            System.out.println("GameActivity.onDialogPositiveClick: Discard " + card.getStartPoint() + " to " + card.getEndPoint());
        }
    }

    /**
     * onDrawNewDestinationCardsSelected()
     * Called when the "Draw New Destination Cards" button is clicked
     */
    @Override
    public void onDrawNewDestinationCardsSelected() {
        DeckService.drawDestination(currentGame.getGameID(), currentPlayer);
    }

    /**
     * onChatUpdated()
     * New message - add to chat log and display if dialog visible
     * @param message the new message received
     */
    public void onChatUpdated(final String message){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentChat.add(message);
                if (chatFragment != null)
                {
                    chatFragment.onChatUpdated(message);
                }
            }
        });
    }

    /**
     * onDrawnDestinationCards()
     * Pop up a new Destination Card Selection dialog
     * @param cards destination cards that can be selected
     * @param gameStarted tells the dialog whether or not this is the initial selection
     */
    @Override
    public void onDrawnDestinationCards(final ArrayList<DestinationCard> cards, final boolean gameStarted) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                bundle.putString("player", gson.toJson(currentPlayer));
                final Type type = new TypeToken<ArrayList<DestinationCard>>(){}.getType();
                bundle.putString("newCards", gson.toJson(cards, type));
                bundle.putBoolean("gameStarted", gameStarted);
                bundle.putBoolean("selection", true);
                destinationCardFragment = new DestinationCardFragment();
                FragmentManager fm = getFragmentManager();
                destinationCardFragment.setArguments(bundle);
                destinationCardFragment.show(fm, "destinationfragment");
            }
        });


    }

    /**
     * onPlayerCardsUpdated()
     * Called by updateFaceUpDeck in DeckPresenter
     * This will update the players fragment and hand fragment with the new cards and the face up deck with a new card
     * @param index index of card that was selected
     * @param oldCard old card to be replaced in the face up deck
     * @param newCard new card to be added to the face up deck
     * @param player player who drew cards
     * @param faceUpCards total list of face up deck cards
     */
    //called by updateFaceUpDeck in DeckPresenter
    @Override
    public void onPlayerCardsUpdated(final int index, final TrainCard oldCard, final TrainCard newCard, final Player player, final ArrayList<TrainCard> faceUpCards){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentPlayer.getUsername().equals(player.getUsername())){
                    currentPlayer = player;
                    ClientModel.getInstance().setCurrentPlayer(player);
                    handFragment.onTrainCardsUpdated(currentPlayer);
                }
                playersFragment.onPlayerUpdated(player);

                int compareCount = 0;
                //Were the face up cards shuffled?
                for (int i = 0; i < currentGame.getTrainFaceup().size(); i++) {
                    //If the old card at index =/= new card at index for > 1 card
                    if (!currentGame.getTrainFaceup().get(i).getColor().equals(faceUpCards.get(i).getColor())) {
                        compareCount++;
                    }
                }
                ClientModel.getInstance().getCurrentGame().setTrainFaceup(faceUpCards);
                currentGame = ClientModel.getInstance().getCurrentGame();
                deckFragment.initializeFaceUpCards(faceUpCards);
                deckFragment.updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());
            }
        });
    }

    /**
     * onPlayerCardsUpdated()
     * Update a specific player's cards
     * @param player player whose cards changed
     */
    @Override
    public void onPlayerCardsUpdated(final Player player){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentPlayer.getUsername().equals(player.getUsername())){
                    currentPlayer = player;
                    ClientModel.getInstance().setCurrentPlayer(player);
                    handFragment.onTrainCardsUpdated(player);
                }
            }
        });
    }

    /**
     * onPlayerUpdated()
     * Update player info (cards, points, etc)
     * @param player player to update
     */
    @Override
    public void onPlayerUpdated(final Player player){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playersFragment != null) {
                    playersFragment.onPlayerUpdated(player);
                }
            }
        });
    }

    /**
     * onTurnUpdated()
     * Called when turn changed - update the player list, deck counts, etc.
     * @param game current state of the game
     */
    @Override
    public void onTurnUpdated(final Game game){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("onTurnUpdated!");
                currentGame = game;
                ClientModel.getInstance().setCurrentGame(game);
                deckFragment.updateDeckCount(ClientModel.getInstance().getCurrentGame().getTrainDeck().size());
                for (Player p : game.getPlayers()) {
                    onPlayerUpdated(p);
                }
            }
        });
    }

    /**
     * onDestinationCardsUpdated()
     * Player's destination cards were updated - update fragment
     * @param player player whose destination cards were updated
     */
    @Override
    public void onDestinationCardsUpdated(final Player player){
        for (DestinationCard c : player.getDestinations()){
            System.out.println(c.getStartPoint() + " to " + c.getEndPoint());
        }

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if (currentPlayer.getUsername().equals(player.getUsername())){
                    currentPlayer = player;
                    ClientModel.getInstance().setCurrentPlayer(player);
                }
                playersFragment.onPlayerUpdated(player);
            }
        });
    }

    /**
     * onGameEnded()
     * Game is over, go to the game over activity
     */
    @Override
    public void onGameEnded(Game game) {
        //Go to game over activity
        Intent intent = new Intent(this, EndGameActivity.class);
        Gson gson = new Gson();
        //Pass game and current player to the game over activity
        intent.putExtra("currentGame", gson.toJson(game));
        startActivity(intent);
        finish();
    }

    /**
     * onError()
     * Display a toast with an error message
     * @param message error message to be displayed
     */
    @Override
    public void onError(final String message){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    /**
     * toggleReconnectProgress()
     * Change the visibility for the reconnection progress bar
     */
    @Override
    public void toggleReconnectProgress(){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if (reconnectLayout.getVisibility() == View.GONE){
                    reconnectLayout.setVisibility(View.VISIBLE);
                }
                else{
                    reconnectLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Getters and Setters

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Game getCurrentGame() {
        return currentGame;
    }
    public IGamePresenter getGamePresenter() { return gamePresenter; }
    public boolean myTurn(){ return currentGame.getPlayers().get(currentGame.getTurn()).getUsername().equals(currentPlayer.getUsername()); }
    public int getTurnIndex(){
        return currentGame.getTurn();
    }
    public void setCurrentGame(Game game){currentGame = game;}

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}

