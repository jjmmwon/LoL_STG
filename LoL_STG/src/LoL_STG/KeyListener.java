package LoL_STG;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//키보드 입력값을 받기위한
public class KeyListener extends KeyAdapter{
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(Lol.game == null) {
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			Lol.game.setUp(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			Lol.game.setDown(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			Lol.game.setLeft(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			Lol.game.setRight(true);
		}
		else if(e.getKeyCode()==KeyEvent.VK_A) {
			Lol.game.setPressA(true);
		}
	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(Lol.game == null) {
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			Lol.game.setUp(false);
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			Lol.game.setDown(false);
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			Lol.game.setLeft(false);
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			Lol.game.setRight(false);	
		}
		else if(e.getKeyCode()==KeyEvent.VK_A) {
			Lol.game.setPressA(false);
		}
	}
}
