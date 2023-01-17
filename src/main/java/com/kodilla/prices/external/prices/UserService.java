package com.kodilla.prices.external.prices;

import com.kodilla.prices.domain.offer.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserClient userClient;

    public UserService(@Autowired UserClient userClient){
        this.userClient = userClient;
    }

    public void addOrUpdateUser(User user){
        if(user.id()==null){
            storeInDatabase(user);
        }else{
            updateInDatabase(user);
        }
    }

    private void updateInDatabase(User user){
        userClient.addUser(UserDto.fromDomain(user));
    }
    private void storeInDatabase(User user){
        userClient.addUser(UserDto.fromDomain(user));
    }
}
