package common;

import java.util.ArrayList;
import java.util.Random;

public class GenerateWorld {
	ArrayList<WorldSegment> segmentList = new ArrayList<WorldSegment>();
	long seed = 64542;

	int howMuchSegmentX = 10;
	int howMuchSegmentY = 10;
	int segmentSize = 100;

	private int grass = 0;
	private int wall = 1;

	public ArrayList<WorldSegment> generateMap() {
		generate();
		return segmentList;
	}

	public void generate() {
		Random rnd = new Random(seed);

		for (int s = 0; s < howMuchSegmentX; ++s) {
			for (int i = 0; i < howMuchSegmentY; ++i) {

				WorldSegment segment = new WorldSegment();
				segment.setX(s*segmentSize);
				segment.setY(i*segmentSize);

				for (int j = 0; j < segmentSize; ++j) {
					if (rnd.nextInt(10) < 9) {
						segment.addElementToSegment(grass);
					} else {
						segment.addElementToSegment(wall);
					}
				}
				segmentList.add(segment);
			}
		}
	}
}
