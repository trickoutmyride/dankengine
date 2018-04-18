package cs340.ui.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import cs340.client.model.ClientModel;
import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.R;
import cs340.ui.activities.interfaces.IPreGameActivity;
import cs340.ui.fragments.CreateGameDialogFragment;
import cs340.ui.fragments.JoinGameDialogFragment;
import cs340.ui.fragments.adapters.GameListAdapter;
import cs340.ui.presenters.interfaces.IPregamePresenter;
import cs340.ui.presenters.PregamePresenter;

public class PreGameActivity extends AppCompatActivity implements CreateGameDialogFragment.CreateGameDialogListener, IPreGameActivity, JoinGameDialogFragment.JoinGameDialogListener {

    private RecyclerView gameList;
    private RecyclerView.Adapter gameListAdapter;
    private RecyclerView.LayoutManager gameListLayoutManager;
    private Button createGameButton;
    private int newGameCapacity;
    private IPregamePresenter preGamePresenter;
    private Player currentPlayer;
    private Game joinGame;
    private ArrayList<Game> currentGameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        //Initialize gameList view
        gameList = findViewById(R.id.games_list);
        gameList.setHasFixedSize(true);
        gameListLayoutManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(gameListLayoutManager);

        //Get current player from LoginActivity
        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        currentPlayer = gson.fromJson(bundle.getString("currentPlayer"), Player.class);

        //Default capacity is 2
        newGameCapacity = 2;

        createGameButton = findViewById(R.id.createGameButton);

        //OnClickListener for Create Game button
        createGameButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                CreateGameDialogFragment createGameFragment = new CreateGameDialogFragment();
                FragmentManager fm = getFragmentManager();
                createGameFragment.show(fm, "creategame");
            }
        });

        //Initialize preGamePresenter
        preGamePresenter = new PregamePresenter(this);
        //Initialize Game List
        onGameListUpdated(ClientModel.getInstance().getGameList());
    }

    public void joinGame(Game game) {
        joinGame = game;
        //Send join game request to Presenter
        JoinGameDialogFragment joinGameFragment = new JoinGameDialogFragment();
        FragmentManager fm = getFragmentManager();
        joinGameFragment.show(fm, "joingame");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        //If called by CreateGameDialogFragment
        if (dialog.getClass() == CreateGameDialogFragment.class) {

            if (currentPlayer == null) {
                System.out.println("currentPlayer == null");
            }

            preGamePresenter.createGame(((CreateGameDialogFragment)dialog).getNewGameName(),
                    currentPlayer,
                    ((CreateGameDialogFragment) dialog).getNewGameCapacity(),
                    ((CreateGameDialogFragment) dialog).getNewGamePlayerColor());
        }
        //If called by JoinGameDialogFragment
        else if (dialog.getClass() == JoinGameDialogFragment.class) {
            if (joinGame.getCapacity() == joinGame.getPlayers().size()){
                onError("Error- Game full");
            }
            else {
                preGamePresenter.joinGame(joinGame.getGameID(), currentPlayer, ((JoinGameDialogFragment) dialog).getPlayerColor());
            }
        }
    }

    public void onError(String message) {
        //toast it up
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onGameListUpdated(ArrayList<Game> games) {

        //Rejoin game in progress
        for (Game g : games){
            for (Player p : g.getPlayers()){
                if (p.getUsername().equals(currentPlayer.getUsername())){
                    preGamePresenter.rejoinGame(currentPlayer);
                }
            }
        }

        System.out.println("PreGameActivity.OnGameListUpdated()");

        if (games != null && games.size() != 0){
            gameListAdapter = new GameListAdapter(games, currentPlayer, this);
            gameList.setAdapter(gameListAdapter);
        }
        else {
            currentGameList = null;
        }

    }

    @Override
    public void onGameJoined(Game game) {
        //Go to lobby activity
        Intent intent = new Intent(this, LobbyActivity.class);
        Gson gson = new Gson();
        //Pass game and current player to the lobby activity
        intent.putExtra("currentGame", gson.toJson(game));
        intent.putExtra("currentPlayer", gson.toJson(currentPlayer));
        startActivity(intent);
        preGamePresenter.detach();
        finish();
    }

    public Game getJoinGame(){
        return joinGame;
    }

    @Override
    public void onGameRejoined(Game game){
        //Go straight to game activity
        Intent intent = new Intent(this, GameActivity.class);
        Gson gson = new Gson();
        System.out.println("PreGameActivity.onGameRejoined()");

        //update player
        for (Player p : game.getPlayers()){
            if (p.getUsername().equals(currentPlayer.getUsername())){
                currentPlayer = p;
            }
        }

        //Pass game and current player to the lobby activity
        intent.putExtra("currentGame", gson.toJson(game));
        intent.putExtra("currentPlayer", gson.toJson(currentPlayer));
        startActivity(intent);
        preGamePresenter.detach();
        finish();
    }

}
