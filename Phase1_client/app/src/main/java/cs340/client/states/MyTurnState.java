package cs340.client.states;

import cs340.client.model.ClientModel;
import cs340.client.model.TrainCard;
import cs340.client.requests.DrawFaceupRequest;
import cs340.client.services.ClientFacade;

public class MyTurnState extends TurnState {
	public TurnState claimRoute(){
		return new NotMyTurnState();
	}
	public TurnState drawDestination(){
		return new NotMyTurnState();
	}
	public TurnState discardDestination() {
		this.fail();
		ClientFacade.getInstance().error("Invalid Destination Discard");
		System.out.println("MyTurnState: discardDestination should not have been called here!");
		return this;
	}
	public TurnState drawTrainCard (){
		return new DrewOneCardState();
	}
	public TurnState drawFaceupCard (DrawFaceupRequest request){
		TrainCard drawnCard = ClientModel.getInstance().getCurrentGame().getTrainFaceup().get(request.getIndex());
		System.out.println("MyTurnState.drawFaceupCard: " + drawnCard.getColor());
		if (drawnCard.getColor().equals("wild")) {
			return new NotMyTurnState();
		}
		else {
			return new DrewOneCardState();
		}
	}
}
