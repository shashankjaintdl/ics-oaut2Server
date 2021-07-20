package com.ics.oauth2server.useraccount.repository;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.BaseRepository;
import com.ics.oauth2server.helper.DatabaseHelper;

import java.util.List;

public interface UserAccountRepository extends BaseRepository<UserAccount> {
    List<UserAccount> get(Long userAccountId, String username, String emailId, Boolean isFlag,DatabaseHelper databaseHelper);
}
