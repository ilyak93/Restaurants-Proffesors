package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CartelDeNachosImpl implements CartelDeNachos {

    private Map<Integer, Profesor> profesors;
    private Map<Integer, CasaDeBurrito> restaurants;

    public CartelDeNachosImpl() {
        this.profesors = new HashMap<>();
        this.restaurants = new HashMap<>();
    }

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        ProfesorImpl prof = new ProfesorImpl(id, name);
        if (profesors.containsKey(id))
            throw new Profesor.ProfesorAlreadyInSystemException();

        profesors.put(id, prof);
        return prof;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist,
                                          Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        if (restaurants.containsKey(id))
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        CasaDeBurritoImpl casa = new CasaDeBurritoImpl(id, name, dist, menu);
        restaurants.put(id, casa);
        return casa;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        return new HashSet<>(profesors.values());
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return new HashSet<>(restaurants.values());
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        Profesor prof = profesors.get(id);
        if (prof==null) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        return prof;
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        CasaDeBurrito casa = restaurants.get(id);
        if (casa==null) {
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        return casa;
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException,
                    Profesor.ConnectionAlreadyExistsException,
                    Profesor.SameProfesorException {

        if (!profesors.containsKey(p1.getId()) || !profesors.containsKey(p2.getId()))
            throw new Profesor.ProfesorNotInSystemException();
        else if(p1.getId() == p2.getId())
            throw new Profesor.SameProfesorException();
        else if(p1.getFriends().contains(p2))
            throw new Profesor.ConnectionAlreadyExistsException();
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!profesors.containsKey(p.getId())) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        // we first clean from possible nulls, then sort according to ID, then filter all the empties to be clean.
        // next use profesor's function to get each friend's list, we then use flatmap to merge
        // all the lists, use distinct to remove doubles and lastly we make it back to a list!
        return profesors.get(p.getId()).getFriends().stream()
                //.filter(Objects::nonNull) // not required
                .sorted(Profesor::compareTo)
                //.filter(prof -> !prof.favorites().isEmpty()) //not required
                .flatMap(friend -> friend.favoritesByRating(Integer.MIN_VALUE).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!profesors.containsKey(p.getId())) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        // we first clean from possible nulls, then sort according to ID, then filter all the empties to be clean.
        // next use profesor's function to get each friend's list, we then use flatmap to merge
        // all the lists, use distinct to remove doubles and lastly we make it back to a list!
        return profesors.get(p.getId()).getFriends().stream()
                //.filter(Objects::nonNull)
                .sorted(Profesor::compareTo)
               // .filter(prof -> !prof.favorites().isEmpty())
                .flatMap(friend -> friend.favoritesByDist(Integer.MAX_VALUE).stream())
                .distinct()
                .collect(Collectors.toList());
    }


    private class graphSearchClass {
        private Map<Integer, Boolean> visited;
        private CasaDeBurrito c;
        private graphSearchClass(CasaDeBurrito casa) {
            visited = new HashMap<>();
            c = casa;
            for(int id : profesors.keySet()){
                visited.put(id, false);
            }
        }
        private boolean graphSearch(Profesor p, int t) {
            if(visited.get(p.getId()) == true){
                return false;
            }
            if(p.favorites().contains(c)){
                return true;
            }
            if(t <= 0){
                return false;
            }
            visited.put(p.getId(), true);
            for (Profesor prof : p.getFriends()) {
                if(graphSearch(prof, t-1)) return true;
            }
            return false;
        }
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException,
                    CasaDeBurrito.CasaDeBurritoNotInSystemException,
                    ImpossibleConnectionException {

        if (!profesors.containsKey(p.getId()))
            throw new Profesor.ProfesorNotInSystemException();
        if (!restaurants.containsKey(c.getId()))
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if (t < 0)
            throw new ImpossibleConnectionException();
        // base step for TRUE value.
        graphSearchClass g = new graphSearchClass(c);
        return g.graphSearch(p,t);
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        HashMap<Integer, Integer> best_casas = new HashMap<>();
        for ( Integer key : restaurants.keySet()) {
            best_casas.put(key, 0);
        }
        profesors.values().stream()
                .map(Profesor::getFriends)
                .map( s -> s.stream()
                            .map( f -> f.favorites().stream()
                                                    .map( c -> best_casas.put(
                                                            c.getId(),
                                                            best_casas.get(
                                                                    c.getId()
                                                            ) + 1))));

        Integer max_score = best_casas.size() > 0 ? best_casas.values().stream().max(Integer::compareTo).get() : 0;

        return best_casas.entrySet().stream()
                .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                //       .max((e1, e2) -> e1.getValue() - e2.getValue())
                .filter(e -> e.getValue() >= max_score)
                .map(Entry::getKey)
                .collect(Collectors.toList());
//        Stream<Map.Entry<K,V>> sorted =
//                map.entrySet().stream()
//                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    }

    @Override
    public String toString() {

        String profesores = profesors.keySet().stream()
                //.filter(Objects::nonNull)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String casas = restaurants.keySet().stream()
                //.filter(Objects::nonNull)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String friends = profesors.values().stream()
                //.filter(Objects::nonNull)
                .sorted()
                .map(p -> p.getId() + " -> [" + p.getFriends().stream()
                        //.filter(Objects::nonNull)
                        .sorted()
                        .map(Profesor::getId)
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")) + "].\n")
                .collect(Collectors.joining());

        return "Registered profesores: " + profesores + ".\n" +
                "Registered casas de burrito: " + casas + ".\n" +
                "Profesores:\n" + friends + "End profesores.";
    }

}
