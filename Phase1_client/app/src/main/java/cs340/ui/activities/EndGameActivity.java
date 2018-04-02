package cs340.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;

import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.R;
import cs340.ui.fragments.adapters.EndGameListAdapter;

public class EndGameActivity extends AppCompatActivity {


    private Player currentPlayer;
    private Game currentGame;
    private RecyclerView endGameList;
    private LinearLayoutManager endGameLayoutManager;
    private EndGameListAdapter endGameListAdapter;
    private TextView gameWinner;


    /**
     * On Activity creation, do the following:
     *      Get current player and game from the PreGameActivity
     *      Initialize LobbyPresenter
     *      Setup buttons, playerList RecyclerView, etc.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Gson gson = new Gson();
        currentGame = gson.fromJson(getIntent().getStringExtra("currentGame"), Game.class);

        //Display results
        endGameList = findViewById(R.id.end_game_score_list);
        endGameLayoutManager = new LinearLayoutManager(this);
        endGameList.setLayoutManager(endGameLayoutManager);
        endGameListAdapter = new EndGameListAdapter(this, currentGame.getPlayers(), currentGame);
        endGameList.setAdapter(endGameListAdapter);

        gameWinner = findViewById(R.id.end_game_winner);
        gameWinner.setText(currentGame.getPlayers().get(currentGame.getWinner()).getUsername());

        System.out.println("EndGameActivity()");
        System.out.println("Winner: " + currentGame.getPlayers().get(currentGame.getWinner()).getUsername());
        for (Player p : currentGame.getPlayers()){
            System.out.println(p.getUsername() + ", Points: " + String.valueOf(p.getPoints()));
            System.out.println("rte points: " + String.valueOf(p.getClaimedRoutePoints()) + ", dest points: " + String.valueOf(p.getReachedDestinationPoints())
            + ", unreached dest points: " + String.valueOf(p.getUnreachedDestinationPoints()) + ", longest train points: " + String.valueOf(p.getLongestRoutePoints()));
        }


    }


}
