package se.iths.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import se.iths.springboot.dtos.UserDto;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SpringBootLab2ApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;

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

    @Test
    void checkIfUrlIsMappingTowardsUsersInControllerAndDatabase() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application.xml");
        var result = testClient.getForEntity("http://localhost:"+port+"/users/", UserDto[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
