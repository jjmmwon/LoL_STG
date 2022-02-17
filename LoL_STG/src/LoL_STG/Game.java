package LoL_STG;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

//In Game에서의 모든 상황을 구현한다.
public class Game extends Thread{
	
	// 배경화면
	private Image backgroundImage = new ImageIcon(Main.class.getResource("../images/backgroundImage.jpg")).getImage();
	private int backgroundX = 0;
	//끝났을 시
	private Image backgroundWin = new ImageIcon(Main.class.getResource("../images/challenger.jpg")).getImage();
	private Image backgroundDefeat = new ImageIcon(Main.class.getResource("../images/bronze.png")).getImage();
	
	//챔피언 이미지와 좌표
	private Image championImage = new ImageIcon(Main.class.getResource("../images/championEzreal.png")).getImage();
	private int championX, championY;
	private Champion ezreal;
	private Image championAttackImage1 = new ImageIcon(Main.class.getResource("../images/championAttack1.png")).getImage();
	private Image championAttackImage2 = new ImageIcon(Main.class.getResource("../images/championAttack2.png")).getImage();
	private int attackDelay; //공격이 무한대로 나가는걸 대비
	
	//미니언 이미지
	private Image minionImage = new ImageIcon(Main.class.getResource("../images/minionImage.png")).getImage();
	private Image minionAttackImage = new ImageIcon(Main.class.getResource("../images/minionAttackImage.png")).getImage();
	
	//용 스킬 이미지
	private Image dragonWindImage = new ImageIcon(Main.class.getResource("../images/dragonWind.png")).getImage();
	private Image dragonFireImage = new ImageIcon(Main.class.getResource("../images/dragonFire.png")).getImage();
	
	//키보드 입력을 위한 boolean 자료 생성
	private boolean up, down, left, right, pressA;
	
	boolean defeat, win; //게임상황
	
	//챔피언 공격 관리하기위한 해쉬맵과 key로 이용할 integer
	HashMap<Integer, ChampionAttack> championAttackMap = new HashMap<Integer, ChampionAttack>();
	private int champAttCnt =0;
	
	//적에 관한 초기화값
	private boolean boss;
	private int cnt;
	private int minionCnt;
	HashMap<Integer, Minions> minionsMap = new HashMap<Integer, Minions>();
	HashMap<Integer, MinionsAttack> minionsAttackMap = new HashMap<Integer, MinionsAttack>();
	Dragon gd= new Dragon();
	HashMap<Integer, WindSkill> windSkillMap = new HashMap<Integer, WindSkill>();
	HashMap<Integer, FireSkill> fireSkillMap = new HashMap<Integer, FireSkill>();
	
	
	
	//클래스 초기화
	public Game() {
		championX = 10; championY = 300;
		ezreal = new Champion();
		up = false; down = false; left = false; right = false; pressA = false;
		defeat = false; win = false;
		attackDelay = 0;
		
		boss = false;
		cnt = 0;
		minionCnt =0;
	}
	
