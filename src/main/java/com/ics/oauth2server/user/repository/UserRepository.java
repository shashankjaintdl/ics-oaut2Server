package com.ics.oauth2server.user.repository;


import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.helper.BaseRepository;
import com.ics.oauth2server.helper.DatabaseHelper;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {

    List<User> get(Long id, String username, String phoneNo, Boolean isFlag,DatabaseHelper databaseHelper);

    String exist(User entity);

}
