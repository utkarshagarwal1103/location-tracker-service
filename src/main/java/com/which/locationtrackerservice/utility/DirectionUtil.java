package com.which.locationtrackerservice.utility;

import com.which.locationtrackerservice.constants.Constants;
import com.which.locationtrackerservice.domain.Coordinates;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DirectionUtil {

    private static int x;
    private static int y;

    public DirectionUtil(){
        this.x=0;
        this.y=0;
    }
    /*
       Method process the list of directions
       and returns coordinates post direction processing
     */
    public Coordinates retrieveCoordinates(List<String> directionInstructions){

        //Initial direction as North with 0,0 coordinates
        AtomicReference<String> currentDirection= new AtomicReference<>(Constants.NORTH);

        directionInstructions.forEach(instruction ->{
            //get new direction
            String newDirection=getDirection(instruction, currentDirection.get());

            //set new direction as current direction
            currentDirection.set(newDirection);

            //modify coordinates based on updated direction
            coordinateChange(instruction,getDirection(instruction, currentDirection.get()));
        });

        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);

        //resetting the coordinates
        x=0;y=0;

        return coordinates;
    }

    /*
      Method to change coordinate based on the instruction
     */
    private static void coordinateChange(String instruction,String currentDirection) {

        if (instruction.equalsIgnoreCase(Constants.FORWARD)) {
            switch (currentDirection.toUpperCase()) {
                case Constants.NORTH:
                    y++;
                    break;
                case Constants.EAST:
                    x++;
                    break;
                case Constants.SOUTH:
                    y--;
                    break;
                case Constants.WEST:
                    x--;
                    break;
                default:
                    System.out.println("invalid");

            }
        }

    }


    /*
      Method to get Direction based on the instructions
     */
    public static String getDirection(String instruction, String currentDirection){

        switch(currentDirection.toUpperCase()){
            case Constants.NORTH:
                if(instruction.equalsIgnoreCase(Constants.LEFT))
                    return Constants.WEST;
                if(instruction.equalsIgnoreCase(Constants.RIGHT))
                    return Constants.EAST;

            case Constants.EAST :
                if(instruction.equalsIgnoreCase(Constants.LEFT))
                    return Constants.NORTH;
                if(instruction.equalsIgnoreCase(Constants.RIGHT))
                    return Constants.SOUTH;

            case Constants.WEST :
                if(instruction.equalsIgnoreCase(Constants.LEFT))
                    return Constants.SOUTH;
                if(instruction.equalsIgnoreCase(Constants.RIGHT))
                    return Constants.NORTH;

            case Constants.SOUTH :
                if(instruction.equalsIgnoreCase(Constants.LEFT))
                    return Constants.EAST;
                if(instruction.equalsIgnoreCase(Constants.RIGHT))
                    return Constants.WEST;

            case Constants.FORWARD:
                return currentDirection;

            default:
                return null;

        }

    }
}
