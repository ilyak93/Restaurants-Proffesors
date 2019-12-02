package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CartelDeNachos.*;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static com.sun.xml.internal.ws.util.VersionUtil.compare;
import static java.lang.CharSequence.*;
import static org.junit.Assert.*;


public class SanityTest {

    CartelDeNachos cartelDeNachos = init();
    CartelDeNachos anotherCartel = makeCartel();

    private CartelDeNachos init() {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        Set<String> menu4 = new HashSet<>();
        menu4.add("LaBurritoDelManiffic");
        menu4.add("OmQuesoLaDuck");
        menu4.add("TreNachosMagnificat");
        menu4.add("PonGuacamoleGorbachov");
        try {
            cartel.addCasaDeBurrito(4, "LaSheff", 1, menu4);
            cartel.joinCartel(6, "SomeFrenchGuy");
            cartel.joinCartel(7, "SomeVeganLady");
            cartel.joinCartel(1, "Menash");
            cartel.joinCartel(4, "Guy");
            cartel.joinCartel(5, "Pini");
            cartel.joinCartel(2, "Pnina");
            cartel.joinCartel(3, "Iris");
        } catch (CasaDeBurritoAlreadyInSystemException | ProfesorAlreadyInSystemException e) {
            fail();
        }
        Set<String> menu2 = new HashSet<>();
        menu2.add("McBurrito");
        menu2.add("McQueso");
        menu2.add("McNachos");
        menu2.add("McGuacamole");
        try {
            cartel.addCasaDeBurrito(2, "McDonSalvador", 3, menu2);
        } catch (CasaDeBurritoAlreadyInSystemException e) {
            fail();
        }
        Set<String> menu1 = new HashSet<>();
        menu1.add("Burito");
        menu1.add("Queso");
        menu1.add("Nachos");
        menu1.add("Guacamole");
        try {
            cartel.addCasaDeBurrito(1, "burritoAhushluk", 4, menu1);
        } catch (CasaDeBurritoAlreadyInSystemException e) {
            fail();
        }
        Set<String> menu3 = new HashSet<>();
        menu3.add("LettuceBurrito");
        menu3.add("TomatoQueso");
        menu3.add("VeganNachos");
        menu3.add("AvocadoGuacamole");
        try {
            cartel.addCasaDeBurrito(3, "VeganIsUs", 2, menu3);
        } catch (CasaDeBurritoAlreadyInSystemException e) {
            fail();
        }
        return cartel;
    }

    private CartelDeNachos makeCartel() {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        try {
            cartel.joinCartel(1, "Shaul");
            cartel.joinCartel(2, "Irad");
            cartel.joinCartel(3, "Geiger");
            cartel.joinCartel(4, "Censor");
            cartel.joinCartel(5, "Gadi");
            cartel.joinCartel(6, "Alex");
            cartel.joinCartel(7, "Miki");
            Profesor p1 = cartel.getProfesor(1);
            Profesor p2 = cartel.getProfesor(2);
            Profesor p3 = cartel.getProfesor(3);
            Profesor p4 = cartel.getProfesor(4);
            Profesor p5 = cartel.getProfesor(5);
            Profesor p6 = cartel.getProfesor(6);
            Profesor p7 = cartel.getProfesor(7);
            cartel.addConnection(p1, p2);
            cartel.addConnection(p1, p3);
            cartel.addConnection(p1, p4);
            cartel.addConnection(p1, p5);
            cartel.addConnection(p1, p6);
            cartel.addConnection(p1, p7);
            cartel.addConnection(p3, p4);
            cartel.addConnection(p4, p5);
            cartel.addConnection(p5, p6);
            cartel.addConnection(p5, p7);
        } catch (ProfesorAlreadyInSystemException | ProfesorNotInSystemException
                | ConnectionAlreadyExistsException | SameProfesorException e) {
            fail();
        }

        try {
            Set<String> menu = new HashSet<>();
            cartel.addCasaDeBurrito(11, "El Toro", 0, menu);
            cartel.addCasaDeBurrito(12, "Delicioso", 0, menu);
            cartel.addCasaDeBurrito(13, "La Barra", 0, menu);
            cartel.addCasaDeBurrito(14, "El Gaucho", 0, menu);
            cartel.addCasaDeBurrito(15, "El Gaucho", 0, menu);
            cartel.addCasaDeBurrito(16, "La Taberna", 0, menu);
            cartel.addCasaDeBurrito(17, "El Encanto", 0, menu);
            CasaDeBurrito c1 = cartel.getCasaDeBurrito(11);
            CasaDeBurrito c2 = cartel.getCasaDeBurrito(12);
            CasaDeBurrito c3 = cartel.getCasaDeBurrito(13);
            CasaDeBurrito c4 = cartel.getCasaDeBurrito(14);
            CasaDeBurrito c5 = cartel.getCasaDeBurrito(15);
            CasaDeBurrito c6 = cartel.getCasaDeBurrito(16);
            CasaDeBurrito c7 = cartel.getCasaDeBurrito(17);
            Profesor p = cartel.getProfesor(7);
            c1.rate(p, 5);
            c2.rate(p, 5);
            c3.rate(p, 5);
            c4.rate(p, 5);
            c5.rate(p, 5);
            c6.rate(p, 5);
            c7.rate(p, 5);
            p.favorite(c1);
            p.favorite(c2);
            p.favorite(c3);
            p.favorite(c4);
            p.favorite(c5);
            p.favorite(c6);
            p.favorite(c7);
        } catch (CasaDeBurritoAlreadyInSystemException | CasaDeBurritoNotInSystemException
                | ProfesorNotInSystemException | RateRangeException | UnratedFavoriteCasaDeBurritoException e) {
            fail();
        }
        return cartel;
    }

