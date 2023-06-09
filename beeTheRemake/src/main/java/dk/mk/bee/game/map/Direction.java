package dk.mk.bee.game.map;

import dk.mk.bee.game.util.Vector2;

import java.util.Arrays;
import java.util.LinkedList;

/** This enum is used as the directions for the bees. */
public enum Direction{
    NORTH(0, 1), NORTHEAST(1, 1), EAST(1, 0), SOUTHEAST(1, -1), SOUTH(0, -1), SOUTHWEST(-1, -1), WEST(-1, 0), NORTHWEST(-1, 1);

    private Vector2 coordinates;

    Direction(int x, int y) {
        this.coordinates = new Vector2(x, y);
    }

    public Vector2 getCoordinates() {
        return new Vector2(coordinates.x(), coordinates.y());
    }

    public static LinkedList<Direction> getLinkedList(){
        return new LinkedList<Direction>(Arrays.asList(Direction.values()));
    }

    public Direction findOppositeDirection(){
        for(Direction newDirection : Direction.values()){
            if((newDirection.coordinates.x() * -1) == this.coordinates.x() && (newDirection.coordinates.y() * -1) == this.coordinates.y())
                return newDirection;
        }

        return null; //Should not get here
    }
}