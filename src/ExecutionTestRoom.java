import processing.core.PApplet;

import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

import java.util.ArrayList;

public class ExecutionTestRoom extends Room {
    /*** No work yet on incorporating SegmentNetwork */

    PApplet p = ToxicSketch.p;
    public VerletPhysics2D physics;

    public ArrayList<Segment> segments;

    /* Execution Variables */
    static float segmentLength = 80f;

    public ExecutionTestRoom() {
        initialize();
    }

    public void initialize() {

        physics = new VerletPhysics2D();
        Segment.length = segmentLength;

        segments = new ArrayList<Segment>();
        Vec2D a = new Vec2D(p.width / 2.0f - segmentLength / 2.0f, p.height / 10.0f);
        Vec2D b = new Vec2D(p.width / 2.0f + segmentLength / 2.0f, p.height / 10.0f);
        Segment seggy = new Segment(a, b);
        segments.add(seggy);

        physics.addParticle(seggy.a);
        physics.addParticle(seggy.b);
        physics.addSpring(seggy);

        physics.addBehavior(new GravityBehavior2D(new Vec2D(0f, 0.5f)));
        seggy.b.lock();
    }

    public void suspend() {
        System.out.println("Execution Test Room activity suspended");
    }

    public void close() {
        physics.clear();
    }

    public void draw() {
        p.background(0);

        physics.update();

        p.stroke(Segment.strokeColor);
        p.strokeWeight(Segment.strokeWeight);
        ArrayList<Segment> overstretched = new ArrayList<Segment>();
        segments.forEach(seg -> { if (Segment.overstretched(seg)) overstretched.add(seg); });
        overstretched.forEach(seg -> segments.remove(seg));
        segments.forEach(Segment::display);

    }

}
