package br.com.joao.controller;

import br.com.joao.entity.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/users")
public class UserController {

    @POST
    @Transactional
    public void createUser(){
        var user = new UserEntity();
        user.username = "joao";
        user.email = "joao@gmail.com";
        user.password = "123";

        user.persist();
    }

    @GET
    public List<UserEntity> getUsers(){
        return UserEntity.listAll();
    }
}
