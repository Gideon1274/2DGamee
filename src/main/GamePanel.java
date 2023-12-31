package main;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
public class GamePanel extends JPanel implements Runnable{
    
    final int originalTilzeSize = 16;
    final int scale = 3;

    public final int tileSize = originalTilzeSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow= 12;
    public final int screenWidth = tileSize*maxScreenCol;
    public final int screenHeight = tileSize*maxScreenRow;


    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    public final int FPS = 60;
    
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();

    Sound music = new Sound();
    Sound se = new Sound();

    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // entity and object
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }

    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }
    //  public void run(){
    //         double drawInterval = 1000000000/FPS;
    //         double nextDrawTime = System.nanoTime()+drawInterval;
    
    //         while(gameThread!=null){
    //             // long currentTime = System.nanoTime();
    //             // System.out.println("CurrentTime: "+currentTime);
    
                
    //             update();
    //             repaint();
                
    //             try {
    //                 double remainingTime = nextDrawTime - System.nanoTime();
    //                 remainingTime = remainingTime/1000000;
    
    //                 if(remainingTime<0){
    //                     remainingTime=0;
    //                 }
    //                 Thread.sleep((long)remainingTime);
    //                 nextDrawTime+=drawInterval;
    //             }catch(InterruptedException e){
    //                 e.printStackTrace();
    //             }
                
    
    //         }
    //     }
    
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        // int drawCount =0;

    
        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime) / drawInterval;
            timer+=(currentTime-lastTime);

            lastTime = currentTime;
            if (delta>=1){
                update();
                repaint();
                delta--;
                // drawCount++;

            }
            if(timer>=1000000000){
                
                // drawCount=0;
                timer=0;
            }
        }
    }

    public void update(){
       player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        
        //debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
        drawStart = System.nanoTime();
        }

        //tile
        tileM.draw(g2);

        //object
        for(int i=0;i < obj.length;i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        //player
        player.draw(g2);
        
        //ui
        ui.draw(g2);


            //debug
            if(keyH.checkDrawTime==true){
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time: "+passed, 10,400);
        System.out.println("Draw Time: "+passed);
            }
        g2.dispose();
        
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
