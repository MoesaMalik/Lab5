package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BuddyInfoPersistenceTest {

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @Test
    public void testSaveAndLoadBuddy() {
        BuddyInfo buddy = new BuddyInfo("Diana", "111-2222", "diana@example.com");
        BuddyInfo saved = buddyInfoRepository.save(buddy);

        assertThat(saved.getId()).isNotNull();

        Optional<BuddyInfo> fetchedOpt = buddyInfoRepository.findById(saved.getId());
        assertThat(fetchedOpt).isPresent();

        BuddyInfo fetched = fetchedOpt.get();
        assertThat(fetched.getName()).isEqualTo("Diana");
        assertThat(fetched.getPhoneNumber()).isEqualTo("111-2222");
        assertThat(fetched.getEmailAddress()).isEqualTo("diana@example.com");
    }

    @Test
    public void testUpdateBuddy() {
        BuddyInfo buddy = new BuddyInfo("Eve", "999-8888", "eve@oldmail.com");
        buddy = buddyInfoRepository.save(buddy);

        buddy.setPhoneNumber("000-1111");
        buddy.setEmailAddress("eve@newmail.com");
        buddyInfoRepository.save(buddy);

        BuddyInfo updated = buddyInfoRepository.findById(buddy.getId()).orElseThrow();
        assertThat(updated.getPhoneNumber()).isEqualTo("000-1111");
        assertThat(updated.getEmailAddress()).isEqualTo("eve@newmail.com");
    }
}
