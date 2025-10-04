package org.example;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class AddressBookTest {
    @Test
    public void testAddAndSize() {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("A", "1"));
        assertEquals(1, ab.size());
    }

    @Test
    public void testRemove() {
        AddressBook ab = new AddressBook();
        BuddyInfo b = new BuddyInfo("B", "2");
        ab.addBuddy(b);
        assertTrue(ab.removeBuddy(b));
        assertEquals(0, ab.size());
    }

    @Test
    public void testGetBuddiesReturnsCopy() {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("C", "3"));
        List<BuddyInfo> list = ab.getBuddies();
        list.add(new BuddyInfo("Hacker", "000"));
        // original address book should not be affected
        assertEquals(1, ab.size());
    }
}
