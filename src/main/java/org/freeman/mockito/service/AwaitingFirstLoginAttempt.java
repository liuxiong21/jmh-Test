package org.freeman.mockito.service;

import org.freeman.mockito.model.IAccount;

public class AwaitingFirstLoginAttempt extends LoginServiceState {

	@Override
	public void loginFail(LoginService context, IAccount account,
			String password) {
		context.setState(new AfterFirstFailedLoginAttempt(account.getId()));

	}

}
