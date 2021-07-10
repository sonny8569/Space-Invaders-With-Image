package SpaceInavderLast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

abstract class GameObject extends JPanel
{
   protected int posX;//왼쩍
   protected int posY;//위쪽
   protected int ImageX;//더하면 오른쪽
   protected int ImageY;//더하면 아래쪽
   protected Image GImage;
   GameObject(int x, int y, Image image,int mx, int my){
      posX=x;
      posY=y;
      GImage=image;
      ImageX=mx;
      ImageY=my;
      
      
   }
   void Setpos(int x, int y) {
      posX=x;
      posY=y;
   }
   int GetPosX()
   {
      return posX;
   }
   int GetPosY() 
   {
      return posY;
      
   }
   Image GetImage() 
   {
      return GImage;
      
   }
   void PlusPosX(int x) {
      posX+=x;
   }
   void PlusPosY(int y) {
      posY+=y;
   }
   
   
   
}
class PlayerObject extends GameObject{
   private boolean right;
   private boolean left;
   private boolean up;
   private boolean down;
   
   PlayerObject(int x, int y, Image image, int sizeX,int sizeY) {
      super(x, y, image, sizeX,sizeY);
      // TODO Auto-generated constructor stub
      right=false;
      left=false;
      up=false;
      down=false;
   }
   public void InPut() 
   {
      if(right) 
      {
         PlusPosX(10);
         if(posX>745) {
            posX-=10;
         }
      }
      if(left)
      {
         PlusPosX(-10);
         if(posX<1) {
            posX+=10;
         }
         
         
      }
      if(down)
      {
         PlusPosY(10);
         if(posY>700) {
            posY-=10;
            
         }
      }
      if(up)
      {
         PlusPosY(-10);
         if(posY<0) {
            posY+=10;
         }
      }
   }
   
   public void setRight(boolean c) {
      right=c;
   }
   public void setLeft(boolean c) {
      left=c;
   }
   public void setDown(boolean c) {
      down=c;
   }
   public void setUp(boolean c) {
      up=c;
   }
   public void KeyReset() {
      right=false;
      left=false;
      down=false;
      up=false;
   }
   
}
class EnemyObject extends GameObject{
	int check = 0;//13
	int movecheck = 5;
	int moveCount = 0;
	int move = 0; // 짝수는 오른쪽 홀수는 왼쪽
   EnemyObject(int x, int y, Image image,int sizeX,int sizeY) {
      super(x, y, image, sizeX, sizeY);
      // TODO Auto-generated constructor stub
   }
   public void Input() {
      // TODO Auto-generated method stub
		check++;
		if(check%movecheck==0) {
			if(moveCount==12) {
				move++;
				posY+=30;
				moveCount=0;
			
				if(movecheck>1)
				{
					movecheck--;
					
				}
			
			}
			if(move%2==0) {
				posX+=10;
			}else {
				posX-=10;
			}
			moveCount++;
			
			
		}
      
   }
public int CheckPlayer(EnemyObject enemy, ArrayList<PlayerObject> player1) {
   // TODO Auto-generated method stub
   for(PlayerObject p:player1) {
      if(p.posX>=enemy.posX&&p.posX<=enemy.posX+enemy.ImageX&&p.posY>=enemy.ImageY&&p.posY<=enemy.posY+enemy.ImageY) //정 가운데를 기준으로 잡았음
      {
         return 1;
      }
   }
   return 0;
}
   
   
}
class BulletObject extends GameObject{

   BulletObject(int x, int y, Image image,int sizeX,int sizeY) {
      super(x, y, image, sizeX, sizeY);
      // TODO Auto-generated constructor stub
   }
   public boolean Input() {
      PlusPosY(-10);
      if(posY>0) {
         return false;
      }
      return false;
      
   }
   public int CheckHitEnemy(BulletObject b, ArrayList<EnemyObject> enemy) {
      int sum=0;
      ArrayList renemy= new ArrayList();
         for(EnemyObject e: enemy) {
            if(b.posX>=e.posX&&b.posX<=e.posX+e.ImageX&&b.posY>=e.ImageY&&b.posY<=e.posY+e.ImageY) //정 가운데를 기준으로 잡았음
            	{
               sum+=1;
               renemy.add(e);
            }
         }
         enemy.removeAll(renemy);
      return sum;
      
   }
   public int CheckHitBonus(BulletObject b, ArrayList<BonusObject> bonusA) {
      // TODO Auto-generated method stub
      int sum= 0;
      ArrayList rBonus = new ArrayList();
       for (BonusObject p: bonusA) {
    	   if(b.posX>=p.posX&&b.posX<=p.posX+p.ImageX&&b.posY>-p.posY&&b.posY<=p.posY+p.ImageY) //정 가운데를 기준으로 잡았음
    	   {
    		   sum+=1;
    		   rBonus.add(p);
    	   }
       }
            
      bonusA.removeAll(rBonus);
      return sum;
   }
   
}
class EnemyBulletObject extends GameObject{

