package sample.model;

import java.util.List;

public class RelationsBuilder {
    public static void buildRelationsWithAll(List<Person> people) {
        for (int i = 0; i < people.size(); i++) {
            for (int j = i+1; j < people.size(); j++) {
                buildRelationsWithTwo(people.get(i), people.get(j));
            }
        }
    }
    private static void buildRelationsWithTwo (Person a, Person b) {
        int pairPotential = 0;
        for (String q : a.getDemands())
            if (b.getQualities().contains(q))
                pairPotential++;
        for (String q : a.getQualities())
            if (b.getDemands().contains(q))
                pairPotential++;

        a.getPersonRespects().add(new PersonRespect(b, pairPotential));
        if (a.getMaxRespect() < pairPotential) {
            a.setMaxRespect(pairPotential);
            a.setBestPair(b);
        }
        b.getPersonRespects().add(new PersonRespect(a, pairPotential));
        if (b.getMaxRespect() < pairPotential) {
            b.setMaxRespect(pairPotential);
            b.setBestPair(a);
        }
    }
}
