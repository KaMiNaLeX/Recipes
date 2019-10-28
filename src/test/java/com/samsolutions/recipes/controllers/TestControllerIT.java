package com.samsolutions.recipes.controllers;

import com.samsolutions.recipes.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author dzmitry.marudau
 * @since 2019.10
 */
public class TestControllerIT extends BaseTest {
    private static final String HTTP_BASE = "http://localhost";

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL(HTTP_BASE + ":" + port + "/api/");
    }

    @Test
    public void hello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("hello"));
    }

    @Test
    public void bye() throws Exception {
        ResponseEntity<String> response = template.getForEntity(withSegment("bye"), String.class);
        assertThat(response.getBody(), equalTo("Bye!"));
    }

    private String withSegment(String path)
            throws URISyntaxException, MalformedURLException {
        URI uri = base.toURI();
        return uri.resolve(uri.getPath() + '/' + path).toURL().toString();
    }
}
