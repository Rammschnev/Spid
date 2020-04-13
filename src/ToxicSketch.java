import processing.core.PApplet;

import java.util.ArrayList;

public class ToxicSketch extends PApplet {

    public static PApplet p; // Reference to Processing environment for other classes

    public static Room currentRoom;
    public ArrayList<Room> rooms;

    public void settings () {
        size(640, 360);
    }

    public void setup() {
        p = this;

        rooms = new ArrayList<Room>();
        currentRoom = new ExecutionTestRoom();
        rooms.add(currentRoom);

    }

    public void draw() {
        currentRoom.draw();
   }

    public static void  main(String[] args) {
        // This is where Java begins program execution
        PApplet.main("ToxicSketch");
    }
}
