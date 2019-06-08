package network.model;

import java.io.Serializable;

public class Comparator implements Serializable {

    private int X;
    private int Y;


    public void setComparator(Comparator comparator){
        this.X = comparator.getX();
        this.Y = comparator.getY();
    }
    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public Comparator(){

        this.setX(0);
        this.setY(0);
    }

    public boolean Equals(Comparator x){
        if(this.getX() == x.getX() && this.getY() == x.getY()){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "(" + this.X + ", "+ this.Y + ") ";
    }

}
