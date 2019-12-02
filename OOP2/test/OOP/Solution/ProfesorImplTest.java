package OOP.Solution;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.ConnectionAlreadyExistsException;
import OOP.Provided.Profesor.SameProfesorException;
import OOP.Provided.Profesor.UnratedFavoriteCasaDeBurritoException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfesorImplTest {

  private Profesor p1;
  private Profesor p2;
  private CasaDeBurrito cas1;
  private CasaDeBurrito cas2;
  private CasaDeBurrito cas3;
  private CasaDeBurrito cas4;
  private CasaDeBurrito cas5;

  @BeforeEach
  private void initParams() {
    p1 = new ProfesorImpl(1, "Zilag");
    p2 = new ProfesorImpl(2, "Censor");
    Set<String> menu1 = new HashSet<>();
    Set<String> menu2 = new HashSet<>();
    menu1.add("Hamburger");
    menu1.add("Fries");
    menu2.add("Steak");
    menu2.add("Fries");
    menu2.add("Orange Juice");
    cas1 = new CasaDeBurritoImpl(1, "MyCas111", 1, menu1);
    cas2 = new CasaDeBurritoImpl(2, "MyCas22", 2, menu2);
    cas3 = new CasaDeBurritoImpl(3, "MyCas3", 3, menu1);
    cas4 = new CasaDeBurritoImpl(4, "MyCas4", 3, menu1);
    cas5 = new CasaDeBurritoImpl(4, "Buritos", 3, menu1);
  }

  @Test
  void getId() {
    assertEquals(1, p1.getId());
    assertEquals(2, p2.getId());
    assertNotEquals(p1.getId(), p2.getId());

  }

  @Test
  void favorite() {
    assertThrows(UnratedFavoriteCasaDeBurritoException.class, () -> p1.favorite(cas1));
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas1));
    assertDoesNotThrow(() -> cas2.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas1)
                               .favorite(cas2));
  }

  @Test
  void favorites() {
    assertEquals(0, p1.favorites()
                      .size());
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas1));
    assertEquals(1, p1.favorites()
                      .size());
    assertDoesNotThrow(() -> cas2.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas2));
    assertEquals(2, p1.favorites()
                      .size());
    {
      //Checking that we are getting a copy of the favorites
      Set<CasaDeBurrito> s = new HashSet<>(p1.favorites());
      s.clear();
      Set<CasaDeBurrito> s1 = new HashSet<>(p1.favorites());
      assertEquals(2, s1.size());
    }
  }

  @Test
  void addFriend() {
    assertThrows(SameProfesorException.class, () -> p1.addFriend(p1));
    assertDoesNotThrow(() -> p1.addFriend(p2));
    assertThrows(ConnectionAlreadyExistsException.class, () -> p1.addFriend(p2));
    assertDoesNotThrow(() -> p2.addFriend(p1));
    assertThrows(ConnectionAlreadyExistsException.class, () -> p2.addFriend(p1));
  }

  @Test
  void getFriends() {
    assertEquals(0, p1.getFriends()
                      .size());
    assertDoesNotThrow(() -> p1.addFriend(p2));
    assertEquals(1, p1.getFriends()
                      .size());
    assertTrue(p1.getFriends()
                 .contains(p2));
  }

  @Test
  void filteredFriends() {
    assertDoesNotThrow(() -> p1.addFriend(p2));
    assertTrue(p1.getFriends()
                 .contains(p2));
    assertFalse(p1.filteredFriends(professor -> professor.getId() > 3)
                  .contains(p2));
    assertTrue(p1.filteredFriends(professor -> professor.getId() > 1)
                 .contains(p2));
  }

  @Test
  void filterAndSortFavorites() {
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> cas2.rate(p1, 4));
    assertDoesNotThrow(() -> cas3.rate(p1, 5));
    assertEquals(0, p1.filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                                            .length()),
        p -> p.getId() > 0)
                      .size());
    assertDoesNotThrow(() -> p1.favorite(cas1));
    assertEquals(1, p1.filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                                            .length()),
        p -> p.getId() > 0)
                      .size());
    assertDoesNotThrow(() -> p1.favorite(cas2));
    assertEquals(2, p1.filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                                            .length()),
        p -> p.getId() > 0)
                      .size());
    assertDoesNotThrow(() -> p1.favorite(cas3));
    assertEquals(0, p1.filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                                            .length()),
        p -> p.getId() > 3)
                      .size());
    assertEquals(1, p1.filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                                            .length()),
        p -> p.getId() > 2)
                      .size());
    Iterator<CasaDeBurrito> iter = p1
        .filterAndSortFavorites(Comparator.comparingInt(c -> c.getName()
                                                              .length()),
            p -> p.getId() > 0d)
        .iterator();
    assertEquals(iter.next(), cas3);
    assertEquals(iter.next(), cas2);
    assertEquals(iter.next(), cas1);
  }

  @Test
  void favoritesByRating() {
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> cas2.rate(p1, 4));
    assertDoesNotThrow(() -> cas3.rate(p1, 5));
    assertDoesNotThrow(() -> p1.favorite(cas1));
    assertDoesNotThrow(() -> p1.favorite(cas2));
    assertDoesNotThrow(() -> p1.favorite(cas3));
    Iterator<CasaDeBurrito> iter = p1.favoritesByRating(4)
                                     .iterator();
    assertEquals(cas3, iter.next());
    assertEquals(cas2, iter.next());
    iter = p1.favoritesByRating(5)
             .iterator();
    assertEquals(cas3, iter.next());
    assertEquals(3, p1.favoritesByRating(0)
                      .size());
    assertEquals(0, p1.favoritesByRating(6)
                      .size());
    assertDoesNotThrow(() -> cas3.rate(p1, 5));
    //check 2nd
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> cas2.rate(p1, 3));
    assertDoesNotThrow(() -> cas3.rate(p1, 3));
    iter = p1.favoritesByRating(0)
             .iterator();
    assertEquals(cas1, iter.next());
    assertEquals(cas2, iter.next());
    assertEquals(cas3, iter.next());

    //check 3rd ordering
    assertDoesNotThrow(() -> cas1.rate(p1, 2));
    assertDoesNotThrow(() -> cas2.rate(p1, 2));
    assertDoesNotThrow(() -> cas3.rate(p1, 3));
    assertDoesNotThrow(() -> cas4.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas4));
    iter = p1.favoritesByRating(3)
             .iterator();
    assertEquals(cas3, iter.next());
    assertEquals(cas4, iter.next());
  }

  @Test
  void favoritesByDist() {
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> cas2.rate(p1, 4));
    assertDoesNotThrow(() -> cas3.rate(p1, 5));
    assertDoesNotThrow(() -> p1.favorite(cas1));
    assertDoesNotThrow(() -> p1.favorite(cas2));
    assertDoesNotThrow(() -> p1.favorite(cas3));
    Iterator<CasaDeBurrito> iter = p1.favoritesByDist(5)
                                     .iterator();
    assertEquals(cas1, iter.next());
    assertEquals(cas2, iter.next());
    iter = p1.favoritesByDist(1)
             .iterator();
    assertEquals(cas1, iter.next());
    assertEquals(0, p1.favoritesByDist(0)
                      .size());
    assertEquals(3, p1.favoritesByDist(6)
                      .size());
    assertDoesNotThrow(() -> cas3.rate(p1, 5));
    //check 2nd
    assertDoesNotThrow(() -> cas4.rate(p1, 5));
    assertDoesNotThrow(() -> p1.favorite(cas4));
    assertDoesNotThrow(() -> cas3.rate(p1, 3));
    iter = p1.favoritesByDist(6)
             .iterator();
    assertEquals(cas1, iter.next());
    assertEquals(cas2, iter.next());
    assertEquals(cas4, iter.next());
    assertEquals(cas3, iter.next());

    //check 3rd ordering
    assertDoesNotThrow(() -> cas1.rate(p1, 1));
    assertDoesNotThrow(() -> cas2.rate(p1, 2));
    assertDoesNotThrow(() -> cas3.rate(p1, 3));
    assertDoesNotThrow(() -> cas4.rate(p1, 3));
    iter = p1.favoritesByDist(3)
             .iterator();
    assertEquals(cas1, iter.next());
    assertEquals(cas2, iter.next());
    assertEquals(cas3, iter.next());
    assertEquals(cas4, iter.next());
  }

  @Test
  void compareTo() {
    assertEquals(0, p1.compareTo(p1));
    assertEquals(1, p2.compareTo(p1));
    assertEquals(-1, p1.compareTo(p2));
  }

  @Test
  void toString1() {
    assertDoesNotThrow(() -> cas1.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas1));
    System.out.println(p1.toString());
    assertEquals(0, "Profesor: Zilag.\nId: 1.\nFavorites: MyCas111.".compareTo(p1.toString()));
    assertDoesNotThrow(() -> cas2.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas2));
    assertEquals(0,
        "Profesor: Zilag.\nId: 1.\nFavorites: MyCas111, MyCas22.".compareTo(p1.toString()));
    assertDoesNotThrow(() -> cas5.rate(p1, 3));
    assertDoesNotThrow(() -> p1.favorite(cas5));
    assertEquals(0,
        "Profesor: Zilag.\nId: 1.\nFavorites: Buritos, MyCas111, MyCas22.".compareTo(
            p1.toString()));
  }

//  private void initParams() {
//    p1 = new ProfesorImpl(1, "Zilag");
//    p2 = new ProfesorImpl(2, "Censor");
//    Set<String> menu1 = new HashSet<>();
//    Set<String> menu2 = new HashSet<>();
//    menu1.add("Hamburger");
//    menu1.add("Fries");
//    menu2.add("Steak");
//    menu2.add("Fries");
//    menu2.add("Orange Juice");
//    cas1 = new CasaDeBurritoImpl(1, "MyCas111", 1, menu1);
//    cas2 = new CasaDeBurritoImpl(2, "MyCas22", 2, menu2);
//    cas3 = new CasaDeBurritoImpl(3, "MyCas3", 3, menu1);
//    cas4 = new CasaDeBurritoImpl(4, "MyCas4", 3, menu1);
//    cas5 = new CasaDeBurritoImpl(4, "Buritos", 3, menu1);
//  }


  @Test
  void equals1() {
    assertNotEquals(1, p1);
    assertNotEquals(p1, p2);
    assertEquals(p1, p1);
  }
}