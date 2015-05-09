package org.freeman.mockito.model;

public interface IAccount {

	void setLoginedIn(boolean logined);
	
	boolean passwordMatches(String candidate);
	
	void setRevoked(boolean revoked);
	
	boolean isLoginedIn();
}
