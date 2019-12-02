package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {

    private final int id;
    private final String name;
    private final Set<Profesor> friends;
    private final Collection<CasaDeBurrito> favorites;

    public ProfesorImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.friends = new HashSet<>();
        this.favorites = new HashSet<>();
    }

    public ProfesorImpl(ProfesorImpl p) {
        this.id = p.getId();
        this.name = p.name;
        this.favorites = p.favorites();
        this.friends = p.getFriends();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if (!c.isRatedBy(this))
            throw new UnratedFavoriteCasaDeBurritoException();
        favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return new HashSet<>(favorites);
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if (this.equals(p))
            throw new SameProfesorException();
        if (friends.contains(p))
            throw new ConnectionAlreadyExistsException();

        friends.add(p);
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return new HashSet<>(friends);
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return getFriends().stream().filter(p).collect(Collectors.toSet());
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return favorites().stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int r) {
        Comparator<CasaDeBurrito> rating_comp = Comparator
                                    .comparing(CasaDeBurrito::averageRating,
                                                Comparator.reverseOrder())
                                    .thenComparing(CasaDeBurrito::distance)
                                    .thenComparing(CasaDeBurrito::getId);
        return  favorites().stream()
                           .filter(casa -> casa.averageRating() >= r)
                           .sorted(rating_comp).collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int d) {
        Comparator<CasaDeBurrito> dist_comp = Comparator
                .comparing(CasaDeBurrito::distance)
                .thenComparing(CasaDeBurrito::averageRating, Comparator.reverseOrder())
                .thenComparing(CasaDeBurrito::getId);

        return  favorites().stream()
                .filter(casa -> casa.distance() <= d)
                .sorted(dist_comp).collect(Collectors.toList());
    }

    @Override
    public int compareTo(Profesor o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if ( !(o instanceof Profesor) )
            return false;
        Profesor p = (Profesor) o;
        return p.getId() == this.id;
    }

    @Override
    public String toString() {
        return "Profesor: " + name + ".\n" +
                "Id: " + getId() + ".\n" +
                "Favorites: " + favorites().stream()
                                            .map(CasaDeBurrito::getName)
                                            .sorted()
                                            .collect(Collectors.joining(", ")) + ".";
    }

}
