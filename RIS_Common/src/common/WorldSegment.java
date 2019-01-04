package common;

import java.util.ArrayList;

public class WorldSegment {

	ArrayList<Integer> list = new ArrayList<Integer>();
	int size = 1000; // 1000px in x and y
	int x = 0;
	int y = 0;
	boolean active = false;
	int ID = 0;

	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

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
