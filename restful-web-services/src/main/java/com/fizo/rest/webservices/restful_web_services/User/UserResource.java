package com.fizo.rest.webservices.restful_web_services.User;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    public UserDaoService userDaoService;

    //UserDaoService is injected to UserResource through Constructor injection
    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAll() {
      return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable int id) {
        User user = userDaoService.findUserById(id);
        if (user == null) throw new UserNotFoundException("id: "+ id);

        return user;
    }

    //Creating a new resource and returning a status response and location
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //Creating and saving a new user, with method for saving on userDaoService class
       User savedUser = userDaoService.save(user);

        //Returning Response HTTP Status and location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
      return ResponseEntity.created(location).build();
    }

    //DELETE controller method, deleting user by id
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.deleteUserById(id);
    }
}
