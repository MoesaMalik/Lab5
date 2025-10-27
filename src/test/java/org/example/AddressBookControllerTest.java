package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Test
    void shouldCreateAddressBook() throws Exception {
        // Test creating an empty address book (no parameters needed)
        this.mockMvc.perform(post("/addressbook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.buddies.length()", is(0)));
    }

    @Test
    void shouldGetAllAddressBooks() throws Exception {
        // Create an address book first
        AddressBook ab = new AddressBook();
        addressBookRepo.save(ab);

        // Test getting all address books
        this.mockMvc.perform(get("/addressbook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void shouldAddBuddyToAddressBook() throws Exception {
        // Create an address book
        AddressBook ab = new AddressBook();
        ab = addressBookRepo.save(ab);

        // Add a buddy via REST API
        String buddyJson = "{\"name\":\"Test Buddy\",\"phoneNumber\":\"123-4567\",\"emailAddress\":\"test@example.com\"}";

        this.mockMvc.perform(post("/addressbook/" + ab.getId() + "/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buddies.length()", is(1)))
                .andExpect(jsonPath("$.buddies[0].name", is("Test Buddy")))
                .andExpect(jsonPath("$.buddies[0].phoneNumber", is("123-4567")))
                .andExpect(jsonPath("$.buddies[0].emailAddress", is("test@example.com")));
    }

    @Test
    void shouldRemoveBuddyFromAddressBook() throws Exception {
        // Create an address book with a buddy
        AddressBook ab = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Remove Me", "999-9999", "remove@example.com");
        ab.addBuddy(buddy);
        ab = addressBookRepo.save(ab);

        Integer buddyId = ab.getBuddies().get(0).getId();

        // Remove the buddy
        this.mockMvc.perform(delete("/addressbook/" + ab.getId() + "/buddies/" + buddyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buddies.length()", is(0)));
    }
}