package LoL_STG;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Lol extends JFrame{
	
	private Image screenImage;
	private Graphics screenGraphic;	
	private Image backgroundImage = new ImageIcon(Main.class.getResource("../images/startBackgroundImage.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	private ImageIcon startButtonImageEntered = new ImageIcon(Main.class.getResource("../images/startButton2.png"));
	private ImageIcon startButtonImageBasic = new ImageIcon(Main.class.getResource("../images/startButton.png"));
	private ImageIcon ruleButtonImageEntered = new ImageIcon(Main.class.getResource("../images/ruleButton2.png"));
	private ImageIcon ruleButtonImageBasic = new ImageIcon(Main.class.getResource("../images/ruleButton.png"));
	
	
	private JButton exitButton = new JButton(new ImageIcon(Main.class.getResource("../images/exitButton.png")));
	private JButton startButton = new JButton(startButtonImageBasic);
	private JButton ruleButton = new JButton(ruleButtonImageBasic);
	
	private boolean startGame= false;
	private boolean ruleGame= false;
	private int mouseX, mouseY;
	
	public static Game game = new Game();
	
	
	public Lol() {
		setUndecorated(true);
		setTitle("League of Legends");
		setSize(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0,0,0,0));
		setLayout(null);
		
		
		//�޴��� ����
		menuBar.setBounds(0,0,1280,30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseX = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		
		//�����ư ����
		exitButton.setBounds(1250,0,30,30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		//���� ��ư
		startButton.setPressedIcon(startButtonImageEntered);
		startButton.setBounds(440,400 , 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameStart();
			}
		});
		
		//���� ��Ģ
		ruleButton.setPressedIcon(ruleButtonImageEntered);
		ruleButton.setBounds(440, 550, 400, 100);
		ruleButton.setBorderPainted(false);
		ruleButton.setContentAreaFilled(false);
		ruleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruleGame =true;
				ruleButton.setVisible(false);
				startButton.setBounds(440,600 , 400, 100);
			}
		});
		
		add(startButton);
		add(ruleButton);
		add(exitButton);
		add(menuBar);
	}
	
	//���� ����, Ű���� �Է� �� GameŬ������ thread ����
	public void gameStart() {
		addKeyListener(new KeyListener());
		setFocusable(true);
		startGame = true;
		startButton.setVisible(false);
		ruleButton.setVisible(false);
		game.start();
	}
	
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	//ȭ�� �׸���
	public void screenDraw(Graphics2D g) {
		//������ �����ϸ� GameŬ������ Draw �޼ҵ���� ���� ���� ȭ�� ����
		if(startGame&&!game.win&&!game.defeat) {
			//�ΰ��� ȭ��
			game.backgroundDraw(g);
			game.championDraw(g);
			game.attackDraw(g);
			game.minionDraw(g);
			game.dragonDraw(g);
			game.championInfoDraw(g);
		}
		else if(!startGame){
			if(ruleGame) {
				//�� ��ư�� ��������
				g.drawImage(new ImageIcon(Main.class.getResource("../images/ruleImage.png")).getImage(),0,0,null);
			}
			else g.drawImage(backgroundImage,0,0,null); //�⺻ ����
		}
		else {
			//����ȭ��
			game.endingDraw(g);
		}
		paintComponents(g);
		this.repaint();
	}
	
}