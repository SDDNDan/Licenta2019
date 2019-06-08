package algorithm.model;

import network.SortingNetwork;

import java.io.Serializable;

public class Individual implements Serializable {

    private Float fitness;
    private SortingNetwork sortingNetwork;

    public Float getFitness() {
        return fitness;
    }

    public void setFitness(Float fitness) {
        this.fitness = fitness;
    }

    public SortingNetwork getSortingNetwork() {
        return sortingNetwork;
    }

    @Override
    public String toString() {
        return this.sortingNetwork.getComparators().toString();
    }

    public void setSortingNetwork(SortingNetwork sortingNetwork) {
        this.sortingNetwork = sortingNetwork;
    }

    public Individual(SortingNetwork sortingNetwork) {
        this.fitness = 0.0f;
        this.sortingNetwork = sortingNetwork;
    }




}
