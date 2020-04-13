public abstract class Room {
    /***
     * A container interface for GUI "states" or "screens"
     * See application lifecycle
     */

    public static boolean initialized;

    public abstract void initialize();
    public abstract void suspend();
    public abstract void close();
    public abstract void draw();

}
