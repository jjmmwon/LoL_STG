package LoL_STG;

public class FireSkill extends GameObject implements moveObject{
	int damage;
	
	public FireSkill(int x, int y) {
		this.x = x;
		this.y =y;
		this.speed = 6;
		this.width = 150;
		this.height = 233;
		this.damage = 100;
	}
	

	@Override
	public void move() {
		this.x -= 2;
		this.y += 3;
	}

}
