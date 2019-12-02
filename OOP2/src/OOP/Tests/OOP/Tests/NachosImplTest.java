package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CasaDeBurrito.CasaDeBurritoNotInSystemException;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class NachosImplTest {
    public static final int COLLECTION_SIZE = 14;
    static CartelDeNachos Cartel;
    static Set<String> menu;


    @BeforeEach
    void setUp() {

        menu = new HashSet<String>();
        Cartel = new CartelDeNachosImpl();

        HashSet<Profesor> prof = (HashSet<Profesor>) Cartel.registeredProfesores();
        assertEquals(prof.size(), 0);

        HashSet<CasaDeBurrito> cas = (HashSet<CasaDeBurrito>) Cartel.registeredCasasDeBurrito();
        assertEquals(cas.size(), 0);

        for (int i = 1; i <= COLLECTION_SIZE; i++) {
            int j = i;
            if (i > 10) {
                j = i - 10;
            }
            int finalI = i;
            int finalJ = j;
            assertDoesNotThrow(() -> Cartel.addCasaDeBurrito(finalI, "name", finalJ, menu));
        }
     //   System.out.println(Cartel.toString());

        for (int i = 1; i <= COLLECTION_SIZE; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> Cartel.joinCartel(finalI, "name"));
        }
        assertThrows(CasaDeBurrito.CasaDeBurritoAlreadyInSystemException.class, () -> {
            Cartel.addCasaDeBurrito(1, "B2", 3, menu);
        });
        assertThrows(Profesor.ProfesorAlreadyInSystemException.class, () -> Cartel.joinCartel(1, "try"));
    }


    @Test
    void registeredProfesores() {
        HashSet<Profesor> prof = (HashSet<Profesor>) Cartel.registeredProfesores();
        assertEquals(prof.size(), COLLECTION_SIZE);
        for (Profesor p : prof) {
            assertDoesNotThrow(() -> {
                Profesor temp = Cartel.getProfesor(p.getId());
                assertEquals(p, temp);
            });
        }
    }

    @Test
    void registeredCasasDeBurrito() {
        HashSet<CasaDeBurrito> cas;
        cas = (HashSet<CasaDeBurrito>) Cartel.registeredCasasDeBurrito();
        assertEquals(cas.size(), COLLECTION_SIZE);
        for (CasaDeBurrito c : cas) {
            assertDoesNotThrow(() -> {
                CasaDeBurrito temp = Cartel.getCasaDeBurrito(c.getId());
                assertEquals(c, temp);
            });
        }
    }


    @Test
    void addConnection() {
        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(1)
                , Cartel.getProfesor(2)));
        assertThrows(Profesor.ProfesorNotInSystemException.class, () -> {
            Cartel.addConnection(Cartel.getProfesor(70), Cartel.getProfesor(2));
        });
        assertThrows(Profesor.ConnectionAlreadyExistsException.class, () -> {
            Cartel.addConnection(Cartel.getProfesor(1), Cartel.getProfesor(2));
        });
        assertThrows(Profesor.SameProfesorException.class, () -> {
            Cartel.addConnection(Cartel.getProfesor(1), Cartel.getProfesor(1));
        });
    }

    void allFriende() {
        //make all the profesors friends
        for (int i = 1; i <= COLLECTION_SIZE; i++) {
            for (int j = COLLECTION_SIZE; j > i; j--) {
                int finalI = i;
                int finalJ = j;
                assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(finalI),
                        Cartel.getProfesor(finalJ)));
            }
        }
    }

    @Test
    void favoritesByDist() {
        allFriende();
        //make all the profesors friends
//        System.out.println(Cartel.toString());
        for (int i = 2; i < COLLECTION_SIZE; i++) {
            int temp = i;
            if (i > 10) temp = i - 10;

            int finalI = i;
            int finalTemp = temp;
//            try {
//                Profesor p = Cartel.getProfesor(finalI);
//                System.out.println(p.toString());
//                System.out.println(Cartel.getCasaDeBurrito(finalTemp).rate(p, 3).toString());
//                System.out.println(Cartel.getCasaDeBurrito(finalTemp+10).rate(p, 3).toString());
//            } catch (Exception e) {}
            if (COLLECTION_SIZE >= finalTemp + 10) {
                assertDoesNotThrow(() -> {
                    Profesor p = Cartel.getProfesor(finalI);
                    Cartel.getCasaDeBurrito(finalTemp).rate(p, 3);
                    Cartel.getCasaDeBurrito(finalTemp + 10).rate(p, 3);
                    p.favorite(Cartel.getCasaDeBurrito(finalTemp));
                    p.favorite(Cartel.getCasaDeBurrito(finalTemp + 10));
                });
            }




        }
        assertDoesNotThrow(() -> {
            ArrayList<CasaDeBurrito> favor = (ArrayList<CasaDeBurrito>) Cartel.favoritesByDist(Cartel.getProfesor(1));
            Iterator<CasaDeBurrito> it = favor.iterator();
//            for(CasaDeBurrito c : favor) {
//                System.out.println(c.getId() + " " + c.distance());
//            }
           // System.out.println(Cartel.toString());
          //  favor.forEach(val -> System.out.println(val.getId()));
           // if (favor.isEmpty()) System.out.println("helaladfa\n");
        //    favor.stream().map(CasaDeBurrito::getId).map(System.out::println);
//            try {
//                System.out.println(Cartel.toString());
//                System.out.println(Cartel.getCasaDeBurrito(finalK).rate(p, 3).toString());
//                System.out.println(Cartel.getCasaDeBurrito(finalTemp+10).rate(p, 3).toString());
//            } catch (Exception e) {}
            for (int k = 2; k <= 3; k++) {
                int finalK = k;
//                System.out.println(Cartel.getCasaDeBurrito(finalK).getId() + " " + (it.next().getId())
//                    + " " + (it.next().getId()));
                if (COLLECTION_SIZE >= finalK + 10) {
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalK));
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalK + 10));
                }


            }
        });

        for (int i = 2; i < COLLECTION_SIZE; i++) {
            int temp = i;
            if (i > 10) temp = i - 10;

            int finalI = i;
            int finalTemp = temp;
            if (finalTemp + 10 <= COLLECTION_SIZE) {
                assertDoesNotThrow(() -> {
                    Profesor p = Cartel.getProfesor(finalI);
                    Cartel.getCasaDeBurrito(finalTemp).rate(p, 3);
                    Cartel.getCasaDeBurrito(finalTemp + 10).rate(p, 5);
                });
            }

        }

        assertDoesNotThrow(() -> {
            ArrayList<CasaDeBurrito> favor = (ArrayList<CasaDeBurrito>) Cartel.favoritesByDist(Cartel.getProfesor(1));
            Iterator<CasaDeBurrito> it = favor.iterator();
            for (int k = 2; k <= 3; k++) {
                int finalK = k;
                if (finalK + 10 <= COLLECTION_SIZE) {
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalK + 10));
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalK));
                }

            }
        });


        // all the profesors love all the boritos rate 3 for all
        for (int i = 1; i < COLLECTION_SIZE; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> {
                Profesor p = Cartel.getProfesor(finalI);
                for(int j = 1; j <= COLLECTION_SIZE; j++){
                    Cartel.getCasaDeBurrito(j).rate(p, 3);
                    p.favorite(Cartel.getCasaDeBurrito(j));
                }

            });
        }

        assertDoesNotThrow(() -> {
            ArrayList<CasaDeBurrito> favor = (ArrayList<CasaDeBurrito>) Cartel.favoritesByDist(Cartel.getProfesor(1));
            Iterator<CasaDeBurrito> it = favor.iterator();

            for (int i = 1; i <= 10; i++) {
                int finalI = i;
                if (finalI + 10 <= COLLECTION_SIZE) {
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalI));
                    assertEquals(it.next(), Cartel.getCasaDeBurrito(finalI + 10));
                }

            }
        });

    }

    @Test
    void getRecommendation() {
        Profesor p = new ProfesorImpl(COLLECTION_SIZE+1,"shahar");
        CasaDeBurrito c = new CasaDeBurritoImpl(COLLECTION_SIZE+1,"vivino",1,menu);
        assertThrows(Profesor.ProfesorNotInSystemException.class,
                () -> Cartel.getRecommendation(p, Cartel.getCasaDeBurrito(1), 1));
        assertThrows(CasaDeBurrito.CasaDeBurritoNotInSystemException.class,
                () -> Cartel.getRecommendation(Cartel.getProfesor(1), c, 1));
        assertThrows(CartelDeNachos.ImpossibleConnectionException.class,
                () -> Cartel.getRecommendation(Cartel.getProfesor(1), Cartel.getCasaDeBurrito(1), -1));

        assertDoesNotThrow(() -> {
            Profesor p1 = Cartel.getProfesor(1);
            Profesor p2 = Cartel.getProfesor(2);
            Cartel.addConnection(p1, p2);
            CasaDeBurrito c_new = Cartel.addCasaDeBurrito(COLLECTION_SIZE+1,"vivino",1,menu);
            assertEquals(Cartel.getRecommendation(p1,c_new, COLLECTION_SIZE),false);
            assertEquals(Cartel.getRecommendation(p1,c_new, Integer.MAX_VALUE),false);
////            System.out.println(p1.toString());
//            for (Profesor p0 : p1.getFriends()) {
//                System.out.println(p0.toString());
//            }

//            CasaDeBurrito c1 = Cartel.getCasaDeBurrito(1);
//            assertEquals(Cartel.getRecommendation(p1,c1, COLLECTION_SIZE),false);
//
//            c1.rate(p1,5);
//            p1.favorite(c1);
//            assertEquals(Cartel.getRecommendation(p1,c1,0),true);
//            assertEquals(Cartel.getRecommendation(p1,c1, COLLECTION_SIZE),true);
//            allFriende(); //make all the profesors friends
//            CasaDeBurrito c_new = Cartel.addCasaDeBurrito(COLLECTION_SIZE+1,"vivino",1,menu);
//            Profesor p20 = Cartel.getProfesor(COLLECTION_SIZE);
//            assertEquals(Cartel.getRecommendation(p20,c_new, COLLECTION_SIZE),false);
//            assertEquals(Cartel.getRecommendation(p20,c_new, Integer.MAX_VALUE),false);
//            assertEquals(Cartel.getRecommendation(p20,c1,COLLECTION_SIZE+1),true);


        });
    }





    @Test
    void getMostPopularRestaurantsIds() {
        allFriende();
        ArrayList<Integer> MostPopu = (ArrayList<Integer>) Cartel.getMostPopularRestaurantsIds();
        Iterator<Integer> it = MostPopu.iterator();
        for(int i = 1; i <= COLLECTION_SIZE; i++) assertEquals((int)it.next(), i);
        for(int i = 1; i<= COLLECTION_SIZE; i++){
            int finalI = i;
            assertDoesNotThrow(() -> {
                Profesor p = Cartel.getProfesor(finalI);
                CasaDeBurrito c = Cartel.getCasaDeBurrito(finalI);
                c.rate(p,5);
                p.favorite(c);
            });
        }
        MostPopu = (ArrayList<Integer>) Cartel.getMostPopularRestaurantsIds();
        it = MostPopu.iterator();
        for(int i = 1; i <= COLLECTION_SIZE; i++) {
            int temp = it.next();
            assertEquals(temp, i);
        }
    }

    @Test
    void toStringTest() {

        Cartel = new CartelDeNachosImpl();

        assertEquals(Cartel.toString(),
                "Registered profesores: .\n" +
                        "Registered casas de burrito: .\n" +
                        "Profesores:\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.joinCartel(236703, "name"));

        assertEquals(Cartel.toString(),
                "Registered profesores: 236703.\n" +
                        "Registered casas de burrito: .\n" +
                        "Profesores:\n" +
                        "236703 -> [].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.joinCartel(1, "name"));
        assertDoesNotThrow(() -> Cartel.joinCartel(555555, "name"));

        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: .\n" +
                        "Profesores:\n" +
                        "1 -> [].\n" +
                        "236703 -> [].\n" +
                        "555555 -> [].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(1), Cartel.getProfesor(555555)));

        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: .\n" +
                        "Profesores:\n" +
                        "1 -> [555555].\n" +
                        "236703 -> [].\n" +
                        "555555 -> [1].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(236703), Cartel.getProfesor(1)));

        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: .\n" +
                        "Profesores:\n" +
                        "1 -> [236703, 555555].\n" +
                        "236703 -> [1].\n" +
                        "555555 -> [1].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.addCasaDeBurrito(13, "name", 1, menu));
        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: 13.\n" +
                        "Profesores:\n" +
                        "1 -> [236703, 555555].\n" +
                        "236703 -> [1].\n" +
                        "555555 -> [1].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.addCasaDeBurrito(12, "name", 12, menu));
        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: 12, 13.\n" +
                        "Profesores:\n" +
                        "1 -> [236703, 555555].\n" +
                        "236703 -> [1].\n" +
                        "555555 -> [1].\n" +
                        "End profesores.");

        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(236703), Cartel.getProfesor(555555)));
        assertEquals(Cartel.toString(),
                "Registered profesores: 1, 236703, 555555.\n" +
                        "Registered casas de burrito: 12, 13.\n" +
                        "Profesores:\n" +
                        "1 -> [236703, 555555].\n" +
                        "236703 -> [1, 555555].\n" +
                        "555555 -> [1, 236703].\n" +
                        "End profesores.");
        assertDoesNotThrow(() -> Cartel.joinCartel(-4, "name"));
        assertDoesNotThrow(() -> Cartel.joinCartel(-20, "name"));
        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(-4), Cartel.getProfesor(-20)));
        assertDoesNotThrow(() -> Cartel.addConnection(Cartel.getProfesor(1), Cartel.getProfesor(-20)));
        assertEquals(Cartel.toString(),
                "Registered profesores: -20, -4, 1, 236703, 555555.\n" +
                        "Registered casas de burrito: 12, 13.\n" +
                        "Profesores:\n" +
                        "-20 -> [-4, 1].\n" +
                        "-4 -> [-20].\n" +
                        "1 -> [-20, 236703, 555555].\n" +
                        "236703 -> [1, 555555].\n" +
                        "555555 -> [1, 236703].\n" +
                        "End profesores.");

    }
//     * Registered profesores: 1, 236703, 555555.
//            * Registered casas de burrito: 12, 13.
//            * Profesores:
//            * 1 -> [236703, 555555555].
//            * 236703 -> [1].
//            * 555555 -> [1].
//            * End profesores.

}