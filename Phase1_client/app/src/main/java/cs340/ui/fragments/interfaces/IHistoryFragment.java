package cs340.ui.fragments.interfaces;

import java.util.ArrayList;

/**
 * Created by sam on 3/7/18.
 */

public interface IHistoryFragment {

    void updateHistory(String historyItem);
    void replaceHistory(ArrayList<String> history);
}
