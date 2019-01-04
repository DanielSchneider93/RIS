package common;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable{

	ArrayList<WorldSegment> segmentList;
	
	public Map(ArrayList<WorldSegment> segmentList) {
		this.segmentList = segmentList;
	}

	public ArrayList<WorldSegment> getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(ArrayList<WorldSegment> segmentList) {
		this.segmentList = segmentList;
	}

	
}
