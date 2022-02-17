package LoL_STG;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Dragon extends GameObject implements moveObject{
	int dragonHp;
	private boolean up;
	Image dragonImage = new ImageIcon(Main.class.getResource("../images/dragonImage.png")).getImage();

	public Dragon() {
		this.dragonHp = 2000;
		this.x = 900;
		this.y = 280;
		this.speed = 1;
		this.width = 330;
		this.height = 175;
		this.up = true;
	}
	@Override
	public void move() {
		if(up) {
			this.y -= this.speed;
			if(this.y<50) {
				up=false;
			}
		}
		else {
			this.y += this.speed;
			if(this.y>500)
				up=true;
		}
	}
}
