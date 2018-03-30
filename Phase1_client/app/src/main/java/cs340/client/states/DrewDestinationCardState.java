package cs340.client.states;

import cs340.client.requests.DrawFaceupRequest;
import cs340.client.services.ClientFacade;

/**
 * Created by Mark on 3/22/2018.
 */

public class DrewDestinationCardState extends TurnState {
	public TurnState claimRoute(){
		this.fail();
		ClientFacade.getInstance().error("You must pick your Destination Cards!");
		return this;
	}
	public TurnState drawDestination(){
		this.fail();
		ClientFacade.getInstance().error("You must pick your Destination Cards!");
		return this;
	}
	public TurnState discardDestination() {
		return new NotMyTurnState();
	}
	public TurnState drawTrainCard (){
		this.fail();
		ClientFacade.getInstance().error("You must pick your Destination Cards!");
		return this;
	}
	public TurnState drawFaceupCard (DrawFaceupRequest request){
		this.fail();
		ClientFacade.getInstance().error("You must pick your Destination Cards!");
		return this;
	}
}
