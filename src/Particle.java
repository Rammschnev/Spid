import toxi.physics2d.*;
import toxi.geom.*;

public class Particle extends VerletParticle2D {

    Particle(Vec2D loc) {
        super(loc);
    }

    public void display() {
        // Called by us later during draw to display particle
        ToxicSketch.p.fill(127);
        ToxicSketch.p.stroke(0);
        ToxicSketch.p.strokeWeight(2);
        ToxicSketch.p.ellipse(x, y, 32, 32);
    }
}
