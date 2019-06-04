package network.model;

import java.io.Serializable;

public class ComparatorSet implements Serializable {

    private int X;
    private int Y;


    public void setComparator(ComparatorSet comparatorSet){
        this.X = comparatorSet.getX();
        this.Y = comparatorSet.getY();
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

    public ComparatorSet(){

        this.setX(0);
        this.setY(0);
    }

    public boolean Equals(ComparatorSet x){
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
