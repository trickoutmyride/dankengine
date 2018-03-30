package cs340.ui.activities.interfaces;

import cs340.client.model.Player;

/**
 * Created by sam on 2/5/18
 */

public interface ILoginActivity {

    public void onLogin(Player currentPlayer);

    public void onError(String msg);
}
