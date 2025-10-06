package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuddyInfoTest {

    @Test
    public void testConstructorAndGetters() {
        BuddyInfo b = new BuddyInfo("Moesa", "1234", "moesa@example.com");
        assertEquals("Moesa", b.getName());
        assertEquals("1234", b.getPhoneNumber());
        assertEquals("moesa@example.com", b.getEmailAddress());
    }

    @Test
    public void testSetters() {
        BuddyInfo b = new BuddyInfo();
        b.setName("Ali");
        b.setPhoneNumber("5555");
        b.setEmailAddress("ali@example.com");

        assertEquals("Ali", b.getName());
        assertEquals("5555", b.getPhoneNumber());
        assertEquals("ali@example.com", b.getEmailAddress());
    }

    @Test
    public void testEqualsAndHashCode() {
        BuddyInfo a = new BuddyInfo("X", "1", "x@example.com");
        BuddyInfo b = new BuddyInfo("X", "1", "x@example.com");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
