package LoL_STG;


public class WindSkill extends GameObject implements moveObject{
	
	int damage;
	
	public WindSkill(int x, int y) {
		this.x = x;
		this.y =y;
		this.speed = 6;
		this.width = 180;
		this.height = 120;
		this.damage = 50;
	}
	

	@Override
	public void move() {
		this.x -= this.speed;
	}
}
