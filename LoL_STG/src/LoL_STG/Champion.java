package LoL_STG;

//챔피언 이미지, 정보 생성
public class Champion {
	//챔피언정보
	int championHp;
	int championExp, championLv;
	int championSpeed, championAd, championAttackSpeed;
	int width =94, height = 85;
	
	//클래스 초기화
	public Champion() {
		this.championHp = 400; this.championExp = 0; this.championLv = 1;
		this.championSpeed = 4; this.championAd = 10; this.championAttackSpeed = 7;
	}
	
	public void championLevelUp(){
		this.championLv++;
		this.championSpeed++;
		this.championAd *= 2;
		this.championAttackSpeed++;
		
	}
}
