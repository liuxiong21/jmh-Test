package org.freeman.mockito.dao;

import org.freeman.mockito.model.IAccount;

public interface IAccountRepository {

	IAccount find(String accountId);
}
