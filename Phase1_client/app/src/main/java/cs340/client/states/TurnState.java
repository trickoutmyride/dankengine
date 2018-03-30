package cs340.client.states;

import cs340.client.requests.DrawFaceupRequest;

public abstract class TurnState {
	private boolean success = true;
	/**
	 * Each method returns the new Turn State (given failure, it will only return itself after calling this.fail() ).
	 */
	public abstract TurnState claimRoute();
	public abstract TurnState drawDestination();
	public abstract TurnState discardDestination();
	public abstract TurnState drawTrainCard ();
	public abstract TurnState drawFaceupCard (DrawFaceupRequest request);
	/**
	 * @returns true if the last call was made successfully
	 */
	public boolean isSuccess() {
		return success;
	}
	protected void fail() {
		success = false;
	}
}
