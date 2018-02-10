package cs340.ui.activities;

import cs340.shared.model.Player;

/**
 * Created by sam on 2/5/18
 */

public interface ILoginActivity {

    public void onLogin(Player currentPlayer);

    public void onError(String msg);
}
