package frc.team5115.subsystems;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Subsystem {

    Queue<String> queue = new LinkedList<String>();
    String state;

    public void update(){

    }

    public String returnCurrentState(){
        return state;
    }

    public void addTask(String state){
        queue.add(state);
    }

    public void removeCurrentTask(){
        try {
            queue.remove();
        } catch (NoSuchElementException e){
            System.out.println("Nothing to remove...");
        }
    }

}
