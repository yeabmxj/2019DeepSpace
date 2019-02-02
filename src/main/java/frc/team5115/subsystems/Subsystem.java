package frc.team5115.subsystems;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Subsystem {

    Deque<String> queue = new LinkedList<String>();
    String state;

    public void update(){

    }

    public String returnCurrentState(){
        return queue.peekFirst();
    }

    public void changeDefaultTask(String state){ queue.addLast(state);}

    public void addTask(String state){
        if(!queue.contains(state)){
            queue.addFirst(state);
        }
    }

    public void removeCurrentTask(){
        try {
            if(queue.size() >= 2){
                queue.removeFirst();
            } else {
                throw new NoSuchElementException("First element should always stay in queue");
            }
        } catch (NoSuchElementException e){
            System.out.println("Nothing to remove...");
        }
    }

}
