package org.freeman.mockito.service;

import java.util.HashMap;
import java.util.Map;

import org.freeman.mockito.dao.IAccountRepository;
import org.freeman.mockito.model.IAccount;

public class LoginService {

	private IAccountRepository repository;

	private Map<String, Integer> loginFailStatisMap = new HashMap<>();

	public LoginService(IAccountRepository repository) {
		this.repository = repository;
	}

	public void login(String accountId, String password) {
		IAccount account = repository.find(accountId);
		if (account.passwordMatches(password)) {
			if (account.isLoginedIn()) {
				throw new AccountLoginLimitReachedException();
			}
			account.setLoginedIn(true);
		}
		else {
			Integer counter = loginFailStatisMap.get(accountId);
			if (counter == null) {
				counter = 0;
			}

			counter++;
			loginFailStatisMap.put(accountId, counter);
			if (counter > 3) {
				account.setRevoked(true);
			}
		}
	}

}
