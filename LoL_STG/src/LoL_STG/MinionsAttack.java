package LoL_STG;

public class MinionsAttack extends GameObject implements moveObject{
	
	public MinionsAttack(int x, int y){
		this.x = x;
		this.y =y;
		this.speed = 5;
		this.width = 33;
		this.height = 30;
	}
	
	@Override
	public void move() {
		this.x -= this.speed;
	}
	
}
