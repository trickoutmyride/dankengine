package cs340.client.states;

import cs340.client.model.ClientModel;
import cs340.client.model.TrainCard;
import cs340.client.requests.DrawFaceupRequest;
import cs340.client.services.ClientFacade;

public class DrewOneCardState extends TurnState {
	public TurnState claimRoute(){
		this.fail();
		ClientFacade.getInstance().error("You can only draw another card!");
		return this;
	}
	public TurnState drawDestination(){
		this.fail();
		ClientFacade.getInstance().error("You can only draw another card!");
		return this;
	}
	public TurnState discardDestination() {
		this.fail();
		ClientFacade.getInstance().error("You can only draw another card!");
		System.out.println("DrewOneCardState: discardDestination should not have been called here!");
		return this;
	}
	public TurnState drawTrainCard () {
		return new NotMyTurnState();
	}
	public TurnState drawFaceupCard (DrawFaceupRequest request){
		TrainCard drawnCard = ClientModel.getInstance().getCurrentGame().getTrainFaceup().get(request.getIndex());
		System.out.println("DrewOneCardState.drawFaceupCard: " + drawnCard.getColor());
		if (drawnCard.getColor().equals("wild")) {
			this.fail();
			ClientFacade.getInstance().error("You can only draw a non-wild card!");
			return this;
		}
		else {
			return new NotMyTurnState();
		}
	}
}