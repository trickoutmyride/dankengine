package cs340.client.states;

import cs340.client.requests.DrawFaceupRequest;
import cs340.client.services.ClientFacade;

/**
 * Created by Mark on 3/22/2018.
 */

public class NotMyTurnState extends TurnState {

	public TurnState claimRoute(){
		this.fail();
		ClientFacade.getInstance().error("Not Your Turn!");
		return this;
	}
	public TurnState drawDestination(){
		this.fail();
		ClientFacade.getInstance().error("Not Your Turn!");
		return this;
	}
	public TurnState discardDestination() {
		this.fail();
		ClientFacade.getInstance().error("Not Your Turn!");
		System.out.println("NotMyTurnState: discardDestination should not have been called here!");
		return this;
	}
	public TurnState drawTrainCard (){
		this.fail();
		ClientFacade.getInstance().error("Not Your Turn!");
		return this;
	}
	public TurnState drawFaceupCard (DrawFaceupRequest request){
		this.fail();
		ClientFacade.getInstance().error("Not Your Turn!");
		return this;
	}
}