    @Test
    public void BasicCasaTest() {
        Set<String> menu = new HashSet<>();
        menu.add("Burito");
        menu.add("Queso");
        menu.add("Nachos");
        menu.add("Guacamole");
        CasaDeBurrito c1 = new CasaDeBurritoImpl(1, "Taqueria", 65, menu);
        menu.add("Frijoles"); // TODO: verify if need to check this
        assertEquals(1, c1.getId(), 0);
        assertEquals(65, c1.distance(), 0);
        assertEquals("Taqueria", c1.getName());
        assertEquals(0, c1.numberOfRates(), 0);
        assertEquals(0.0, c1.averageRating(), 0);
        assertEquals("CasaDeBurrito: Taqueria.\nId: 1.\nDistance: 65.\nMenu: Burito, Guacamole, Nachos, Queso.\n",
                c1.toString());
        CasaDeBurrito c2 = new CasaDeBurritoImpl(7, "", 0, new HashSet<>());
        assertEquals("CasaDeBurrito: .\nId: 7.\nDistance: 0.\nMenu: .\n", c2.toString());
        assertNotEquals(c1, c2);
        assertNotEquals(c2, c1);
        CasaDeBurrito c3 = new CasaDeBurritoImpl(1, "", 0, new HashSet<>());
        assertEquals(c1, c3);
        assertEquals(c3, c1);
    }