	//배경, 챔피언, 챔피언 정보, 공격상황, 미니언, 용 따로 움직임 구현
	public void backgroundDraw(Graphics2D g) {
		g.drawImage(backgroundImage, backgroundX, 0, null);
		backgroundX--;
		if(backgroundX < -1280)	
			g.drawImage(backgroundImage, Main.SCREEN_WIDTH-((backgroundX*-1)-1280), 0, null);
		if(backgroundX == -2560)	
			backgroundX=0;
	}
	//챔피언 그리기
	public void championDraw(Graphics2D g) {
		g.drawImage(championImage,championX,championY,null);
	}
	//챔피언 정보 그리기
	public void championInfoDraw(Graphics2D g) {
		//챔피언의 hp와 경험치 표시
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("SanSerif", Font.BOLD, 20));
		g.drawString("LV"+ezreal.championLv,20,55);
		g.drawString("Hp  ", 20, 85);
		g.drawString("Exp ", 20, 115);
		g.setColor(Color.RED);
		g.fillRect(60, 65, 200, 20);
		g.setColor(Color.GREEN);
		g.fillRect(60, 65, ezreal.championHp/2, 20);
		g.setColor(Color.BLACK);
		g.fillRect(60, 95, 200, 20);
		Color purple = new Color(0xFF00FF);
		g.setColor(purple);
		if(ezreal.championExp>200) {
			g.fillRect(60, 95, 200, 20);
		}
		else {
			g.fillRect(60, 95, ezreal.championExp, 20);
		}
		
	}
	//챔피언 공격 그리기
	public void attackDraw(Graphics2D g) {
		if(ezreal.championLv==1) {
			for(int i =0; i<champAttCnt;i++) {
				if(championAttackMap.get(i)!=null) {
					ChampionAttack att = championAttackMap.get(i);
					g.drawImage(championAttackImage1,att.x, att.y, null);
				}
			}
		}
		else {
			for(int i =0; i<champAttCnt;i++) {
				if(championAttackMap.get(i)!=null) {
					ChampionAttack att = championAttackMap.get(i);
					g.drawImage(championAttackImage2,att.x, att.y, null);
				}
			}
		}
	}
	//미니언 움직임 및 공격 화면에 그리기
	public void minionDraw(Graphics2D g) {
		for(int i=0;i<minionCnt;i++) {
			Minions m = minionsMap.get(i);
			MinionsAttack ma = minionsAttackMap.get(i);
			if(m!=null) {
				g.drawImage(minionImage, m.x,m.y,null);
				g.setColor(Color.GREEN);
				g.fillRect(m.x, m.y-10, m.minionHp*2, 7);
			}
			if(ma!=null) {
				g.drawImage(minionAttackImage, ma.x,ma.y,null);
			}
		}
	}
	//드래곤 및 스킬 그리기
	public void dragonDraw(Graphics2D g) {
		if(boss) {
			g.drawImage(gd.dragonImage,gd.x,gd.y,null);
			g.setColor(Color.GREEN);
			g.fillRect(gd.x,gd.y-20,gd.dragonHp/6,15);	
		}
		for(int i=0;i<3;i++) {
			WindSkill ws = windSkillMap.get(i);
			FireSkill fs = fireSkillMap.get(i);
			if(ws!=null) {
				g.drawImage(dragonWindImage,ws.x,ws.y,null);
			}
			if(fs!=null) {
				g.drawImage(dragonFireImage,fs.x,fs.y,null);
			}
		}
		
	}
	//엔딩화면
	public void endingDraw(Graphics2D g) {
		if(win) {
			g.drawImage(backgroundWin,0,0,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("SanSerif", Font.BOLD, 80));
			g.drawString("You Win",480,120);
		}
		else {
			g.drawImage(backgroundDefeat,0,0,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("SanSerif", Font.BOLD, 80));
			g.drawString("You Lose",480,120);
		}
	}
	
	//쓰레드 시작
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			}catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			while(!defeat&&!win) {
				keyInput();
				championAttackProcess();
				enemySpawn(boss);
				enemyProcess(boss);
				if(boss) {
					if(cnt%500==0)
						generateDragonSkill('w');
					else if(cnt%700==0)
						generateDragonSkill('f');
				}
				
				try {
					Thread.sleep(10);
					attackDelay = (++attackDelay%10000);
				}catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				cnt++;
			}
		}
	}
	
	//미니언 생성
	public void enemySpawn(boolean boss) {
		if(!boss) {
			if(cnt%600==0) {
				int y = (int)(Math.random()*100);
				Minions m1 = new Minions(1280,y*5+30);
				Minions m2 = new Minions(1280-20,y*5+90);
				Minions m3 = new Minions(1280,y*5+150);
				MinionsAttack ma1 = new MinionsAttack(1280,y*5+30);
				MinionsAttack ma2 = new MinionsAttack(1280-20,y*5+90);
				MinionsAttack ma3 = new MinionsAttack(1280,y*5+150);
				minionsMap.put(minionCnt, m1);
				minionsAttackMap.put(minionCnt++, ma1);
				minionsMap.put(minionCnt, m2);
				minionsAttackMap.put(minionCnt++, ma2);
				minionsMap.put(minionCnt, m3);
				minionsAttackMap.put(minionCnt++, ma3);
			}
		}
	}
	// 미니언 공격 및 드래곤 스킬 움직임 구현 및 챔피언에게 적중했을 시 상황 구현
	public void enemyProcess(boolean boss) {
		if(!boss) {
			for(int i=0;i<minionCnt;i++) {
				Minions m = minionsMap.get(i);
				MinionsAttack ma = minionsAttackMap.get(i);
				if(m!=null) {
					m.move();
					if(championX+ezreal.width-4>m.x&&championX<m.x+m.width&&championY+ezreal.height>m.y&&championY<m.y+m.height) {
						ezreal.championHp -= 20;
						minionsMap.remove(i);
						if(ezreal.championHp<=0) {
							defeat = true;
						}
					}
				}
				if(ma!=null) {
					ma.move();
					if(championX+ezreal.width-4>ma.x&&championX<ma.x+ma.width&&championY+ezreal.height>ma.y&&championY<ma.y+ma.height) {
						ezreal.championHp -= 20;
						minionsAttackMap.remove(i);
						if(ezreal.championHp<=0) {
							defeat = true;
						}
					}
				}
				
				
			}
		}
		else {
			for(int i=0;i<minionCnt;i++) {
				minionsMap.remove(i);
				minionsAttackMap.remove(i);
			}
			gd.move();
			if(championX+ezreal.width-4>gd.x&&championX<gd.x+gd.width&&championY+ezreal.height>gd.y&&championY<gd.y+gd.height) {
				ezreal.championHp -= 3;
			}
			for(int j=0;j<3;j++) {
				WindSkill ws = windSkillMap.get(j);
				FireSkill fs = fireSkillMap.get(j);
				if(ws!=null) {
					ws.move();
					if(championX+ezreal.width-4>ws.x&&championX<ws.x+ws.width&&championY+ezreal.height>ws.y&&championY<ws.y+ws.height) {
						ezreal.championHp -= ws.damage;
						windSkillMap.remove(j);
						if(ezreal.championHp<=0) {
							defeat = true;
						}
					}
				}
				if(fs!=null) {
					fs.move();
					if(championX+ezreal.width-4>fs.x&&championX<fs.x+fs.width&&championY+ezreal.height>fs.y+60&&championY<fs.y+fs.height) {
						ezreal.championHp -= fs.damage;
						fireSkillMap.remove(j);
						if(ezreal.championHp<=0) {
							defeat = true;
						}
					}
				}
			}
		}
	}
	
	//드래곤 스킬 생성 및 해쉬맵을 통해 관리
	public void generateDragonSkill(char type) {
		if (type == 'w') {
			int y = (int) (Math.random() * 100);
			WindSkill wind1 = new WindSkill( 1280, y * 2);
			WindSkill wind2 = new WindSkill( 1280, y * 2 + 300);
			WindSkill wind3 = new WindSkill( 1280, y * 2 + 600);
			windSkillMap.put(0, wind1);
			windSkillMap.put(1, wind2);
			windSkillMap.put(2, wind3);
		}
		else {
			int x = (int) (Math.random() * 100);
			FireSkill fire1 = new FireSkill(x*6+50, 0);
			FireSkill fire2 = new FireSkill(x*6+450, 0);
			FireSkill fire3 = new FireSkill(x*6+850, 0);
			fireSkillMap.put(0, fire1);
			fireSkillMap.put(1, fire2);
			fireSkillMap.put(2, fire3);
		}
	}
	
	//GameObject간 충돌했는지 확인
	public boolean crachCheck(GameObject o1, GameObject o2) {
		boolean ret = false;
		if(o1.x+o1.width>o2.x&&o1.x<o2.x+o2.width&&o1.y+o1.height>o2.y&&o1.y<o2.y+o2.height) {
			ret = true;
		}
		return ret;
	}
	

	//챔피언 공격 움직임 및 적에게 적중했을 시 상황 구현
	private void championAttackProcess() {
		for (int i = 0; i < champAttCnt; i++) {
			if (championAttackMap.get(i) != null) {
				ChampionAttack att = championAttackMap.get(i);
				att.move();
				if (att.x > 1280 + att.width) {
					championAttackMap.remove(i);
				}
				if (!boss) {
					for (int j = 0; j < minionCnt; j++) {
						if (minionsMap.get(j) != null) {
							if (crachCheck(att, minionsMap.get(j))) {
								minionsMap.get(j).minionHp -= att.power;
								championAttackMap.remove(i);
							}
							if (minionsMap.get(j).minionHp <= 0) {
								minionsMap.remove(j);
								ezreal.championExp += 10;
								if (ezreal.championExp == 200) {
									ezreal.championLevelUp();
									boss = true;
								}
							}
						}
					}
				} 
				else {
					if (crachCheck(att, gd)) {
						gd.dragonHp -= att.power;
						championAttackMap.remove(i);
						if (gd.dragonHp <= 0) {
							win = true;
						}
					}

				}
			}

		}

	}
	
	//키보드 인풋을 받아서 챔피언을 조종하는 method
	private void keyInput() {
		if (up && championY-ezreal.championSpeed > 30) {
			championY-=ezreal.championSpeed;
		}
		if (down && championY+ezreal.championSpeed+ezreal.height < 720) {
			championY+=ezreal.championSpeed;
		}
		if (left && championX-ezreal.championSpeed > 0) {
			championX-=ezreal.championSpeed;
		}
		if (right && championX+ezreal.championSpeed+ezreal.width < 1280) {
			championX+=ezreal.championSpeed;
		}
		if (pressA) {
			if(attackDelay%(ezreal.championAttackSpeed*2)==0) {
				ChampionAttack attack = new ChampionAttack(championX+ezreal.width-40, championY +20, ezreal.championAttackSpeed);
				championAttackMap.put(champAttCnt++, attack);
			}
		}
	}
	

	
	// keyListener를 통해 움직임의 값을 최신화 해주기 위한 method
	public void setUp(boolean input) {
		if(up==input)
			return;
		else
			up=input;
	}

	public void setDown(boolean input) {
		if(down==input)
			return;
		else
			down=input;
	}

	public void setLeft(boolean input) {
		if(left==input)
			return;
		else
			left=input;
	}

	public void setRight(boolean input) {
		if(right==input)
			return;
		else
			right=input;
	}

	public void setPressA(boolean input) {
		if(pressA==input)
			return;
		else
			pressA=input;
	}
	
	
}