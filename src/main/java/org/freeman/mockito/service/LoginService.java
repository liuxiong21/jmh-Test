package org.freeman.mockito.service;

import org.freeman.mockito.dao.IAccountRepository;
import org.freeman.mockito.model.IAccount;

public class LoginService {

	private IAccountRepository repository;
	
	private LoginServiceState state = new AwaitingFirstLoginAttempt();

	public LoginService(IAccountRepository repository) {
		this.repository = repository;
	}

	public void login(String accountId, String password) {
		IAccount account = repository.find(accountId);
		if(account==null){
			throw new AccountNotFoundException();
		}
		state.login(this,account, password);
	}

	public void setState(LoginServiceState state){
		this.state = state;
	}
}
