package org.freeman.mockito.service;

import org.freeman.mockito.model.IAccount;

public abstract class LoginServiceState {

	protected String previousAccountId = "";
	
	protected int failedAttempts;

	public final void login(LoginService context,IAccount account, String password){
		if (account.passwordMatches(password)) {
			if (account.isLoggedIn())
				throw new AccountLoginLimitReachedException();
			if (account.isRevoked())
				throw new AccountRevokedException();
			account.setLoggedIn(true);
			return;
		}else{
			loginFail(context,account,password);
		}
	}
	
	public abstract void loginFail(LoginService context,IAccount account, String password);
}
