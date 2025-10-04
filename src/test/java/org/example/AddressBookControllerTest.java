package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAddressBook() throws Exception {
        this.mockMvc.perform(post("/addressbook")
                        .param("name", "Test Buddy")
                        .param("phone", "123-4567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())   // JSON response contains an id
                .andExpect(jsonPath("$.buddies.length()", is(0))); // empty buddies initially
    }
}
