package com.epilgrim.app.model;

import java.util.HashMap;

public class UserRepository {
    private HashMap<String, User> users = new HashMap<String, User>();

    public void addUser(User user)
    {
        users.put(user.getName(), user);
    }

    public User findUser(String userName) throws UserNotFoundException
    {
        if (users.containsKey(userName)){
            return users.get(userName);
        }

        throw new UserNotFoundException();
    }


}
