package common;

public class ClosestWall {
	double minDist = 10000;
	int minX;
	int minY;
	
	public ClosestWall(double minDist, int minX, int minY) {
		this.minDist = minDist;
		this.minX = minX;
		this.minY = minY;
	}

	public double getMinDist() {
		return minDist;
	}

	public void setMinDist(double minDist) {
		this.minDist = minDist;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}	
}
