package LoL_STG;

public class Minions extends GameObject implements moveObject{
	int minionHp;
	public Minions(int x, int y) {
		this.x =x;
		this.y = y;
		this.speed =4;
		this.width =64;
		this.height = 50;
		this.minionHp = 30;
	}
	
	@Override
	public void move() {
		this.x -= this.speed;
	}
	
}
