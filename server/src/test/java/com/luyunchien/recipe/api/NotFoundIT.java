package com.luyunchien.recipe.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotFoundIT {

    @LocalServerPort
    int port;

    @Test
    public void testNotFoundErrorMessage() throws Exception {
        // Make a request to a URL we know that does not exist
        var url = String.format("http://localhost:%d/does-not-exist", port);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(404);
        // Check the response body is in the RFC 7807 problem format
        var problem = ProblemParser.convertString(response.body());
        assertThat(problem.getStatus()).isEqualTo(404);
        assertThat(problem.getTitle()).isEqualTo("Not Found");
        assertThat(problem.getDetail()).isEqualTo("No endpoint GET /does-not-exist.");
    }
}
