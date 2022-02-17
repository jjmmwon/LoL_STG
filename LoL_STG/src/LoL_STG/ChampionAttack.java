package LoL_STG;

public class ChampionAttack extends GameObject implements moveObject{
	int power;
	public ChampionAttack(int x, int y, int speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = 60;
		this.height = 39;
		this.power = 10;
	}
	@Override
	public void move() {
		this.x += this.speed;
	}
}
