package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressBookPersistenceTest {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void testSaveAndLoadAddressBook() {
        // Create buddies
        BuddyInfo b1 = new BuddyInfo("Moesa", "123-4567");
        BuddyInfo b2 = new BuddyInfo("Malik", "987-6543");

        // Create address book and add buddies
        AddressBook ab = new AddressBook();
        ab.addBuddy(b1);
        ab.addBuddy(b2);

        // Save
        AddressBook saved = addressBookRepository.save(ab);

        // Verify ID assigned
        assertThat(saved.getId()).isNotNull();

        // Fetch back from repository
        Optional<AddressBook> fetchedOpt = addressBookRepository.findById(saved.getId());
        assertThat(fetchedOpt).isPresent();

        AddressBook fetched = fetchedOpt.get();
        assertThat(fetched.getBuddies()).hasSize(2);
        assertThat(fetched.getBuddies())
                .extracting(BuddyInfo::getName)
                .containsExactlyInAnyOrder("Moesa", "Malik");
    }

    @Test
    public void testFindAllAddressBooks() {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("Moesa", "555-5555"));
        addressBookRepository.save(ab);

        List<AddressBook> allBooks = (List<AddressBook>) addressBookRepository.findAll();
        assertThat(allBooks).isNotEmpty();
    }
}
