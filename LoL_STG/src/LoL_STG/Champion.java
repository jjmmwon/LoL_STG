package LoL_STG;

//è�Ǿ� �̹���, ���� ����
public class Champion {
	//è�Ǿ�����
	int championHp;
	int championExp, championLv;
	int championSpeed, championAd, championAttackSpeed;
	int width =94, height = 85;
	
	//Ŭ���� �ʱ�ȭ
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
