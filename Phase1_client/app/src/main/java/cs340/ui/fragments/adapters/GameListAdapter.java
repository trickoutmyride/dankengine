package cs340.ui.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cs340.client.model.Game;
import cs340.client.model.Player;
import cs340.ui.R;
import cs340.ui.activities.PreGameActivity;

/**
 * Created by sam on 2/7/18.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private ArrayList<Game> _gameList;
    private Context _context;
    private Player _currentPlayer;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        //public TextView gameNum;
        public TextView gameName;
        public TextView gamePlayers;
        public TextView gameCapacity;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            //gameNum = v.findViewById(R.id.game_number);
            gameName = v.findViewById(R.id.game_name);
            gamePlayers = v.findViewById(R.id.game_players);
            gameCapacity = v.findViewById(R.id.game_capacity);
        }
    }

    public GameListAdapter(ArrayList<Game> gameList, Player player, Context context) {
        _gameList = gameList;
        _context = context;
        _currentPlayer = player;
    }

    //Create new views
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.pregame_game_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        System.out.println("OnBindViewHolder " + position);

        final Game game = _gameList.get(position);
        /*
        boolean joinedGame = false;
        for (Player p : game.getPlayers()){
            if (p.getUsername().equals(_currentPlayer.getUsername())){
                joinedGame = true;
            }
        }
        */
        /*
        if (joinedGame){
            String gameText = game.getGameName() + " (Rejoin)";
            holder.gameName.setText(gameText);
        }

        else{
        */
        holder.gameName.setText(game.getGameName());
        //}

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (i == 0){
                sb.append(game.getPlayers().get(i).getUsername());
            }
            else {
                sb.append(", ");
                sb.append(game.getPlayers().get(i).getUsername());
            }
        }
        holder.gamePlayers.setText(sb.toString());

        sb = new StringBuilder();
        sb.append(game.getPlayers().size());
        sb.append("/");
        sb.append(game.getCapacity());
        holder.gameCapacity.setText(sb.toString());

        //Send join game request when clicked
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PreGameActivity)_context).joinGame(game);
            }

        });
    }

    //Return size of dataset
    @Override
    public int getItemCount() {
        return _gameList.size();
    }


}
