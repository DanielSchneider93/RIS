package common;

import java.util.ArrayList;

public class WorldSegment {

	ArrayList<Integer> list = new ArrayList<Integer>();
	int size = 100;
	int x = 0;
	int y = 0;
	boolean active = false;

	public int getSize() {
		return size;
	}

	public ArrayList<Integer> getList() {
		return list;
	}

	public void setList(ArrayList<Integer> list) {
		this.list = list;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addElementToSegment(Integer value) {
		list.add(value);
	}
}
