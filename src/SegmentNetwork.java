import toxi.geom.Vec2D;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class SegmentNetwork {

    public ArrayList<ArrayList<Integer>> A;
    public ArrayList<ArrayList<Integer>> B;
    public ArrayList<Segment> segments;

    public SegmentNetwork() {
        A = new ArrayList<ArrayList<Integer>>();
        B = new ArrayList<ArrayList<Integer>>();
        segments = new ArrayList<Segment>();
    }

    @Override
    public String toString() {
        String outStr = String.format("SegmentNetwork instance || %d segments\n", segments.size());

        // Unnecessary pretty underline
        int lengthSoFar = outStr.length();
        String lineSegment = "+--+";
        int iterations = lengthSoFar / lineSegment.length();
        if (lengthSoFar % lineSegment.length() > 0) iterations++;
        outStr += Utilities.stringRepeat(lineSegment, iterations);
        outStr += "\n";
        // Okay all done

        outStr += String.format("A.size()==%d ", A.size());
        outStr += String.format("B.size()==%d ", B.size());
        outStr += String.format("segments.size()==%d\n\n", segments.size());

        // The segments column needs very little space, so we'll treat it separately and see exactly how much it needs
        int segColumnDigits = 0;
        float digitCounter = (float) segments.size() - 1;
        do {
            segColumnDigits++;
            digitCounter /= 10f;
        }
        while (digitCounter >= 1f);

        // Some other settings
        int cellPadding = 1;
        String padString = Utilities.stringRepeat(" ", cellPadding);
        int tableWidth = 80;
        String borderChar = "*";

        int segColWidth = segColumnDigits + cellPadding * 2 + 2; // + 2 for the left and right cell borders
        int epColWidth = (tableWidth - segColWidth) / 2;

        // Okay, let's do this
        String sLabel = String.format("%3s", "s");
        String aLabel = String.format("%"+segColWidth+"s", "A");
        String bLabel = String.format("%"+epColWidth+"s", "B");
        outStr += sLabel + aLabel + bLabel + "\n";
        outStr += Utilities.stringRepeat(borderChar, tableWidth) + "\n";

        for (int a = 0; a < segments.size(); a++) { // a represents index of segments, A, and B

            ArrayList<Integer> activeSublistA = A.get(a);
            ArrayList<Integer> activeSublistB = B.get(a);

            // Left border
            outStr += borderChar + padString;

            // Segment Column
            outStr += String.format("%-" + segColumnDigits + "d", segments.get(a).snID);
            outStr += padString + borderChar;

            // Endpoint Columns + additional rows
            boolean newRowNeeded = false;
            String subrowA = "";
            String subrowB = "";

            int indexCursorA = 0;
            int indexCursorB = 0;

            // First subrow
            outStr += Utilities.stringRepeat(" ", cellPadding);

            for (int b = 0; b < activeSublistA.size(); b++) {
                String newEntry = "";
                newEntry += activeSublistA.get(b);
                if (b < activeSublistA.size() - 1) newEntry += ", ";
                if (newEntry.length() + subrowA.length() <= epColWidth - cellPadding - 2) {
                    subrowA += newEntry;
                    indexCursorA++;
                }
                else {
                    newRowNeeded = true;
                    break;
                }
            }
            subrowA += Utilities.stringRepeat(" ", epColWidth - subrowA.length() - 3);
            outStr += subrowA + padString + borderChar + padString;

            for (int b = 0; b < activeSublistB.size(); b++) {
                String newEntry = "";
                newEntry += activeSublistB.get(b);
                if (b < activeSublistB.size() - 1) newEntry += ", ";
                if (newEntry.length() + subrowB.length() <= epColWidth - cellPadding - 2) {
                    subrowB += newEntry;
                    indexCursorB++;
                }
                else {
                    newRowNeeded = true;
                    break;
                }
            }
            subrowB += Utilities.stringRepeat(" ", epColWidth - subrowB.length() - 3);
            outStr += subrowB + padString + borderChar + "\n";

            if (!newRowNeeded) {
                outStr += Utilities.stringRepeat(borderChar, tableWidth) + "\n";
                continue;
            }

            // Additional subrows
            subrowA = "";
            subrowB = "";

            do {
                newRowNeeded = false;

                outStr += borderChar + Utilities.stringRepeat(" ", cellPadding * 2 + segColumnDigits) + borderChar;
                outStr += padString;

                for (int b = indexCursorA; b < activeSublistA.size(); b++) {
                    String newEntry = "";
                    newEntry += activeSublistA.get(b);
                    if (b < activeSublistA.size() - 1) newEntry += ", ";
                    if (newEntry.length() + subrowA.length() <= epColWidth - cellPadding - 2) {
                        subrowA += newEntry;
                        indexCursorA++;
                    }
                    else {
                        newRowNeeded = true;
                        break;
                    }
                }
                subrowA += Utilities.stringRepeat(" ", epColWidth - subrowA.length() - 3);
                outStr += subrowA + padString + borderChar + padString;

                for (int b = indexCursorB; b < activeSublistB.size(); b++) {
                    String newEntry = "";
                    newEntry += activeSublistB.get(b);
                    if (b < activeSublistB.size() - 1) newEntry += ", ";
                    if (newEntry.length() + subrowB.length() <= epColWidth - cellPadding - 2) {
                        subrowB += newEntry;
                        indexCursorB++;
                    }
                    else {
                        newRowNeeded = true;
                        break;
                    }
                }
                subrowB += Utilities.stringRepeat(" ", epColWidth - subrowB.length() - 3);
                outStr += subrowB + padString + borderChar + "\n";
            }
            while (newRowNeeded);
            outStr += Utilities.stringRepeat(borderChar, tableWidth) + "\n";
        }

        return outStr;
    }

    public int getSegmentIndex(Segment seg) {
        return segments.indexOf(seg);
    }

    public void addSegment(Segment seg) {
        A.add(new ArrayList<Integer>());
        B.add(new ArrayList<Integer>());
        segments.add(seg);

        seg.snID = segments.indexOf(seg);
    }

    public void connectSegments(Segment seg1, ArrayList<ArrayList<Integer>> endpointList1,
                                Segment seg2, ArrayList<ArrayList<Integer>> endpointList2) {
        /***
         * Connects seg1 from the endpoint given by endpointList1 to seg2 by the endpoint given by endpointList2
         * Ex.: snInstance.connectSegments(seg1, snInstance.A, seg2, snInstance.B);
         */

        /* Do physics spring connections between endpoints here */

        int index1 = getSegmentIndex(seg1);
        int index2 = getSegmentIndex(seg2);

        endpointList1.get(index1).add(index2);
        endpointList2.get(index2).add(index1);


    }

    public void removeSegment(Segment seg) {
        int index = getSegmentIndex(seg);
        segments.remove(index);
        A.remove(index);
        B.remove(index);

        // Now all indices above our index have been decremented. We need to adjust.
        ArrayList<Integer> removalQueue = new ArrayList<Integer>();
        for (int a = 0; a < segments.size(); a++) {

            Segment segbo = segments.get(a);
            if (segbo.snID != a) segbo.snID = a;

            ArrayList<Integer> activeSublistA = A.get(a);
            ArrayList<Integer> activeSublistB = B.get(a);
            for (int b = 0; b < activeSublistA.size(); b++) {
                int theActualEntry = activeSublistA.get(b);
                if (theActualEntry == index) {
                    removalQueue.add(b);
                }
                else if (theActualEntry > index) {
                    activeSublistA.set(b, theActualEntry - 1);
                }
            }
            removalQueue.forEach(activeSublistA::remove);
            removalQueue.clear();
            for (int b = 0; b < activeSublistB.size(); b++) {
                int theActualEntry = activeSublistB.get(b);
                if (theActualEntry == index) {
                    removalQueue.add(b);
                }
                else if (theActualEntry > index) {
                    activeSublistA.set(b, theActualEntry - 1);
                }
            }
            removalQueue.forEach(activeSublistB::remove);
            removalQueue.clear();
        }
    }

    public static void main(String[] args) {
        SegmentNetwork snInstance = new SegmentNetwork();
        Random random = new Random();

        System.out.println("Let's see if the universe favours us today.\n");

        for (int a = 0; a < 20; a++) {
            Vec2D c = new Vec2D(random.nextFloat() * 100, random.nextFloat() * 100);
            Vec2D d = new Vec2D(random.nextFloat() * 100, random.nextFloat() * 100);
            Segment seg = new Segment(c, d);
            snInstance.addSegment(seg);
            snInstance.segments.forEach(s -> snInstance.connectSegments(seg, snInstance.B,
                                                                        s, snInstance.A));
        }

        System.out.println(snInstance.toString());
    }

}
