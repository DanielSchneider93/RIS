package common;

import java.util.ArrayList;
import java.util.Random;

public class GenerateWorld {
	ArrayList<WorldSegment> segmentList = new ArrayList<WorldSegment>();
	long seed = 64542;

	private int howMuchSegmentX = 20;
	private int howMuchSegmentY = 20;
	private int segmentSize = 100; // 10x10 each sprite has 100px so 1000x1000 pixel for each segment
	private int grass = 0;
	private int wall = 1;

	public ArrayList<WorldSegment> generateMap() {
		generate();
		return segmentList;
	}

	public void generate() {
		int id = 0;
		Random rnd = new Random(seed);

		for (int s = 0; s < howMuchSegmentX; ++s) {
			for (int i = 0; i < howMuchSegmentY; ++i) {

				WorldSegment segment = new WorldSegment();
				segment.setX(s * segmentSize * 10);
				segment.setY(i * segmentSize * 10);
				segment.setID(id);

				for (int j = 0; j < segmentSize; ++j) {
					if (rnd.nextInt(10) < 9) {
						segment.addElementToSegment(grass);
					} else {
						segment.addElementToSegment(wall);
					}
				}
				id++;
				segmentList.add(segment);
			}
		}
	}

	public int getHowMuchSegmentX() {
		return howMuchSegmentX;
	}

	public int getHowMuchSegmentY() {
		return howMuchSegmentY;
	}

	public int getSegmentSize() {
		return segmentSize;
	}

}
