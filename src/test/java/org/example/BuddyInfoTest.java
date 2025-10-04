package org.example;
import org.junit.Test;
import static org.junit.Assert.*;

public class BuddyInfoTest {
    @Test
    public void testConstructorAndGetters() {
        BuddyInfo b = new BuddyInfo("Moesa", "1234");
        assertEquals("Moesa", b.getName());
        assertEquals("1234", b.getPhoneNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        BuddyInfo a = new BuddyInfo("X", "1");
        BuddyInfo b = new BuddyInfo("X", "1");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

}
