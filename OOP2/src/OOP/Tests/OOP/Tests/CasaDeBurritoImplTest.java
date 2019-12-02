package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CasaDeBurritoImplTest {
    private CasaDeBurritoImpl casa1;
    private CasaDeBurritoImpl casa2;
    private Set<String> menu1;
    private Set<String> menu2;
    private ProfesorImpl prof1;
    private ProfesorImpl prof2;
    private ProfesorImpl prof3;


    @Before
    public void setUp() throws Exception {
        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        menu2 = new HashSet<>(Arrays.asList(menu2_array));

        casa1 = new CasaDeBurritoImpl(1, "Italian Casa", 40, menu1);
        casa2 = new CasaDeBurritoImpl(2, "American Casa", 30, menu2);

        prof1 = new ProfesorImpl(1, "Wamuu");
        prof2 = new ProfesorImpl(2, "Esidisi");
        prof3 = new ProfesorImpl(3, "Kars");
    }

    @Test
    public void getId() {
        assertEquals(casa1.getId(), 1);
        assertEquals(casa2.getId(), 2);
    }

    @Test
    public void getName() {
        assertEquals(casa1.getName(),"Italian Casa");
        assertEquals(casa2.getName(), "American Casa");
    }

    @Test
    public void distance() {
        assertEquals(casa1.distance(), 40);
        assertEquals(casa2.distance(), 30);
    }

    @Test
    public void isRatedBy() {
        assertFalse(casa1.isRatedBy(prof1));
        assertFalse(casa1.isRatedBy(prof2));
        assertFalse(casa1.isRatedBy(prof3));

        try {
            casa1.rate(prof1, 0);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertTrue(casa1.isRatedBy(prof1));
        assertFalse(casa1.isRatedBy(prof2));
        assertFalse(casa2.isRatedBy(prof1));

        try {
            casa1.rate(prof1, 1);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertTrue(casa1.isRatedBy(prof1));
        assertFalse(casa1.isRatedBy(prof2));
        assertFalse(casa2.isRatedBy(prof1));

        try { // even after failure, previous ratings should remain untouched
            casa1.rate(prof1, 6);
            fail();
        } catch (CasaDeBurrito.RateRangeException ignored) {}

        assertTrue(casa1.isRatedBy(prof1));
        assertFalse(casa1.isRatedBy(prof2));
        assertFalse(casa2.isRatedBy(prof1));

        // isRatedBy compares by id, not by reference
        ProfesorImpl prof4 = new ProfesorImpl(1, "Santana");
        assertTrue(casa1.isRatedBy(prof4));

        try {
            casa1.rate(prof4, 3);
        } catch (CasaDeBurrito.RateRangeException e) {fail();}

        assertTrue(casa1.isRatedBy(prof4));
        assertTrue(casa1.isRatedBy(prof1));
        assertFalse(casa1.isRatedBy(prof2));
        assertFalse(casa2.isRatedBy(prof1));
    }

    @Test
    public void rate() {
        try {
            casa1.rate(prof1, 0)
                    .rate(prof2, 1)
                    .rate(prof3, 2)
                    .rate(prof1, 3)
                    .rate(prof2, 4)
                    .rate(prof3, 5);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        try { // no rating lower than 0
            casa1.rate(prof1, -1);
            fail();
        } catch (CasaDeBurrito.RateRangeException ignored) {}

        try { // no rating higher than 5
            casa1.rate(prof1, 6);
            fail();
        } catch (CasaDeBurrito.RateRangeException ignored) {}
    }

    @Test
    public void numberOfRates() {
        assertEquals(casa1.numberOfRates(), 0);
        assertEquals(casa2.numberOfRates(), 0);

        try {
            casa1.rate(prof1, 0);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 1);

        try {
            casa1.rate(prof1, 3);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 1);

        try {
            casa1.rate(prof1, 6);
            fail();
        } catch (CasaDeBurrito.RateRangeException ignored) {}

        assertEquals(casa1.numberOfRates(), 1);

        try {
            casa1.rate(prof2, 3);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 2);

        try {
            casa1.rate(prof3, 3);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 3);

        // isRatedBy compares by id, not by reference
        ProfesorImpl prof4 = new ProfesorImpl(1, "Santana");

        try {
            casa1.rate(prof4, 3);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 3);

        try {
            casa1.rate(prof4, 4);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 3);

        try {
            casa1.rate(prof1, 2);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 3);

        // same name but different id should count as separate rating
        ProfesorImpl prof5 = new ProfesorImpl(5, "Wamuu");
        try {
            casa1.rate(prof5, 3);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.numberOfRates(), 4);
    }

    @Test
    public void averageRating() {
        assertEquals(casa1.averageRating(), 0, 0);
        assertEquals(casa2.averageRating(), 0, 0);

        try {
            casa1.rate(prof1, 00); // 00 just to avoid jetBrains shouting at duplicate code
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 0, 0);

        try {
            casa1.rate(prof2, 0);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 0, 0);

        try {
            casa1.rate(prof3, 0);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 0, 0);

        try {
            casa1.rate(prof1, 1);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 1.0/3, 0);

        try {
            casa1.rate(prof2, 1);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 2.0/3, 0);

        try {
            casa1.rate(prof1, 2);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 3.0/3, 0);

        try {
            casa1.rate(prof3, 5);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 8.0/3, 0);

        try {
            casa1.rate(prof3, 6);
            fail();
        } catch (CasaDeBurrito.RateRangeException ignored) {}

        assertEquals(casa1.averageRating(), 8.0/3, 0);

        try {
            casa1.rate(prof3, 4);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 7.0/3, 0);

        ProfesorImpl prof5 = new ProfesorImpl(5, "Wamuu");

        try {
            casa1.rate(prof5, 0);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 7.0/4, 0);

        try {
            casa1.rate(prof5, 5);
        } catch (CasaDeBurrito.RateRangeException e) { fail(); }

        assertEquals(casa1.averageRating(), 12.0/4, 0);
    }

    @Test
    public void equals() {
        assertEquals(casa1, casa1);
        assertEquals(casa2, casa2);
        assertNotEquals(casa1, casa2);
        assertNotEquals(casa2, casa1);

        // Same id but everything else different
        CasaDeBurritoImpl casa3;
        casa3 = new CasaDeBurritoImpl(1, "Not The same name", 60, menu2);
        assertEquals(casa1, casa3);
        assertEquals(casa3, casa1);

        // Different id but everything else the same
        casa3 = new CasaDeBurritoImpl(3, "Italian Casa", 40, menu1);
        assertNotEquals(casa1, casa3);
        assertNotEquals(casa3, casa1);
    }

    @Test
    public void toStringTest() {
        assertEquals(casa1.toString(), "CasaDeBurrito: Italian Casa.\nId: 1.\nDistance: 40.\nMenu: Grappa, Pasta, Pizza.\n");
        assertEquals(casa2.toString(), "CasaDeBurrito: American Casa.\nId: 2.\nDistance: 30.\nMenu: Cheeseburger, Coca-Cola, Fries.\n");

        // changes to menu not rendered to casa menu
        menu1.add("Calzone");
        assertEquals(casa1.toString(), "CasaDeBurrito: Italian Casa.\nId: 1.\nDistance: 40.\nMenu: Grappa, Pasta, Pizza.\n");

        menu1.remove("Pasta");
        assertEquals(casa1.toString(), "CasaDeBurrito: Italian Casa.\nId: 1.\nDistance: 40.\nMenu: Grappa, Pasta, Pizza.\n");
    }

    @Test
    public void compareTo() {
        assertTrue(casa1.compareTo(casa2) < 0);
        assertTrue(casa2.compareTo(casa1) > 0);
        assertEquals(casa1.compareTo(casa1), 0);
        assertEquals(casa2.compareTo(casa2), 0);

        // Same id but everything else different
        CasaDeBurritoImpl casa3;
        casa3 = new CasaDeBurritoImpl(1, "Not The same name", 60, menu2);
        assertEquals(casa1.compareTo(casa3), 0);
        assertEquals(casa3.compareTo(casa1), 0);

        casa3 = new CasaDeBurritoImpl(3, "Italian Casa", 40, menu1);
        assertTrue(casa1.compareTo(casa3) < 0);
        assertTrue(casa3.compareTo(casa1) > 0);
    }

    @Test
    public void hashCodeImplemented() {
        assertNotEquals(casa1.hashCode(), casa2.hashCode());
        assertEquals(casa1.hashCode(), casa1.hashCode());
        CasaDeBurritoImpl casa4 = new CasaDeBurritoImpl(1, "name", 434, menu2);
        assertEquals(casa1.hashCode(), casa4.hashCode()); // hash code equals iff casas equals
    }
}