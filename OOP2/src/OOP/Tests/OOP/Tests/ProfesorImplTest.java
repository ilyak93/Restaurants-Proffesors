package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ProfesorImplTest {
    private CasaDeBurritoImpl casa1;
    private CasaDeBurritoImpl casa2;
    private CasaDeBurritoImpl casa3;
    private CasaDeBurritoImpl casa4;
    private CasaDeBurritoImpl casa5;
    private CasaDeBurritoImpl casa6;
    private Set<String> menu1;
    private Set<String> menu2;
    private ProfesorImpl prof1;
    private ProfesorImpl prof2;
    private ProfesorImpl prof3;

    @Before
    public void setUp() throws Exception {
        prof1 = new ProfesorImpl(1, "Wamuu");
        prof2 = new ProfesorImpl(2, "Esidisi");
        prof3 = new ProfesorImpl(3, "Kars");

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        menu2 = new HashSet<>(Arrays.asList(menu2_array));

        casa1 = new CasaDeBurritoImpl(1, "Italian Casa", 40, menu1);
        casa2 = new CasaDeBurritoImpl(2, "American Casa", 30, menu2);
        casa3 = new CasaDeBurritoImpl(3, "Casa 3", 60, menu2);
        casa4 = new CasaDeBurritoImpl(4, "Casa 4", 60, menu2);
        casa5 = new CasaDeBurritoImpl(5, "Casa 5", 50, menu2);
        casa6 = new CasaDeBurritoImpl(6, "Casa 6", 50, menu2);
    }

    @Test
    public void getId() {
        assertEquals(prof1.getId(), 1);
        assertEquals(prof2.getId(), 2);
        assertEquals(prof3.getId(), 3);
    }

    @Test
    public void favorite() {
        try {
            prof1.favorite(casa1);
            fail();
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException ignored) {}

        try {
            casa1.rate(prof1, 3);
        } catch (CasaDeBurrito.RateRangeException ignored) {fail();}

        try {
            prof1.favorite(casa1).favorite(casa1).favorite(casa1);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {fail();}

        try {
            casa1.rate(prof1, 0);
        } catch (CasaDeBurrito.RateRangeException ignored) {fail();}

        try {
            prof1.favorite(casa1).favorite(casa1).favorite(casa1);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {fail();}

        // CHANGED 25.04: according to instructor this should throw an exception now
        CasaDeBurritoImpl casa3 = new CasaDeBurritoImpl(1, "Different", 50, menu2);

        try {
            prof1.favorite(casa3);
            fail();
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException ignored) {}

        // profesors are compared by id //
        // so if profesor with id 2 rated casa X, every other profesor with id 2 can favorite it
        Profesor prof4 = new ProfesorImpl(2, "Santana");
        try {
            casa2.rate(prof4, 3);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }
        try {
            prof2.favorite(casa2);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) { fail(); }
    }

    @Test
    public void favorites() {
        assertTrue(prof1.favorites().isEmpty());

        try {
            casa1.rate(prof1, 3);
            casa2.rate(prof1, 4);
        } catch (CasaDeBurrito.RateRangeException ignored) {fail();}

        try {
            prof1.favorite(casa1);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {fail();}
        Collection<CasaDeBurrito> favorites = prof1.favorites();

        assertTrue(favorites.contains(casa1));
        assertFalse(favorites.contains(casa2));

        // check that changing favorites from outside does not affect actual favorites
        favorites.remove(casa1);
        favorites = prof1.favorites();

        assertTrue(favorites.contains(casa1));
        assertFalse(favorites.contains(casa2));

        try {
            prof1.favorite(casa2);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {fail();}

        favorites = prof1.favorites();

        assertTrue(favorites.contains(casa1));
        assertTrue(favorites.contains(casa2));

        // test that this is a shallow copy - casas should be the same references

        try {
            prof3.favorite(casa1);
            fail(); // prof 3 can't favorite since he has not rated
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {}

        favorites.forEach(x -> {
            try {
                x.rate(prof3, 5);
            } catch (CasaDeBurrito.RateRangeException e) {fail();}
        });

        try {
            prof3.favorite(casa1);
            prof3.favorite(casa2);
            // now prof3 can favorite
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {fail();}

    }

    @Test
    public void addFriend() {
        try {
            prof1.addFriend(prof1);
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.SameProfesorException e) {}

        assertTrue(prof1.getFriends().isEmpty());

        try {
            prof1.addFriend(prof2);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {fail();
        }

        try {
            prof1.addFriend(prof2);
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException e) {
            // ok
        } catch (Profesor.SameProfesorException e) {
            fail();
        }

        try {
            prof2.addFriend(prof1);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        Profesor prof4 = new ProfesorImpl(1, "Santana");
        // friend check should be done by id.
        try {
            prof1.addFriend(prof4);
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.SameProfesorException e) {

        }

        prof4 = new ProfesorImpl(2, "Santana");
        assertEquals(prof2, prof4);
        assertTrue(prof1.getFriends().contains(prof4));
        // friend check should be done by id.
        try {
            prof1.addFriend(prof4);
            fail(); // if this fails you might need to implement hashCode
        } catch (Profesor.ConnectionAlreadyExistsException e) {

        } catch (Profesor.SameProfesorException e) {
            fail();
        }
    }

    @Test
    public void getFriends() {
        assertTrue(prof1.getFriends().isEmpty());

        Collection<Profesor> friends = prof1.getFriends();
        friends.add(prof2); // should not affect actual friends of prof1
        assertTrue(prof1.getFriends().isEmpty());

        try {
            prof1.addFriend(prof2);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        friends = prof1.getFriends();
        assertTrue(friends.contains(prof2));
        assertFalse(friends.contains(prof1));
        assertFalse(friends.contains(prof3));
        assertEquals(friends.size(), 1);

        try {
            prof3.addFriend(prof1);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }
        // adding prof1 as friend of prof3 should not affect prof1.friends
        friends = prof1.getFriends();
        assertTrue(friends.contains(prof2));
        assertFalse(friends.contains(prof1));
        assertFalse(friends.contains(prof3));
        assertEquals(friends.size(), 1);

        try {
            prof1.addFriend(prof3);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        friends = prof1.getFriends();
        assertTrue(friends.contains(prof2));
        assertFalse(friends.contains(prof1));
        assertTrue(friends.contains(prof3));
        assertEquals(friends.size(), 2);

        try {
            prof1.addFriend(prof1);
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {}

        friends = prof1.getFriends();
        assertTrue(friends.contains(prof2));
        assertFalse(friends.contains(prof1));
        assertTrue(friends.contains(prof3));
        assertEquals(friends.size(), 2);

        Profesor prof4 = new ProfesorImpl(4, "Santana");

        // test shallow copy - changing friends.profX should change actual profX
        friends.forEach(x -> {
            try {
                x.addFriend(prof4);
            } catch (Profesor.SameProfesorException | Profesor.ConnectionAlreadyExistsException e) {
                fail();
            }
        });

        friends = prof2.getFriends();
        assertTrue(friends.contains(prof4));
        assertEquals(friends.size(), 1);

    }


    @Test
    public void filteredFriends() {
        assertTrue(prof1.filteredFriends(x -> true).isEmpty());
        try {
            prof1.addFriend(prof2);
            prof1.addFriend(prof3);
        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        Collection<Profesor> friends = prof1.filteredFriends(x -> true);
        assertTrue(friends.contains(prof2));
        assertTrue(friends.contains(prof3));
        assertEquals(friends.size(), 2);

        friends = prof1.filteredFriends(null);

        // changing the collection should not affect actual friend list
        friends.remove(prof2);
        friends = prof1.filteredFriends(x -> true);
        assertTrue(friends.contains(prof2));
        assertTrue(friends.contains(prof3));
        assertEquals(friends.size(), 2);

        assertTrue(prof1.filteredFriends(x -> false).isEmpty());

        friends = prof1.filteredFriends(x -> x.getId() == 2);
        assertTrue(friends.contains(prof2));
        assertFalse(friends.contains(prof3));
        assertEquals(friends.size(), 1);

    }

    @Test
    public void favoritesFilterAndSorting() {
        assertTrue(prof1.filterAndSortFavorites(Comparable::compareTo, c1 -> true).isEmpty());
        try {
            casa1.rate(prof1, 1);
            casa2.rate(prof1, 1);
            casa3.rate(prof1, 1);
            casa4.rate(prof1, 1);
            casa5.rate(prof1, 1);
            casa6.rate(prof1, 1);

            prof1.favorite(casa1);
            prof1.favorite(casa2);
            prof1.favorite(casa3);
            prof1.favorite(casa4);
            prof1.favorite(casa5);
            prof1.favorite(casa6);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException | CasaDeBurrito.RateRangeException e) {
            fail();
        }
        assertTrue(prof1.filterAndSortFavorites(Comparable::compareTo, c1 -> false).isEmpty());
        assertEquals(prof1.filterAndSortFavorites(Comparable::compareTo, c1 -> true).toArray(),
                new CasaDeBurritoImpl[]{casa1, casa2, casa3, casa4, casa5, casa6});
        assertEquals(prof1.filterAndSortFavorites(Comparator.reverseOrder(), c1 -> true).toArray(),
                new CasaDeBurritoImpl[]{casa6, casa5, casa4, casa3, casa2, casa1});
        assertEquals(prof1.filterAndSortFavorites(Comparable::compareTo, c1 -> c1.getId() % 2 == 0).toArray(),
                new CasaDeBurritoImpl[] {casa2, casa4, casa6});

        // Testing favoritesByRating
        assertEquals(prof1.favoritesByRating(0).toArray(),
                new CasaDeBurritoImpl[]{casa2, casa1, casa5, casa6, casa3, casa4});

        try {
            casa1.rate(prof1, 2);
            casa2.rate(prof1, 3);
            casa3.rate(prof1, 4);
            casa4.rate(prof1, 1);
            casa5.rate(prof1, 5);
            casa6.rate(prof1, 0);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByRating(0).toArray(),
                new CasaDeBurritoImpl[]{casa5, casa3, casa2, casa1, casa4, casa6});

        assertEquals(prof1.favoritesByRating(-1).toArray(),
                new CasaDeBurritoImpl[]{casa5, casa3, casa2, casa1, casa4, casa6});

        assertEquals(prof1.favoritesByRating(3).toArray(),
                new CasaDeBurritoImpl[]{casa5, casa3, casa2});

        assertEquals(prof1.favoritesByRating(33).toArray(),
                new CasaDeBurritoImpl[]{});

        try {
            casa1.rate(prof1, 1);
            casa2.rate(prof1, 1);
            casa3.rate(prof1, 2);
            casa4.rate(prof1, 2);
            casa5.rate(prof1, 3);
            casa6.rate(prof1, 3);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByRating(0).toArray(),
                new CasaDeBurritoImpl[]{casa5, casa6, casa3, casa4, casa2, casa1});

        try {
            casa1.rate(prof2, 3);
            casa2.rate(prof2, 3);
            casa3.rate(prof2, 2);
            casa4.rate(prof2, 2);
            casa5.rate(prof2, 1);
            casa6.rate(prof2, 1);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByRating(0).toArray(),
                new CasaDeBurritoImpl[]{casa2, casa1, casa5, casa6, casa3, casa4});

        try {
            casa1.rate(prof2, 0);
            casa2.rate(prof2, 0);
            casa3.rate(prof2, 0);
            casa4.rate(prof2, 0);
            casa5.rate(prof2, 0);
            casa6.rate(prof2, 0);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        try {
            casa1.rate(prof1, 3);
            casa2.rate(prof1, 3);
            casa3.rate(prof1, 2);
            casa4.rate(prof1, 1);
            casa5.rate(prof1, 2);
            casa6.rate(prof1, 1);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByRating(0).toArray(),
                // if you fail here, you might be casing doubles to int in your comparing
                new CasaDeBurritoImpl[]{casa2, casa1, casa5, casa3, casa6, casa4});

        // Test favorites by distance

        assertEquals(prof2.favoritesByDist(1000).toArray(),
                new CasaDeBurritoImpl[]{});

        try {
            casa1.rate(prof1, 1);
            casa2.rate(prof1, 1);
            casa3.rate(prof1, 1);
            casa4.rate(prof1, 1);
            casa5.rate(prof1, 1);
            casa6.rate(prof1, 1);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByDist(20).toArray(),
                new CasaDeBurritoImpl[]{});

        assertEquals(prof1.favoritesByDist(100).toArray(),
                new CasaDeBurritoImpl[]{casa2, casa1, casa5, casa6, casa3, casa4});

        try {
            casa1.rate(prof1, 2);
            casa2.rate(prof1, 1);
            casa3.rate(prof1, 1);
            casa4.rate(prof1, 2);
            casa5.rate(prof1, 1);
            casa6.rate(prof1, 2);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        assertEquals(prof1.favoritesByDist(100).toArray(),
                new CasaDeBurritoImpl[]{casa2, casa1, casa6, casa5, casa4, casa3});

        assertEquals(prof1.favoritesByDist(50).toArray(),
                new CasaDeBurritoImpl[]{casa2, casa1, casa6, casa5});

        assertEquals(prof1.favoritesByDist(10).toArray(),
                new CasaDeBurritoImpl[]{});

    }

    @Test
    public void equals() {
        assertEquals(prof1, prof1);
        assertNotEquals(prof1, prof2);
        assertNotEquals(prof2, prof1);

        // everthing is differenct except id
        ProfesorImpl prof4 = new ProfesorImpl(1, "Santana");
        assertEquals(prof4, prof1);
        assertEquals(prof1, prof4);

        // everything is the same except id
        prof4 = new ProfesorImpl(4, "Wamuu");
        assertNotEquals(prof1, prof4);
        assertNotEquals(prof4, prof1);
    }

    @Test
    public void toStringTest() {
        assertEquals(prof1.toString(), "Profesor: Wamuu.\nId: 1.\nFavorites: .");
        try {
            casa1.rate(prof1, 3);
            casa2.rate(prof1, 3);
            prof1.favorite(casa1);
            prof1.favorite(casa1);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException | CasaDeBurrito.RateRangeException e) {
            fail();
        }
        assertEquals(prof1.toString(), "Profesor: Wamuu.\nId: 1.\nFavorites: Italian Casa.");

        try {
            prof1.favorite(casa1).favorite(casa2);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {
            fail();
        }

        assertEquals(prof1.toString(), "Profesor: Wamuu.\nId: 1.\nFavorites: American Casa, Italian Casa.");
    }

    @Test
    public void compareTo() {
        assertTrue(prof1.compareTo(prof2) < 0);
        assertTrue(prof2.compareTo(prof1) > 0);
        assertEquals(prof1.compareTo(prof1), 0);
        assertEquals(prof1.compareTo(prof1), 0);

        // Same id but everything else different
        ProfesorImpl prof4 = new ProfesorImpl(1, "Santana");
        assertEquals(prof1.compareTo(prof4), 0);
        assertEquals(prof4.compareTo(prof1), 0);

        // Same everythin but different id
        prof4 = new ProfesorImpl(4, "Wamuu");
        assertTrue(prof1.compareTo(prof4) < 0);
        assertTrue(prof4.compareTo(prof1) > 0);
    }

    @Test
    public void hashCodeImplemented() {
        assertNotEquals(prof1.hashCode(), prof2.hashCode());
        assertEquals(prof1.hashCode(), prof1.hashCode());
        ProfesorImpl prof4 = new ProfesorImpl(1, "Santana");
        assertEquals(prof1.hashCode(), prof4.hashCode()); // hash code equals iff prof equals
    }
}