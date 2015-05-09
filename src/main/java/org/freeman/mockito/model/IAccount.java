package org.freeman.mockito.model;

public interface IAccount {
	
	String getId();

	void setLoggedIn(boolean logined);
	
	boolean passwordMatches(String candidate);
	
	void setRevoked(boolean revoked);
	
	boolean isRevoked();
	
	boolean isLoggedIn();
}
