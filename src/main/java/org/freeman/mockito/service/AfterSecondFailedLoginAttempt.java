package org.freeman.mockito.service;

import org.freeman.mockito.model.IAccount;

public class AfterSecondFailedLoginAttempt extends LoginServiceState {

	public AfterSecondFailedLoginAttempt(String previousAccountId) {
		this.previousAccountId = previousAccountId;
		failedAttempts = 2;
	}

	@Override
	public void loginFail(LoginService context, IAccount account,
			String password) {
		if (previousAccountId.equals(account.getId())) {
			account.setRevoked(true);
			context.setState(new AwaitingFirstLoginAttempt());
		}
		else {
			context.setState(new AfterFirstFailedLoginAttempt(account.getId()));
		}

	}

}
