package org.freeman.mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.freeman.mockito.dao.IAccountRepository;
import org.freeman.mockito.model.IAccount;
import org.freeman.mockito.service.AccountLoginLimitReachedException;
import org.freeman.mockito.service.LoginService;
import org.junit.Before;
import org.junit.Test;

public class LoginServiceTest {

	IAccount account;

	IAccountRepository repository;

	LoginService service;

	@Before
	public void init() {
		account = mock(IAccount.class);
		repository = mock(IAccountRepository.class);
		when(repository.find(anyString())).thenReturn(account);

		service = new LoginService(repository);
	}

	public void shouldPasswordMatches(boolean val) {
		when(account.passwordMatches(anyString())).thenReturn(val);
	}

	@Test
	public void testLogin() {
		shouldPasswordMatches(true);
		service.login("testa", "123456");
		verify(account).setLoginedIn(true);
	}

	@Test
	public void testRepeatLoginFailRevokeAccount() {
		shouldPasswordMatches(false);

		for (int i = 0; i < 4; i++) {
			service.login("a1111", "p" + i);
		}
		verify(account, times(1)).setRevoked(true);
	}

	@Test
	public void testRepeatLoginUseDifferenceAccount() {
		shouldPasswordMatches(false);
		IAccount secondAccount = mock(IAccount.class);
		when(secondAccount.passwordMatches(anyString())).thenReturn(false);
		when(repository.find("schuchert")).thenReturn(secondAccount);
		service.login("freeman", "password");
		service.login("freeman", "password");
		service.login("andy", "password");
		verify(account,times(0)).setRevoked(true);;
		verify(secondAccount,times(0)).setRevoked(true);;
	}
	
	@Test(expected=AccountLoginLimitReachedException.class)
	public void testRepeatLoginedIn(){
		shouldPasswordMatches(true);
		when(account.isLoginedIn()).thenReturn(true);
		service.login("freeman", "freeman");
	}

}
