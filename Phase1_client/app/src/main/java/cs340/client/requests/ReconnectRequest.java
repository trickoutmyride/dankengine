package cs340.client.requests;

/**
 * Created by Mark on 4/18/2018.
 */

public class ReconnectRequest {
	private String auth;

	public ReconnectRequest(String auth) {
		this.auth = auth;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}