   EnemyBulletObject(int x, int y, Image image,int sizeX,int sizeY) {
      super(x, y, image, sizeX, sizeY);
      // TODO Auto-generated constructor stub
   }
   public void Input() {
      PlusPosY(5);
   }
   public int CheckHitPlayer(EnemyBulletObject e, ArrayList<PlayerObject> player1) {
      // TODO Auto-generated method stub
      for(PlayerObject p :player1) {
        if(e.posX>=p.posX&&e.posX<=p.posX+p.ImageX&&e.posY>=p.posY&&e.posY<=p.posY+p.ImageY) //정 가운데를 기준으로 잡았음
        {
        	return 1;
        }
      }
      
      return 0;
   }
   
   
   
}

class BonusObject extends GameObject{

   BonusObject(int x, int y, Image image,int sizeX,int sizeY) {
      super(x, y, image, sizeX, sizeY);
      // TODO Auto-generated constructor stub
   }
   public void InPut() {
      PlusPosX(1);
      
   }
   
   
}
 class GameHandler extends JPanel{
    private JTextArea textArea;
    private boolean GameOver;
    private boolean PlayerWin;
    private boolean EndGame;
    private boolean Bonus;
    private int HighScore;
    private int Score;
    private int GameHight=900;
    private int GameWidth=800;
    private int ScoreWidth=300;
    private int bonouscount;
     private PlayerObject player;
    private ArrayList<PlayerObject> Player1 = new ArrayList<PlayerObject>();
    private ArrayList<BulletObject> pbullet= new ArrayList<BulletObject>();
    private ArrayList<EnemyObject> enemy= new ArrayList<EnemyObject>();
    private ArrayList<BonusObject> bonusA= new ArrayList<BonusObject>();
    private ArrayList<EnemyBulletObject>FireEnemy= new ArrayList<EnemyBulletObject>();
    protected Image EnemyBulletImage = new ImageIcon("image/EnemyBullet.png").getImage();
    protected Image RightImage = new ImageIcon("image/GamePlayimage.jpg").getImage();
    protected Image PlayerBulletImage = new ImageIcon("image/PlayerBullet.png").getImage();
    protected Image EnemyImage = new ImageIcon("image/enemy.jpg").getImage();
    protected Image bnousImage= new ImageIcon("image/boou.png").getImage();
   public GameHandler(JTextArea ta,PlayerObject player) {
      textArea=ta;
      initData(player);
      LoadScoreFile();
      
   }

   private void initData(PlayerObject Player) {
      // TODO Auto-generated method stub
     Player1.clear();
     pbullet.clear();
     enemy.clear();
     bonusA.clear();
     FireEnemy.clear();
     bonusA.clear();
     player=Player;
     player.Setpos(300,600);
     Player1.add(player);
     player.KeyReset();
     //모든 ArrayList 초기화 해주며 키값까지 초기화 해줍니다.
     GameOver=false;
     PlayerWin=false;
     EndGame=false;
     Bonus=false;
     Score=0;
     bonouscount=0;
      for (int i = 0; i < 2; ++i) {
         for (int j = 0; j < 4; ++j) {
            EnemyObject e = new EnemyObject(i * 200+ 4 + (j * 100),  45* j +5, EnemyImage, 54, 57);
            enemy.add(e);//적 arraylist 저장

         }
      }
    
      
   }
   public void gameTiming() {
      // TODO Auto-generated method stub
      try {
         Thread.sleep(50);//마이크로50 마다 
         if(!Bonus)//boolean Bonus가 거짓일때마다 
         {
        	  bonouscount++;//보너스카운트++;
         }
      } catch (InterruptedException ex)
      {
         ex.printStackTrace();
      }
   }
   
   public boolean ReturnGameOver(){
      return GameOver;//게임 일시정지용
   }
   public boolean ReturnPlayerWin() {
      return PlayerWin;//자바 경고창을 위해 선언
   }
   public boolean ReturnEndGame() {
      return  EndGame;//게임을 끝내기 위해 선언
   }
   public void SaveHighScore() {
      if (Score > HighScore)
         HighScore = Score;//점수가 기존 higscore보다 높을시 highscore재정의
   }

   public void SaveScoreFile() {
      SaveHighScore();//highscore을 score.aaa에 적어줘서 저장
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Score.txt"))) {
         out.write(HighScore);
      } catch (Exception e) {
         // TODO: handle exception
      }
   }
   public void LoadScoreFile() {
	   //highsocre 읽어와주는것
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Score.txt"))) {
         HighScore = in.read();
         
      } catch (Exception e) {
         // TODO: handle exception
      }
   }
      public void PlayerBullet(PlayerObject player) {
         BulletObject bullet=new BulletObject(player.GetPosX()+(player.ImageX/2),player.GetPosY(),PlayerBulletImage,4,24);
         pbullet.add(bullet);//플레이어 총알 ArrayList 저장
         
         
      }
      public void EnemyBullet(EnemyObject enemy) {
         EnemyBulletObject enemybullet= new EnemyBulletObject(enemy.GetPosX()+(enemy.ImageX/2),enemy.GetPosY()+enemy.ImageY,EnemyBulletImage,4,24);
         FireEnemy.add(enemybullet);//적 총알arrayList 저장
         
      }
      public void DrawAll(Graphics G) {
         Graphics g2d= (Graphics2D) G;
         //게임창 그려주기
         g2d.setColor(Color.black);
         g2d.fillRect(0, 0, GameWidth , GameHight);
         g2d.setColor(Color.blue);
         g2d.fillRect(GameWidth, 0,ScoreWidth, GameHight);
         //각 arraylist그려줍니다.
         for(int i=0;i<Player1.size();i++) {
            g2d.drawImage(Player1.get(i).GetImage(), Player1.get(i).GetPosX(), Player1.get(i).GetPosY(),Player1.get(i).ImageX,Player1.get(i).ImageY,this);
         }
         for(int i=0;i<pbullet.size();i++) {
            g2d.drawImage(pbullet.get(i).GetImage(), pbullet.get(i).GetPosX(),pbullet.get(i).GetPosY(),pbullet.get(i).ImageX,pbullet.get(i).ImageY,this);
         }
         for(int i=0;i<enemy.size();i++) {
            g2d.drawImage(enemy.get(i).GetImage(),enemy.get(i).GetPosX(),enemy.get(i).GetPosY(),enemy.get(i).ImageX,enemy.get(i).ImageY,this);
         }
         for(int i=0;i<bonusA.size();i++) {
            g2d.drawImage(bonusA.get(i).GetImage(),bonusA.get(i).posX,bonusA.get(i).posY,bonusA.get(i).ImageX,bonusA.get(i).ImageY,this);
         }
         for(int i=0;i<FireEnemy.size();i++) {
            g2d.drawImage(FireEnemy.get(i).GetImage(),FireEnemy.get(i).GetPosX(),FireEnemy.get(i).GetPosY(),this);
         }
         //score창 g2d로 그려줍니다.
         g2d.setColor(Color.white);
         FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
         g2d.setFont(g2d.getFont().deriveFont(40F));
         metrics =g2d.getFontMetrics(g2d.getFont());
         g2d.setColor(Color.white);
         g2d.drawString("score:"+Score, (GameWidth+(ScoreWidth-metrics.stringWidth("score:"+Score))/2) , 100);
         //빬간색으로 글자수 입력 
         g2d.setColor(Color.red);
         g2d.drawString("" +Score, (GameWidth + (ScoreWidth - metrics.stringWidth("score :" + Score)) / 2) +112,100);
         g2d.setFont(g2d.getFont().deriveFont(25f));
         metrics = g2d.getFontMetrics(g2d.getFont());
         g2d.setColor(Color.white);
         g2d.drawString("TOP SCORE: " +HighScore,(GameWidth + (ScoreWidth - metrics.stringWidth("TOP SCORE: " +HighScore)) / 2), 300);
         g2d.setColor(Color.red);
         g2d.drawString("" + HighScore, (GameWidth + "TOP SCORE: ".length()+ (ScoreWidth - metrics.stringWidth("TOP SCORE: " +HighScore)) / 2) + 149, 300);
         g2d.drawImage(RightImage, 800, 400, this);

         
         
      }
      public void GameInput() {
         SaveHighScore();
        
         for(PlayerObject player:Player1) {
            player.InPut();//플레이어 움직이는 함수
         }
         
         for(EnemyObject Enemy:enemy) {
            Enemy.Input();
            Random random =new Random();
            if (0 == 1000 % (int) (1 + Math.random() * 1000)) 
             {
                EnemyBullet(Enemy);//적총알함수에 적 인자넘겨주기
                
             }
            if(Enemy.posY>700) {
               GameOver=true;//적이 일정수 이상 내려올시 게임일시정지
            }
            int a= Enemy.CheckPlayer(Enemy,Player1);//적과 플레이어가 충돌했을시
            if(a==1) {
               GameOver=true;
            }

         }
         for(EnemyBulletObject Ebullet:FireEnemy) {
            Ebullet.Input();
            int checkP=Ebullet.CheckHitPlayer(Ebullet, Player1);//적 총알이 플레이어와 충돌했을시
            if(checkP==1) {
               PlayerWin=false;
               GameOver=true;
            }
         }
        ArrayList removePlayerBullet= new ArrayList();//플레이어 총알 없에주는것
        
         for(BulletObject bullet: pbullet) {
            bullet.Input();
            int checkE=bullet.CheckHitEnemy(bullet, enemy);
           
            if(checkE==1) {
               Score+=10;
               removePlayerBullet.add(bullet);//플레이어 총알 없에주는 arraylist 추가
               
              
            }
            int checkB=bullet.CheckHitBonus(bullet, bonusA);
            if(checkB==1) {
               Score+=100;
               removePlayerBullet.add(bullet);
               Bonus=false;
            }
         }
         
         pbullet.removeAll(removePlayerBullet);//제거
         
         if(bonouscount%101==100&&Bonus==false) {
             BonusObject bonus = new BonusObject(30,5,bnousImage,58,37) ;
             bonusA.add(bonus);
             Bonus=true;
             bonouscount=0;
         }
         
         for(int i=0;i<bonusA.size();i++) {
             bonusA.get(i).InPut();//보너스 이미지 움직이는것
          }

         if(enemy.isEmpty()) //적 arraylist가 비어있을시
         {
             PlayerWin=true;
             GameOver=true;
          }
      }

      public void drawGameOver() {
         // TODO Auto-generated method stub
          String ss;
          if (PlayerWin) {
             ss = "You Win!";

          } else {
             ss = "You Lose!";
          }

          int result = JOptionPane.showConfirmDialog(null, "Play Again?", ss, JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.CLOSED_OPTION) {
             SaveScoreFile();
             EndGame = true;

          } else if (result == JOptionPane.YES_OPTION) {
             SaveHighScore();
             
             initData(player);
             GameOver = false;
             
               
          }

          else {
             SaveScoreFile();
             EndGame = true;
          }
         
      }
    
 }
 
