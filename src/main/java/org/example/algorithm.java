package org.example;

import org.example.model.Bar;
import java.util.ArrayList;


public class algorithm {

    public int sortAlgorithm(ArrayList<Bar> array){
        ArrayList<ArrayList<Bar>> sets = createSets(array);
        int result = 0;

        ArrayList<Bar> set1 = sets.get(0);
        ArrayList<Bar> set2 = sets.get(1);
        ArrayList<Bar> set3 = sets.get(2);

        double totalWeight1 = getTotalWeight(set1);
        double totalWeight2 = getTotalWeight(set2);

        if (totalWeight1 > totalWeight2){
                result = compareSet(set2).getId();
        }
        if (totalWeight1 < totalWeight2){
            result = compareSet(set1).getId();
        }
        if (totalWeight1 == totalWeight2){
            result = compareSet(set3).getId();
        }
        return result;
    }
    ArrayList <ArrayList<Bar>> createSets(ArrayList<Bar> array){
        ArrayList<Bar> set1 = new ArrayList<>();
        ArrayList<Bar> set2 = new ArrayList<>();
        ArrayList<Bar> set3 = new ArrayList<>();
        int setSize = array.size() / 3;

        for (int i = 0; i < array.size(); i++) {
            if (i < setSize) {
                set1.add(array.get(i));
            } else if (i < 2 * setSize) {
                set2.add(array.get(i));
            } else {
                set3.add(array.get(i));
            }
        }
        ArrayList<ArrayList<Bar>> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);

        return sets;
    }

    double getTotalWeight(ArrayList<Bar> set){
        double weight = 0;
        for (Bar bar : set) {
            weight += bar.getWeight();
        }
        return weight;
    }
    Bar compareSet(ArrayList<Bar> set){
        Bar smallest;
        if (set.get(0).getWeight() > set.get(1).getWeight()){
            smallest = set.get(1);
        } else if (set.get(0).getWeight() < set.get(1).getWeight()) {
            smallest = set.get(0);
        } else {
            smallest = set.get(2);
        }
        return smallest;
    }
}
