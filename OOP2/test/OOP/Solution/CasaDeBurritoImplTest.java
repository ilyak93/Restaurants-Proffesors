package OOP.Solution;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CasaDeBurrito.RateRangeException;
import OOP.Provided.Profesor;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CasaDeBurritoImplTest {

  private CasaDeBurrito cas1;
  private CasaDeBurrito cas2;

  @BeforeEach
  private void initParams() {
    Set<String> menu1 = new HashSet<>();
    Set<String> menu2 = new HashSet<>();
    menu1.add("Hamburger");
    menu1.add("Fries");
    menu2.add("Steak");
    menu2.add("Fries");
    menu2.add("Orange Juice");
    cas1 = new CasaDeBurritoImpl(1, "MyCas1", 1, menu1);
    cas2 = new CasaDeBurritoImpl(2, "MyCas2", 2, menu2);
  }

  @Test
  void compareTo() {
  }

  @Test
  void getId() {
    assertEquals(1, cas1.getId());
    assertEquals(2, cas2.getId());
  }

  @Test
  void getName() {
    assertEquals("MyCas1", cas1.getName());
    assertEquals("MyCas2", cas2.getName());
  }

  @Test
  void distance() {
    assertEquals(1, cas1.distance());
    assertEquals(2, cas2.distance());
  }

  @Test
  void rate() {
    assertThrows(RateRangeException.class, () -> cas1.rate(new ProfesorImpl(1, "Zilag"), -1));
    assertThrows(RateRangeException.class, () -> cas1.rate(new ProfesorImpl(1, "Zilag"), 6));
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(1, "Zilag"), 0));
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(1, "Zilag"), 5));


  }

  @Test
  void isRatedBy() {
    Profesor p1 = new ProfesorImpl(1, "Zilag");
    Profesor p2 = new ProfesorImpl(2, "Censor");
    assertDoesNotThrow(() -> cas1.rate(p1, 1));
    assertEquals(true, cas1.isRatedBy(p1));
    assertNotEquals(true, cas1.isRatedBy(p2));
    assertDoesNotThrow(() -> cas1.rate(p2, 1));
    assertEquals(true, cas1.isRatedBy(p2));
  }


  @Test
  void numberOfRates() {
    assertEquals(0, cas1.numberOfRates());
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(1, "Zilag"), 3));
    assertEquals(1, cas1.numberOfRates());
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(2, "Zilag"), 3));
    assertEquals(2, cas1.numberOfRates());
  }

  @Test
  void averageRating() {
    assertEquals(0.0, cas1.averageRating());
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(1, "Zilag"), 3));
    assertEquals(3.0, cas1.averageRating());
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(2, "Zilag"), 3));
    assertEquals(3.0, cas1.averageRating());
    assertDoesNotThrow(() -> cas1.rate(new ProfesorImpl(3, "Zilag"), 0));
    assertEquals(2.0, cas1.averageRating());
  }

  @Test
  void toString1() {
    //CasaDeBurrito: <name>. Id: <id>. Distance: <dist>. Menu: <menuItem1, menuItem2, menuItem3...>.
    assertEquals("CasaDeBurrito: MyCas1.\nId: 1.\nDistance: 1.\nMenu: Fries, Hamburger.\n",
        cas1.toString());
    assertEquals("CasaDeBurrito: MyCas2.\nId: 2.\nDistance: 2.\nMenu: Fries, Orange Juice, Steak.\n",
        cas2.toString());
  }

  @Test
  void equals1() {
    assertNotEquals(5, cas1);
    assertEquals(cas1, cas1);
    assertNotEquals(cas2, cas1);
    assertNotEquals(cas1, cas2);
  }
}