    @Test
    public void BasicProfesorTest() {
        int exp_counter = 0;
        Profesor p1 = new ProfesorImpl(1, "El Profesor");
        assertEquals(1, p1.getId(), 0);
        assertTrue(p1.favorites().isEmpty());
        assertTrue(p1.getFriends().isEmpty());
        String profStr = "Profesor: El Profesor.\nId: 1.\nFavorites: .";
        assertEquals(profStr, p1.toString());
        try {
            p1.addFriend(p1);
        } catch (SameProfesorException e) {
            exp_counter++;
        }
        catch (ConnectionAlreadyExistsException e) {
            fail();
        }
        assertEquals(1, exp_counter);
        Profesor p2 = new ProfesorImpl(2, "Un Profesor");
        try {
            p1.addFriend(p2);
        } catch (SameProfesorException | ConnectionAlreadyExistsException e) {
            fail();
        }
        assertTrue(p1.getFriends().contains(p2));
        assertTrue(p2.getFriends().isEmpty());
        try {
            p1.addFriend(p2);
        } catch (SameProfesorException e) {
            fail();
        }
        catch (ConnectionAlreadyExistsException e) {
            exp_counter++;
        }
        assertEquals(2, exp_counter);
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);
        Profesor p3 = new ProfesorImpl(1, "Nada");
        assertEquals(p1, p3);
        assertEquals(p3, p1);
    }

    @Test
    public void BasicCartelTest() {
        int exp_counter = 0;
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertTrue(cartel.registeredProfesores().isEmpty());
        assertTrue(cartel.registeredCasasDeBurrito().isEmpty());
        try {
            cartel.joinCartel(2, "El Profesor");
        } catch (ProfesorAlreadyInSystemException e) {
            fail();
        }
        assertEquals(1, cartel.registeredProfesores().size());
        try {
            cartel.joinCartel(2, "Un Profesor");
        } catch (ProfesorAlreadyInSystemException e) {
            exp_counter++;
        }
        assertEquals(1, exp_counter);
        try {
            cartel.addCasaDeBurrito(2, "Taqueria", 65, new HashSet<>());
        } catch (CasaDeBurritoAlreadyInSystemException e) {
            fail();
        }
        assertEquals(1, cartel.registeredCasasDeBurrito().size());
        try {
            cartel.addCasaDeBurrito(2, "El Lugar", 0, new HashSet<>());
        } catch (CasaDeBurritoAlreadyInSystemException e) {
            exp_counter++;
        }
        assertEquals(2, exp_counter);
        try {
            Profesor p = cartel.getProfesor(0);
        } catch (ProfesorNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(3, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            assertEquals(2, p.getId());
        } catch (ProfesorNotInSystemException e) {
            fail();
        }
        try {
            CasaDeBurrito c = cartel.getCasaDeBurrito(0);
        } catch (CasaDeBurritoNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(4, exp_counter);
        try {
            CasaDeBurrito c = cartel.getCasaDeBurrito(2);
            assertEquals(2, c.getId());
        } catch (CasaDeBurritoNotInSystemException e) {
            fail();
        }
        try {
            Profesor p1 = cartel.getProfesor(2);
            Profesor p2 = new ProfesorImpl(1, "Un Profesor");
            cartel.addConnection(p1, p2);
        } catch (SameProfesorException | ConnectionAlreadyExistsException e) {
            fail();
        } catch (ProfesorNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(5, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            cartel.addConnection(p, p);
        } catch (ProfesorNotInSystemException | ConnectionAlreadyExistsException e) {
            fail();
        } catch (SameProfesorException e) {
            exp_counter++;
        }
        assertEquals(6, exp_counter);
        try {
            cartel.joinCartel(1, "Un Profesor");
            Profesor p1 = cartel.getProfesor(1);
            Profesor p2 = cartel.getProfesor(2);
            cartel.addConnection(p1, p2);
        } catch (ProfesorAlreadyInSystemException | ProfesorNotInSystemException | SameProfesorException
                | ConnectionAlreadyExistsException e) {
            fail();
        }
        try {
            Profesor p1 = cartel.getProfesor(1);
            Profesor p2 = cartel.getProfesor(2);
            cartel.addConnection(p1, p2);
        } catch (ProfesorNotInSystemException | SameProfesorException e) {
            fail();
        } catch (ConnectionAlreadyExistsException e) {
            exp_counter++;
        }
        assertEquals(7, exp_counter);
        try {
            Profesor p = new ProfesorImpl(3, "Profesor");
            cartel.favoritesByRating(p);
        } catch (ProfesorNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(8, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            cartel.favoritesByRating(p);
        } catch (ProfesorNotInSystemException e) {
            fail();
        }
        try {
            Profesor p = new ProfesorImpl(3, "Profesor");
            cartel.favoritesByDist(p);
        } catch (ProfesorNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(9, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            cartel.favoritesByDist(p);
        } catch (ProfesorNotInSystemException e) {
            fail();
        }
        try {
            Profesor p = new ProfesorImpl(3, "Profesor");
            CasaDeBurrito c = cartel.getCasaDeBurrito(2);
            cartel.getRecommendation(p, c, 4);
        } catch (CasaDeBurritoNotInSystemException | ImpossibleConnectionException e) {
            fail();
        } catch (ProfesorNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(10, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            CasaDeBurrito c = new CasaDeBurritoImpl(1, "El Lugar", 0, new HashSet<>());
            cartel.getRecommendation(p, c, 4);
        } catch (ProfesorNotInSystemException | ImpossibleConnectionException e) {
            fail();
        } catch (CasaDeBurritoNotInSystemException e) {
            exp_counter++;
        }
        assertEquals(11, exp_counter);
        try {
            Profesor p = cartel.getProfesor(2);
            CasaDeBurrito c = cartel.getCasaDeBurrito(2);
            cartel.getRecommendation(p, c, -1);
        } catch (ProfesorNotInSystemException | CasaDeBurritoNotInSystemException e) {
            fail();
        } catch (ImpossibleConnectionException e) {
            exp_counter++;
        }
        assertEquals(12, exp_counter);
        String profStr = "Registered profesores: 1, 2.\n" +
                "Registered casas de burrito: 2.\n" +
                "Profesores:\n" +
                "1 -> [2].\n" +
                "2 -> [1].\n" +
                "End profesores.";
        assertEquals(profStr, cartel.toString());
    }

    @Test
    public void RatingTest() {
        int exp_counter = 0;
        CartelDeNachos cartel = cartelDeNachos;
        try {
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(1),-1);
        } catch (RateRangeException e) {
            exp_counter++;
        } catch (CasaDeBurritoNotInSystemException | ProfesorNotInSystemException e) {
            fail();
        }
        assertEquals(1, exp_counter);
        try {
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(1),11);
        } catch (RateRangeException e) {
            exp_counter++;
        } catch (CasaDeBurritoNotInSystemException | ProfesorNotInSystemException e) {
            fail();
        }
        assertEquals(2, exp_counter);
        try {
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(1),4);
            cartel.getCasaDeBurrito(4).rate(cartel.getProfesor(1),5);
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(2),4);
            cartel.getCasaDeBurrito(2).rate(cartel.getProfesor(2),3);
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(3),2);
            cartel.getCasaDeBurrito(2).rate(cartel.getProfesor(3),3);
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(4),5);
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(5),4);
            cartel.getCasaDeBurrito(4).rate(cartel.getProfesor(6),4);
            cartel.getCasaDeBurrito(3).rate(cartel.getProfesor(7),1);
            assertEquals(5, cartel.getCasaDeBurrito(1).numberOfRates());    // ratings: 4, 4, 2, 5, 4
            assertEquals(3.8, cartel.getCasaDeBurrito(1).averageRating(), 0.0);
            assertEquals(2, cartel.getCasaDeBurrito(2).numberOfRates());    // ratings: 3, 3
            assertEquals(3.0, cartel.getCasaDeBurrito(2).averageRating(), 0.0);
            assertEquals(1, cartel.getCasaDeBurrito(3).numberOfRates());    // ratings: 1
            assertEquals(1.0, cartel.getCasaDeBurrito(3).averageRating(), 0.0);
            assertEquals(2, cartel.getCasaDeBurrito(4).numberOfRates());    // ratings: 5, 4
            assertEquals(4.5, cartel.getCasaDeBurrito(4).averageRating(), 0.0);
        } catch (RateRangeException | CasaDeBurritoNotInSystemException | ProfesorNotInSystemException e) {
            fail();
        }
    }

    @Test
    public void FilteringTest() {
        CartelDeNachos cartel = anotherCartel;

        Predicate<Profesor> isFriendly = p -> p.getFriends().size() > 2;
        try {
            Profesor p = cartel.getProfesor(1);

            Set<Profesor> unfilteredFriends = p.getFriends();
            assertEquals(6, unfilteredFriends.size());
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(2)));
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(3)));
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(4)));
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(5)));
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(6)));
            assertTrue(unfilteredFriends.contains(cartel.getProfesor(7)));

            Set<Profesor> filteredFriends = p.filteredFriends(isFriendly);
            Iterator<Profesor> it = filteredFriends.iterator();
            assertEquals(it.next(), cartel.getProfesor(4));
            assertEquals(it.next(), cartel.getProfesor(5));
            assertFalse(it.hasNext());
        } catch (ProfesorNotInSystemException e) {
            fail();
        }

        Predicate<CasaDeBurrito> nameStartsWithEl = c -> c.getName().startsWith("El");

        Comparator<CasaDeBurrito> backwardsNameComparator = (CasaDeBurrito c1, CasaDeBurrito c2) ->
        {
            if (!c1.getName().equals(c2.getName())) {
                return c2.getName().compareTo(c1.getName());
            //    return compare(c2.getName(), c1.getName());
            }
            return Integer.compare(c2.getId(), c1.getId());
        };

        try {
            Profesor p = cartel.getProfesor(7);

            Collection<CasaDeBurrito> faves = p.favorites();
            assertEquals(7, faves.size());
            assertTrue(faves.contains(cartel.getCasaDeBurrito(11)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(12)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(13)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(14)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(15)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(16)));
            assertTrue(faves.contains(cartel.getCasaDeBurrito(17)));

            Collection<CasaDeBurrito> filteredFaves = p.filterAndSortFavorites(backwardsNameComparator, nameStartsWithEl);
            Iterator<CasaDeBurrito> it = filteredFaves.iterator();
            assertEquals(it.next(), cartel.getCasaDeBurrito(11));
            assertEquals(it.next(), cartel.getCasaDeBurrito(15));
            assertEquals(it.next(), cartel.getCasaDeBurrito(14));
            assertEquals(it.next(), cartel.getCasaDeBurrito(17));
            assertFalse(it.hasNext());
        } catch (ProfesorNotInSystemException | CasaDeBurritoNotInSystemException e) {
            fail();
        }
    }

    @Test
    public void FavoritesTest() {
        int exp_counter = 0;
        CartelDeNachos cartel = cartelDeNachos;
        try {
            cartel.getProfesor(1).favorite(cartel.getCasaDeBurrito(1));
        } catch (UnratedFavoriteCasaDeBurritoException e) {
            exp_counter++;
        } catch (ProfesorNotInSystemException | CasaDeBurritoNotInSystemException e) {
            fail();
        }
        assertEquals(1, exp_counter);
        try {
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(1),4);
            cartel.getProfesor(2).favorite(cartel.getCasaDeBurrito(1));
        } catch (UnratedFavoriteCasaDeBurritoException e) {
            exp_counter++;
        } catch (RateRangeException | ProfesorNotInSystemException | CasaDeBurritoNotInSystemException e) {
            fail();
        }
        assertEquals(2, exp_counter);
        try {
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(1),4);
            cartel.getProfesor(1).favorite(cartel.getCasaDeBurrito(1));
            cartel.getCasaDeBurrito(4).rate(cartel.getProfesor(1),5);
            cartel.getProfesor(1).favorite(cartel.getCasaDeBurrito(4));
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(2),4);
            cartel.getProfesor(2).favorite(cartel.getCasaDeBurrito(1));
            cartel.getCasaDeBurrito(2).rate(cartel.getProfesor(2),3);
            cartel.getProfesor(2).favorite(cartel.getCasaDeBurrito(2));
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(3),2);
            cartel.getProfesor(3).favorite(cartel.getCasaDeBurrito(1));
            cartel.getCasaDeBurrito(2).rate(cartel.getProfesor(3),3);
            cartel.getProfesor(3).favorite(cartel.getCasaDeBurrito(2));
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(4),5);
            cartel.getProfesor(4).favorite(cartel.getCasaDeBurrito(1));
            cartel.getCasaDeBurrito(1).rate(cartel.getProfesor(5),4);
            cartel.getProfesor(5).favorite(cartel.getCasaDeBurrito(1));
            cartel.getCasaDeBurrito(4).rate(cartel.getProfesor(6),4);
            cartel.getProfesor(6).favorite(cartel.getCasaDeBurrito(4));
            cartel.getCasaDeBurrito(3).rate(cartel.getProfesor(7),1);
            cartel.getProfesor(7).favorite(cartel.getCasaDeBurrito(3));
            /*
              ProfId | FavRestaurant
                1          1,4
                2          1,2
                3          1,2
                4          1
                5          1
                6          4
                7          3
             */
        } catch (RateRangeException | CasaDeBurritoNotInSystemException | ProfesorNotInSystemException
                | UnratedFavoriteCasaDeBurritoException e) {
            fail();
        }
        try {
            cartel.addConnection(cartel.getProfesor(1),cartel.getProfesor(2));
            cartel.addConnection(cartel.getProfesor(1),cartel.getProfesor(3));
            cartel.addConnection(cartel.getProfesor(1),cartel.getProfesor(4));
            cartel.addConnection(cartel.getProfesor(6),cartel.getProfesor(7));
            cartel.addConnection(cartel.getProfesor(6),cartel.getProfesor(5));
            /*
              ProfId | friends
                1      2,3,4
                6       7,5
             */
        } catch (Exception e) {
            fail();
        }
        try {
            Collection<CasaDeBurrito> favesByRate = cartel.favoritesByRating(cartel.getProfesor(1));
            Iterator<CasaDeBurrito> it = favesByRate.iterator();
            assertEquals(it.next(), cartel.getCasaDeBurrito(1));
            assertEquals(it.next(), cartel.getCasaDeBurrito(2));
        } catch (Exception e) {
            fail();
        }
        assertEquals("Registered profesores: 1, 2, 3, 4, 5, 6, 7.\n" +
                "Registered casas de burrito: 1, 2, 3, 4.\n" +
                "Profesores:\n" +
                "1 -> [2, 3, 4].\n" +
                "2 -> [1].\n" +
                "3 -> [1].\n" +
                "4 -> [1].\n" +
                "5 -> [6].\n" +
                "6 -> [5, 7].\n" +
                "7 -> [6].\n" +
                "End profesores.", cartel.toString());
    }

}

