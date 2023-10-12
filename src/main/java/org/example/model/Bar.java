package org.example.model;

public class Bar {
    int id;
    double weight;

    public Bar(int id, double weight){
        this.id = id;
        this.weight = weight;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public double getWeight(){
        return this.weight;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
}
