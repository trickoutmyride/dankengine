package cs340.client.interfaces;

public interface IClient {
	public void createGame(String request);
	public void joinGame(String request);
	public void login(String request);
	public void register(String request);
	public void startGame(String request);
	public void error(String request);
}