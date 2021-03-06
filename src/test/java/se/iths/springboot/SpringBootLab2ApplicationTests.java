package se.iths.springboot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;
import se.iths.springboot.dtos.UserDto;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        UserDto invalidDto = new UserDto(0,null,"Lärk");
        addHeaderAndJson();
        ResponseEntity<UserDto> result = testClient.postForEntity("http://localhost:"+port+"/users/", invalidDto, UserDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    //Test CREATE
    @Test
    void checkIfPostReturns201CreatedAndCreatesUserWithJson(){
        UserDto userDto = new UserDto(4,"Test","Test");
        addHeaderAndJson();
        HttpEntity<UserDto> demand = new HttpEntity<UserDto>(userDto);
        ResponseEntity<UserDto> response = testClient.postForEntity("http://localhost:"+port+"/users", userDto, UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getFirstName()).isEqualTo(userDto.getLastName());
        assertThat(response.getBody().getLastName()).isEqualTo(userDto.getLastName());
    }
    //Test mapping-response functionality and GET all users
    @Test
    void checkIfUrlIsMappingTowardsUsersInDatabaseAndCheckIfGetAllUsersReturnsArrayOfUsers() {
        addHeaderAndJson();
        var result = getResultArray("");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.hasBody());
        assertThat(result.getBody().length).isGreaterThan(0);
        assertThat(result.getBody().length).isEqualTo(3);
    }
    //Test invalid GET by id
    @Test
    void testGetByIdReturns404IfNotFound() {
        addHeaderAndJson();
        var result = getResult("4");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    //Test GET one by id
    @Test
    void checkIfGetByIdReturnsUserAsJson(){
        addHeaderAndJson();
        var result = getResult("1");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getLastName()).isEqualTo("Lärk");
    }
    //Test invalid UPDATE(PATCH)
    @Test
    void checkIfUpdateFailsToChangeNameAndReturnResponse404NotFound(){
        UserDto updateFirstname = new UserDto(4,"Test","Test");
        //Standard JDK cannot invoke HTTP PATCH, implement components
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testClient.getRestTemplate().setRequestFactory(requestFactory);
        addHeaderAndJson();

        HttpEntity<UserDto> request = new HttpEntity<>(updateFirstname);
        ResponseEntity<UserDto> response = testClient.exchange("http://localhost:"+port+"/users/4", HttpMethod.PATCH, request, UserDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //Test UPDATE(PATCH)
    @Test
    void checkIfUpdateChangesNameAndReturnsResponse200AndUserAsJson(){
        UserDto user = new UserDto(1,"TestFirstname","TestLastname");
        //Standard JDK cannot invoke HTTP PATCH, implement components
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testClient.getRestTemplate().setRequestFactory(requestFactory);
        addHeaderAndJson();

        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<UserDto> response = testClient.exchange("http://localhost:"+port+"/users/1",
                HttpMethod.PATCH, request, UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getFirstName()).isEqualTo(user.getFirstName());
        assertThat(response.getBody().getLastName()).isEqualTo("Lärk");
    }
    //Test invalid REPLACE(PUT)
    @Test
    void checkIfReplaceReturns404NotFound(){
        UserDto user = new UserDto(1,"Test","Test");
        addHeaderAndJson();
        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<UserDto> response = testClient.exchange("http://localhost:"+port+"/users/4",
                HttpMethod.PUT, request, UserDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //Test REPLACE(PUT)
    @Test
    void checkIfReplaceReturnsStatus200andReturnsUserAsJson(){
        UserDto user = new UserDto(1,"Test","Test");
        addHeaderAndJson();
        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<UserDto> response = testClient.exchange("http://localhost:"+port+"/users/1",
                HttpMethod.PUT, request, UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getFirstName()).isEqualTo(user.getFirstName());
    }
    //Test SEARCH
    @Test
    void searchByFirstnameShouldReturn200okAndResultInJson(){
        addHeaderAndJson();
        var result = testClient.getForEntity("http://localhost:"+port+"/users/search?firstName=Mar",
                UserDto[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(Arrays.stream(result.getBody()).findFirst().get().getFirstName().contains("Marcus"));
        assertThat(result.getBody().length).isEqualTo(1);
    }
    //Test DELETE
    @Test
    void deleteByIdShouldReturnNoContent204(){
        addHeaderAndJson();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        var result = testClient
                .exchange("http://localhost:"+port+"/users/1"
                ,HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //Methods to use TestRestTemplate, responseEntity and to add HeadersAndJson, cleans up the code
    public ResponseEntity<UserDto> getResult(String id){
        ResponseEntity<UserDto> result = testClient.getForEntity("http://localhost:"+port+"/users/"+id, UserDto.class);
        return result;
    }
    public ResponseEntity<UserDto[]> getResultArray(String id){
        ResponseEntity<UserDto[]> result = testClient.getForEntity("http://localhost:"+port+"/users/"+id, UserDto[].class);
        return result;
    }
    public void addHeaderAndJson(){
        HttpHeaders header = new HttpHeaders();
        header.add("Accept","application.xml");
        header.setContentType(MediaType.APPLICATION_JSON);
    }
}
