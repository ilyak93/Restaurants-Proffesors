package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CartelDeNachosImplTest {
    private CartelDeNachos cartel;
    private Profesor prof1;
    private Profesor prof2;
    private Profesor prof3;
    private Profesor prof4;
    private Profesor prof5;

    private CasaDeBurrito casa1, casa2, casa3;

    @Before
    public void setUp() throws Exception {
        cartel = new CartelDeNachosImpl();
        prof1 = new ProfesorImpl(1, "Charlie");
        prof2 = new ProfesorImpl(2, "Dee");
        prof3 = new ProfesorImpl(3, "Mac");
        prof4 = new ProfesorImpl(4, "Dennis");
        prof5 = new ProfesorImpl(5, "Frank");

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));
        String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
        Set<String> menu3 = new HashSet<>(Arrays.asList(menu2_array));

        casa1 = new CasaDeBurritoImpl(1, "Italian Casa", 40, menu1);
        casa2 = new CasaDeBurritoImpl(2, "American Casa", 30, menu2);
        casa3 = new CasaDeBurritoImpl(3, "Technion Student Casa", 60, menu3);

        assertEquals(cartel.joinCartel(1, "Charlie"), prof1);
        assertEquals(cartel.joinCartel(2, "Dee"), prof2);
        assertEquals(cartel.joinCartel(3, "Mac"), prof3);
        assertEquals(cartel.joinCartel(4, "Dennis"), prof4);
        assertEquals(cartel.joinCartel(5, "Frank"), prof5);
        assertEquals(cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu1), casa1);
        assertEquals(cartel.addCasaDeBurrito(2, "American Casa", 30, menu2), casa2);
        assertEquals(cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3), casa3);

        cartel = new CartelDeNachosImpl();
        prof1 = cartel.joinCartel(1, "Charlie");
        prof2 = cartel.joinCartel(2, "Dee");
        prof3 = cartel.joinCartel(3, "Mac");
        prof4 = cartel.joinCartel(4, "Dennis");
        prof5 = cartel.joinCartel(5, "Frank");
        casa1 = cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu1);
        casa2 = cartel.addCasaDeBurrito(2, "American Casa", 30, menu2);
        casa3 = cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3);
    }

    @Test
    public void joinCartel() {
        try {
            cartel.joinCartel(1, "Not Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
        try {
            cartel.joinCartel(1, "Not Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
        try {
            cartel.joinCartel(1, "Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
    }

    @Test
    public void addCasaDeBurrito() {

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));
        String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
        Set<String> menu3 = new HashSet<>(Arrays.asList(menu2_array));

        try {
            cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu1);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException ignored) {

        }
        try {
            cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu2);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException ignored) {

        }
    }

    @Test
    public void registeredProfesores() {
        Collection<Profesor> profs = cartel.registeredProfesores();
        assertEquals(new HashSet<>(profs), new HashSet<Profesor>(Arrays.asList(prof1, prof2, prof3, prof4, prof5)));
        profs.remove(prof1);
        profs = cartel.registeredProfesores();
        assertEquals(new HashSet<>(profs), new HashSet<Profesor>(Arrays.asList(prof1, prof2, prof3, prof4, prof5)));

        // do not create a deep copy, only shallow copy
        for (Profesor p : profs) {
            try {
                casa1.rate(p, 3);
                p.favorite(casa1);
            } catch (CasaDeBurrito.RateRangeException | Profesor.UnratedFavoriteCasaDeBurritoException e) {
                fail();
            }
        } // so changes made to the members of the set should actually affect cartel
        profs = cartel.registeredProfesores();
        for (Profesor p : profs) {
            assertEquals(new HashSet<>(p.favorites()), new HashSet<>(Collections.singletonList(casa1)));
        }

    }

    @Test
    public void registeredCasasDeBurrito() {
        Collection<CasaDeBurrito> casas = cartel.registeredCasasDeBurrito();
        assertEquals(new HashSet<>(casas), new HashSet<CasaDeBurrito>(Arrays.asList(casa1, casa2, casa3)));
        casas.remove(casa1);
        casas = cartel.registeredCasasDeBurrito();
        assertEquals(new HashSet<>(casas), new HashSet<CasaDeBurrito>(Arrays.asList(casa1, casa2, casa3)));

        // do not create a deep copy, only shallow copy
        for (CasaDeBurrito c : casas) {
            try {
                c.rate(prof1, 3);
            } catch (CasaDeBurrito.RateRangeException e) {
                fail();
            }
        } // so changes made to the members of the set should actually affect cartel
        casas = cartel.registeredCasasDeBurrito();
        for (CasaDeBurrito c : casas) {
            assertEquals(c.averageRating(), 3, 0);
        }
    }

    @Test
    public void getProfesor() {
        try {
            assertEquals(cartel.getProfesor(1), prof1);
            assertEquals(cartel.getProfesor(2), prof2);
            assertEquals(cartel.getProfesor(3), prof3);
            assertEquals(cartel.getProfesor(4), prof4);
            assertEquals(cartel.getProfesor(5), prof5);
        } catch (Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        try {
            cartel.getProfesor(6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        }

        // check that this is a reference and not a copy
        try {
            Profesor p = cartel.getProfesor(1);
            p.addFriend(prof2);
            p = cartel.getProfesor(1);
            assertEquals(new HashSet<>(p.getFriends()), new HashSet<Profesor>(Collections.singletonList(prof2)));
        } catch (Profesor.ProfesorNotInSystemException |
                Profesor.ConnectionAlreadyExistsException |
                Profesor.SameProfesorException e) {
            fail();
        }
    }

    @Test
    public void getCasaDeBurrito() {
        try {
            assertEquals(cartel.getCasaDeBurrito(1), casa1);
            assertEquals(cartel.getCasaDeBurrito(2), casa2);
            assertEquals(cartel.getCasaDeBurrito(3), casa3);

        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            fail();
        }

        try {
            cartel.getCasaDeBurrito(4);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException ignored) {
        }

        // check that this is a reference and not a copy
        try {
            CasaDeBurrito c = cartel.getCasaDeBurrito(1);
            c.rate(prof1, 3);
            assertEquals(c.averageRating(), 3, 0);

        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException | CasaDeBurrito.RateRangeException e) {
            fail();
        }
    }

    @Test
    public void addConnection() {
        try {
            cartel.addConnection(prof1, prof2).addConnection(prof2, prof3);
        } catch (Profesor.SameProfesorException |
                Profesor.ConnectionAlreadyExistsException |
                Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        try {
            cartel.addConnection(prof1, prof2);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ProfesorNotInSystemException e) {
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {

        }

        try {
            cartel.addConnection(prof2, prof1);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ProfesorNotInSystemException e) {
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {

        }

        Profesor prof6 = new ProfesorImpl(6, "Charlie");
        Profesor prof7 = new ProfesorImpl(7, "Charlie");
        try {
            cartel.addConnection(prof6, prof1);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
           fail();
        }
        try {
            cartel.addConnection(prof1, prof6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }
        try {
            cartel.addConnection(prof7, prof6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        try {
            cartel.addConnection(prof1, prof1);
            fail();
        } catch (Profesor.ProfesorNotInSystemException | Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.SameProfesorException ignored) {

        }
    }

    @Test
    public void favoritesByRating() {
        try {
            cartel.addConnection(prof1, prof2);
            cartel.addConnection(prof1, prof3);
            cartel.addConnection(prof1, prof4);
            cartel.addConnection(prof4, prof5);

        } catch (Profesor.ProfesorNotInSystemException
                | Profesor.ConnectionAlreadyExistsException
                | Profesor.SameProfesorException e) {
            fail();
        }

    }

    @Test
    public void favoritesByDist() {
    }

    @Test
    public void getRecommendation() {
    }

    @Test
    public void getMostPopularRestaurantsIds() {
    }

    @Test
    public void toStringTest() {
    }
}