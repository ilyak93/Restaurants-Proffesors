package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CasaDeBurritoImpl implements CasaDeBurrito {

    private final int id;
    private final int dst;
    private final String name;
    private final Set<String> menu;
    private final Map<Integer, Integer> burrito_rates;

    public CasaDeBurritoImpl(CasaDeBurritoImpl c) {
        this.id = c.id;
        this.name = c.name;
        this.dst = c.dst;
        this.menu = new HashSet<>(c.menu);
        this.burrito_rates = new HashMap<>(c.burrito_rates);
    }

    public CasaDeBurritoImpl(int id, String name, int dst, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dst = dst;
        this.menu = new HashSet<>(menu);
        this.burrito_rates = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int distance() {
        return dst;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return burrito_rates.containsKey(p.getId());
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if ( r < 0 || r > 5) {
            throw new RateRangeException();
        }
        burrito_rates.put(p.getId(), r);
        return this;
    }

    @Override
    public int numberOfRates() {
        return burrito_rates.size();
    }

    @Override
    public double averageRating() {
        return burrito_rates.isEmpty() ? 0.0 : burrito_rates.values().stream().reduce(0, Integer::sum) / (double)numberOfRates();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CasaDeBurrito)) {
            return false;
        }
        CasaDeBurrito casa = (CasaDeBurrito) obj;
        return this.getId() == casa.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
        return getId() - o.getId();
    }

    @Override
    public String toString() {

        String menu_lexicordered = this.menu.stream().sorted().reduce((s1, s2) -> s1+", "+s2).orElse("");

        return "CasaDeBurrito: " + getName() +".\n" +
                "Id: " + getId() + ".\n" +
                "Distance: " + distance() + ".\n" +
                "Menu: " + menu_lexicordered +".";
    }
}
