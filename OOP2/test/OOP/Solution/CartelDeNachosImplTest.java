package OOP.Solution;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import OOP.Provided.CartelDeNachos.ImpossibleConnectionException;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CasaDeBurrito.CasaDeBurritoAlreadyInSystemException;
import OOP.Provided.CasaDeBurrito.CasaDeBurritoNotInSystemException;
import OOP.Provided.CasaDeBurrito.RateRangeException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.ConnectionAlreadyExistsException;
import OOP.Provided.Profesor.ProfesorAlreadyInSystemException;
import OOP.Provided.Profesor.ProfesorNotInSystemException;
import OOP.Provided.Profesor.SameProfesorException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartelDeNachosImplTest {

  private CartelDeNachosImpl cartl;
  private Set<String> menu1;

  @BeforeEach
  void init() {
    cartl = new CartelDeNachosImpl();
    menu1 = new HashSet<>();
    menu1.add("Hamburger");
    menu1.add("Fries");
  }

  @Test
  void joinCartel() {
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag"));
    assertThrows(ProfesorAlreadyInSystemException.class, () -> cartl.joinCartel(1, "Zilag"));
    try {
      Profesor zilag = cartl.joinCartel(2, "Zilag");
      assertEquals(2, zilag.getId());
    } catch (ProfesorAlreadyInSystemException e) {
      e.printStackTrace();
    }
  }

  @Test
  void addCasaDeBurrito() {
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Buritos", 0, menu1));
    assertThrows(CasaDeBurritoAlreadyInSystemException.class,
        () -> cartl.addCasaDeBurrito(1, "Buritos", 0, menu1));
    try {
      CasaDeBurrito burito = cartl.addCasaDeBurrito(2, "Buritos", 0, menu1);
      assertEquals(2, burito.getId());

    } catch (CasaDeBurritoAlreadyInSystemException e) {
      e.printStackTrace();
    }
  }

  @Test
  void registeredProfesores() {
    try {
      Profesor p1 = cartl.joinCartel(1, "Zilag");
      assertTrue(cartl.registeredProfesores()
                      .contains(p1));
      Profesor p2 = cartl.joinCartel(2, "Zilag");
      assertTrue(cartl.registeredProfesores()
                      .contains(p2));
      Profesor p3 = cartl.joinCartel(3, "Zilag");
      assertTrue(cartl.registeredProfesores()
                      .contains(p3));
    } catch (ProfesorAlreadyInSystemException e) {
      e.printStackTrace();
    }
  }

  @Test
  void registeredCasasDeBurrito() {
    try {
      CasaDeBurrito c1 = cartl.addCasaDeBurrito(1, "Zilag", 0, menu1);
      assertTrue(cartl.registeredCasasDeBurrito()
                      .contains(c1));
      CasaDeBurrito c2 = cartl.addCasaDeBurrito(2, "Zilag", 0, menu1);
      assertTrue(cartl.registeredCasasDeBurrito()
                      .contains(c2));
      CasaDeBurrito c3 = cartl.addCasaDeBurrito(3, "Zilag", 0, menu1);
      assertTrue(cartl.registeredCasasDeBurrito()
                      .contains(c3));
    } catch (CasaDeBurritoAlreadyInSystemException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getProfesor() {
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag"));
    assertDoesNotThrow(() -> cartl.getProfesor(1));
    assertDoesNotThrow(() -> cartl.getProfesor(2));
    assertDoesNotThrow(() -> cartl.getProfesor(3));
    assertThrows(ProfesorNotInSystemException.class, () -> cartl.getProfesor(4));
  }

  @Test
  void getCasaDeBurrito() {
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Zilag", 0, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(2, "Zilag", 0, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(3, "Zilag", 0, menu1));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3));
    assertThrows(CasaDeBurritoNotInSystemException.class, () -> cartl.getCasaDeBurrito(4));

  }

  @Test
  void addConnection() {
    Profesor p1 = new ProfesorImpl(4, "Zilag");
    Profesor p2 = new ProfesorImpl(5, "Zilag");
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag"));
    try {
      p1 = cartl.getProfesor(1);
    } catch (ProfesorNotInSystemException e) {
      e.printStackTrace();
    }
    Profesor finalP = p1;
    Profesor finalP1 = p2;
    assertThrows(ProfesorNotInSystemException.class, () -> cartl.addConnection(finalP, finalP1));
    assertThrows(ProfesorNotInSystemException.class, () -> cartl.addConnection(finalP1, finalP));
    assertThrows(SameProfesorException.class, () -> cartl.addConnection(finalP, finalP));
    try {
      p2 = cartl.getProfesor(2);
    } catch (ProfesorNotInSystemException e) {
      e.printStackTrace();
    }
    Profesor finalP2 = p2;
    assertDoesNotThrow(() -> cartl.addConnection(finalP, finalP2));
    assertThrows(ConnectionAlreadyExistsException.class,
        () -> cartl.addConnection(finalP, finalP2));
  }

  @Test
  void favoritesByRating() {
    assertThrows(ProfesorNotInSystemException.class,
        () -> cartl.favoritesByRating(new ProfesorImpl(12, "None")));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Burito1", 1, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(2, "Burito2", 2, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(3, "Burito3", 3, menu1));
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag1"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag2"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag3"));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(1), 2));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(3), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(2), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(1), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(3), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(2), 4));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(1), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .addFriend(cartl.getProfesor(2)));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(2), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    try {
      Iterator<CasaDeBurrito> p1Rate = cartl.favoritesByRating(cartl.getProfesor(1))
                                            .iterator();
      Iterator<CasaDeBurrito> p2Rate = cartl.favoritesByRating(cartl.getProfesor(2))
                                            .iterator();
      Iterator<CasaDeBurrito> p3Rate = cartl.favoritesByRating(cartl.getProfesor(3))
                                            .iterator();
      try {
        assertEquals(cartl.getCasaDeBurrito(3), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(2), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(3), p2Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p2Rate.next());
        assertEquals(cartl.getCasaDeBurrito(2), p3Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p3Rate.next());
        assertEquals(cartl.getCasaDeBurrito(3), p3Rate.next());
      } catch (CasaDeBurritoNotInSystemException e) {
        e.printStackTrace();
      }
    } catch (ProfesorNotInSystemException e) {
      e.printStackTrace();
    }

  }

  @Test
  void favoritesByDist() {
    assertThrows(ProfesorNotInSystemException.class,
        () -> cartl.favoritesByDist(new ProfesorImpl(12, "None")));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Burito1", 1, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(2, "Burito2", 2, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(3, "Burito3", 2, menu1));
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag1"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag2"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag3"));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(1), 2));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(3), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(2), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(1), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(3), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(2), 4));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(1), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .addFriend(cartl.getProfesor(2)));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(2), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    try {
      Iterator<CasaDeBurrito> p1Rate = cartl.favoritesByDist(cartl.getProfesor(1))
                                            .iterator();
      Iterator<CasaDeBurrito> p2Rate = cartl.favoritesByDist(cartl.getProfesor(2))
                                            .iterator();
      Iterator<CasaDeBurrito> p3Rate = cartl.favoritesByDist(cartl.getProfesor(3))
                                            .iterator();
      try {
        assertEquals(cartl.getCasaDeBurrito(3), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(2), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p1Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p2Rate.next());
        assertEquals(cartl.getCasaDeBurrito(3), p2Rate.next());
        assertEquals(cartl.getCasaDeBurrito(1), p3Rate.next());
        assertEquals(cartl.getCasaDeBurrito(2), p3Rate.next());
        assertEquals(cartl.getCasaDeBurrito(3), p3Rate.next());
      } catch (CasaDeBurritoNotInSystemException e) {
        e.printStackTrace();
      }
    } catch (ProfesorNotInSystemException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getRecommendation() {
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Burito1", 1, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(2, "Burito2", 2, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(3, "Burito3", 2, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(4, "Burito3", 4, menu1));
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag1"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag2"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag3"));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(1), 2));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(1)
                                  .rate(cartl.getProfesor(3), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(2), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(2)
                                  .rate(cartl.getProfesor(1), 3));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(3), 5));
    assertDoesNotThrow(() -> cartl.getCasaDeBurrito(3)
                                  .rate(cartl.getProfesor(2), 4));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(1), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .addFriend(cartl.getProfesor(2)));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(2), cartl.getProfesor(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(2)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(1)));
    assertDoesNotThrow(() -> cartl.getProfesor(3)
                                  .favorite(cartl.getCasaDeBurrito(3)));
    assertThrows(CasaDeBurritoNotInSystemException.class, () -> cartl
        .getRecommendation(cartl.getProfesor(1), new CasaDeBurritoImpl(15, "none", 5, menu1), 0));
    assertThrows(ProfesorNotInSystemException.class,
        () -> cartl.getRecommendation(new ProfesorImpl(55, "None"), cartl.getCasaDeBurrito(1), 0));
    assertThrows(ImpossibleConnectionException.class,
        () -> cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(1), -1));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(1), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(2), 0)));
    assertDoesNotThrow(() -> assertFalse(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(3), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(1), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(2), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(3), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(1), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(2), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(1), cartl.getCasaDeBurrito(3), 2)));
    assertDoesNotThrow(() -> assertFalse(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(1), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(2), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(3), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(1), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(2), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(3), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(1), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(2), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(2), cartl.getCasaDeBurrito(3), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(1), 0)));
    assertDoesNotThrow(() -> assertFalse(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(2), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(3), 0)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(1), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(2), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(3), 1)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(1), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(2), 2)));
    assertDoesNotThrow(() -> assertTrue(
        cartl.getRecommendation(cartl.getProfesor(3), cartl.getCasaDeBurrito(3), 2)));
  }

  @Test
  void getMostPopularRestaurantsIds()
      throws CasaDeBurritoAlreadyInSystemException, ProfesorAlreadyInSystemException, RateRangeException {
    CasaDeBurrito c1 = cartl.addCasaDeBurrito(1, "Burito1", 0, menu1);
    CasaDeBurrito c2 = cartl.addCasaDeBurrito(2, "Burito2", 0, menu1);
    CasaDeBurrito c3 = cartl.addCasaDeBurrito(3, "Burito3", 0, menu1);
    Profesor p1 = cartl.joinCartel(1, "Zilag1");
    Profesor p2 = cartl.joinCartel(2, "Zilag2");
    Profesor p3 = cartl.joinCartel(3, "Zilag3");
    c1.rate(p1, 3);
    c2.rate(p1, 3);
    c3.rate(p1, 3);
    c1.rate(p2, 2);
    c2.rate(p2, 2);
    assertDoesNotThrow(() -> p1.favorite(c1));
    assertDoesNotThrow(() -> p1.favorite(c2));
    assertDoesNotThrow(() -> p1.favorite(c3));
    assertDoesNotThrow(() -> p2.favorite(c1));
    assertDoesNotThrow(() -> p2.favorite(c2));
    assertDoesNotThrow(() -> cartl.addConnection(p1, p2));
    assertDoesNotThrow(() -> cartl.addConnection(p1, p3));
    assertDoesNotThrow(() -> cartl.addConnection(p2, p3));
    Iterator<Integer> i = cartl.getMostPopularRestaurantsIds()
                               .iterator();
    assertEquals(1, i.next());
    assertEquals(2, i.next());
  }

  @Test
  void toString1() {
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(1, "Burito1", 0, menu1));
    assertDoesNotThrow(() -> cartl.addCasaDeBurrito(2, "Burito2", 0, menu1));
    assertDoesNotThrow(() -> cartl.joinCartel(1, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(2, "Zilag"));
    assertDoesNotThrow(() -> cartl.joinCartel(3, "Zilag"));
    assertDoesNotThrow(() -> cartl.addConnection(cartl.getProfesor(3), cartl.getProfesor(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .addFriend(cartl.getProfesor(2)));
    assertDoesNotThrow(() -> cartl.getProfesor(1)
                                  .addFriend(cartl.getProfesor(3)));
    assertEquals("Registered profesores: 1, 2, 3.\n"
            + "Registered casas de burrito: 1, 2.\n"
            + "Profesores:\n"
            + "1 -> [2, 3].\n"
            + "2 -> [3].\n"
            + "3 -> [2].\n"
            + "End profesores.",
        cartl.toString());
  }
}