package se.iths.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springboot.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {

    //Unit tests. Checks the methods individually in isolation.

    @Test
    void callingOneWithValidIdReturnsOneUser(){
        UserController userController = new UserController(new TestService());

        var user = userController.one(1);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("Test");
        assertThat(user.getLastName()).isEqualTo("Test");


    }

    @Test
    void callingOneWithInvalidIdThrows404Exception(){
        UserController userController = new UserController(new TestService());
        //Testing if the "one" method in UserController succeeds to fail (expecting404Error).
        var exception = assertThrows(ResponseStatusException.class, () -> userController.one(2));

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
