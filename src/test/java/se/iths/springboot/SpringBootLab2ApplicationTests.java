package se.iths.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import se.iths.springboot.dtos.UserDto;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SpringBootLab2ApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;

    //Test invalid CREATE
    @Test
    void checkIfInvalidPostRequestCreateReturnsError400(){
        UserDto userDto = new UserDto(1,"Marcus","Lärk");

        HttpHeaders header = new HttpHeaders();
        header.add("Accept","application.xml");
        header.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<UserDto> responseEntity = testClient
                .postForEntity("http://localhost:"+port+"/users",
                 userDto, UserDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    //Test CREATE
    @Test
    void checkIfPostReturns201CreatedAndCreatesUserWithJson(){
        UserDto userDto = new UserDto(4,"Test","Test");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application.xml");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDto> demand = new HttpEntity<UserDto>(userDto);
        ResponseEntity<UserDto> response = testClient
                .postForEntity("http://localhost:"+port+"/users",
                 userDto, UserDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(response.getBody().getLastName()).isEqualTo("Test");
    }

    //Test mapping-response functionality and GET all users
    @Test
    void checkIfUrlIsMappingTowardsUsersInDatabaseAndCheckIfGetAllUsersReturnsArrayOfUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application.xml");
        var result = testClient.getForEntity("http://localhost:"+port+"/users/", UserDto[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.hasBody());
        assertThat(result.getBody().length).isGreaterThan(0);
        assertThat(result.getBody().length).isEqualTo(3);
        System.out.println("TEST-Firstname "+ Arrays.stream(result.getBody()).findFirst().get().getFirstName());
        System.out.println("TEST-Lastname "+Arrays.stream(result.getBody()).findFirst().get().getLastName());
    }

    //Test invalid get by id
    @Test
    void testGetByIdReturns404IfNotFound() {
        HttpHeaders header = new HttpHeaders();
        header.add("Accept", "application.xml");
        header.setContentType(MediaType.APPLICATION_JSON);

        var result = testClient.getForEntity("http://localhost:"+port+"/users/4", UserDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //Test get one by id
    @Test
    void checkIfGetByIdReturnsUserAsJson(){
        HttpHeaders header = new HttpHeaders();
        header.add("Accept","application.xml");
        header.setContentType(MediaType.APPLICATION_JSON);

        var result = testClient.getForEntity("http://localhost:"+port+"/users/1", UserDto.class);
        System.out.println("Result: "+result.getBody().getFirstName());
        System.out.println(result.getBody().getLastName());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getLastName()).isEqualTo("Lärk");
    }

}
