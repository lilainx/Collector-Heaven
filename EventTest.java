package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event event;
    private Date date;
    
//NOTE: these tests might fail if time at which line (2) below is executed
//is different from time that line (1) is executed.  Lines (1) and (2) must
//run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {

        event = new Event("Item added!");   // (1)
        date = Calendar.getInstance().getTime();   // (2)
    }
    
    @Test
    public void testEvent() {
        assertEquals("Item added!", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Item added!", event.toString());
    }
    
    @Test
    public void testEqualsSameObject() {
        assertTrue(event.equals(event));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(event.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(event.equals("Some String"));
    }


    @Test
    public void testEqualsDifferentDescription() {
        Event anotherEvent = new Event("Item added!");
        assertTrue(event.equals(anotherEvent));
    }

    @Test
    public void testHashCodeSameProperties() {
        Event anotherEvent = new Event("Item added!");
        assertEquals(event.hashCode(), anotherEvent.hashCode());
    }

    @Test
    public void testHashCodeDifferentDescription() {
        Event anotherEvent = new Event("Item added!");
        assertEquals(event.hashCode(), anotherEvent.hashCode());
    }

   
    
}
