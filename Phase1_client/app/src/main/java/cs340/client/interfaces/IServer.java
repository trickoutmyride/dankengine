package cs340.client.interfaces;

public interface IServer {
	public void createGame(Object request);
	public void joinGame(Object request);
	public void startGame(Object request);
}