public class SpaceInavderLast extends JPanel implements KeyListener{
   GameHandler handler;
   private JTextArea textArea= new JTextArea();
   private static SpaceInavderLast aaa;//화면 하나 더추가
   
   JPanel startPanel;
   JPanel GamePanel;
   boolean fire;
   
   private JButton btn;
   private JLabel ShowImage=new JLabel();
   private JFrame frame= new JFrame("Let's Play SpaceInvader3");
   protected Image PlayerImage=new ImageIcon("image/Player.jpg").getImage();
   PlayerObject Player= new PlayerObject(300,600,PlayerImage,66,66);
   

   
   public SpaceInavderLast() {
      // TODO Auto-generated constructor stub
      handler= new GameHandler(textArea, Player);
      
      frame.setBounds(100 , 100 , 1100, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      btn=new JButton("Game Start");
      btn.addActionListener(new  MyActionListener());
      btn.setPreferredSize(new Dimension(1200,100));
      
      ShowImage.setIcon(new ImageIcon("image/start.jpg"));
      ShowImage.setHorizontalAlignment(SwingConstants.CENTER);
      
      startPanel= new JPanel();
      startPanel.setLayout(new BorderLayout());
      startPanel.add(ShowImage,BorderLayout.CENTER);
      startPanel.add(btn,BorderLayout.SOUTH);
      
      frame.addKeyListener(this);
      frame.add(startPanel);
      frame.setFocusable(true);
      frame.setVisible(true);
      
      
   }
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics g2d= (Graphics2D) g;
      handler.DrawAll(g2d);
      
      
   }
   class MyActionListener implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
         // TODO Auto-generated method stub
         JButton bt=(JButton)e.getSource();
         if(bt==btn) {
          
            new Thread(new GameThread()).start();
            frame.remove(startPanel);
            frame.add(aaa);
            frame.setVisible(true);
         }
      }
      
   }
   class GameThread implements Runnable{

      @Override
      public void run() {
         // TODO Auto-generated method stub
         for(;;) {
            if(!handler.ReturnGameOver()) {
               handler.gameTiming();
               handler.GameInput();
               repaint();
            }else {
               handler.drawGameOver();
               if(handler.ReturnGameOver())
                  break;
            }
      
            
         }
         System.exit(0);
      }
      
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
     aaa=new SpaceInavderLast();
   }


   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }


   @Override
   public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
      switch(e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
         Player.setRight(true);
         
         break;
      case KeyEvent.VK_LEFT:
         Player.setLeft(true);
         break;
      case KeyEvent.VK_UP:
         Player.setUp(true);
         break;
      case KeyEvent.VK_DOWN:
         Player.setDown(true);
         break;
      case KeyEvent.VK_SPACE:
         handler.PlayerBullet(Player);
         
         break;
      }
   }


   @Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      switch(e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
         Player.setRight(false);
         break;
      case KeyEvent.VK_LEFT:
         Player.setLeft(false);
         break;
      case KeyEvent.VK_UP:
         Player.setUp(false);
         break;
      case KeyEvent.VK_DOWN:
         Player.setDown(false);
         break;
         
      }
   }

}