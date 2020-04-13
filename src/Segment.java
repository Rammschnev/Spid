import processing.core.PApplet;
import toxi.physics2d.*;
import toxi.geom.*;

public class Segment extends VerletSpring2D {

    static PApplet p = ToxicSketch.p;

    public static float length;
    public static float strength = 0.1f;
    public static float weight = 132.92f; // at gravity 0.5, this needs to be ~16000 times higher than strength to reach length*2
    public static float maxStretch = 1.1f; // Maximum ratio of current to resting length before segment is destroyed

    public static float strokeColor = 122f;
    public static float strokeWeight = 3f;
    public static float endpointRadius = 25f;

    public int snID; // ID used by SegmentNetwork

    public Segment(Vec2D a, Vec2D b) {
        super(new VerletParticle2D(a, weight), new VerletParticle2D(b, weight), length, strength);
    }

    public static void display(Segment seg) {
        p.stroke(strokeColor);
        p.strokeWeight(strokeWeight);
        p.line(seg.a.x, seg.a.y, seg.b.x, seg.b.y);
        p.circle(seg.a.x, seg.a.y, endpointRadius);
        p.circle(seg.b.x, seg.b.y, endpointRadius);
    }

    public static boolean overstretched(Segment seg) {
        float deltaX = Math.abs(seg.a.x - seg.b.x);
        float deltaY = Math.abs(seg.a.y - seg.b.y);
        float currentLength = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        return currentLength / length >= maxStretch;
    }
}
