package cs340.ui.fragments.interfaces;

import android.util.Pair;

import java.util.Map;

import cs340.client.model.MapRoute;
import cs340.ui.activities.ClaimRouteActivity;

public interface IMapFragment {
    void claimRoute(ClaimRouteActivity.IntentFactory intentFactory);
    void onRouteClaimed(Map<Pair<String, String>, MapRoute> routes);
}
