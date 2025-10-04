package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressBookRepository addressBookRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void addressBookPageShouldContainHeader() {
        // Arrange: create a test AddressBook with one buddy
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("Test Buddy", "123-4567"));
        ab = addressBookRepository.save(ab);

        // Act
        ResponseEntity<String> response =
                restTemplate.getForEntity(getBaseUrl() + "/addressbook/" + ab.getId() + "/view", String.class);

        // Assert: check that the HTML page renders correctly
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Address Book");
        assertThat(response.getBody()).contains("Buddies");
        assertThat(response.getBody()).contains("Test Buddy");
    }
}
