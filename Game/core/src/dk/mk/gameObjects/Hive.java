package dk.mk.gameObjects;

import java.util.ArrayList;

public class Hive extends GameObject {

    ArrayList<Bee> ownedBees;
    ArrayList<Queen> ownedQueens;

    public Hive() {
        super("hive.png");

        ownedBees = new ArrayList<Bee>();
        ownedQueens = new ArrayList<>();
    }

    public int numberOfOwnedBees(){
        return ownedBees.size();
    }

    public int numberOfOwnedQueens(){ return ownedQueens.size();}

    /** Returns half of the owned bees, they will be removed from the current hive.*/
    public ArrayList<Bee> popHalfOfOwnedBees(){

        ArrayList<Bee> half = new ArrayList<Bee>();
        int numberOfOwnedBees = numberOfOwnedBees();

        for(int i = 0; i <= numberOfOwnedBees/2; i++){
            half.add(ownedBees.get(ownedBees.size()));
            ownedBees.remove(ownedBees.get(ownedBees.size()));
        }

        //TODO do something about their hiveCoordinates/Direction

        return half;
    }

    public void addOwnedBee(Bee bee){
        ownedBees.add(bee);
    }

    public void addOwnedQueen(Queen queen){
        ownedQueens.add(queen);
    }
}
