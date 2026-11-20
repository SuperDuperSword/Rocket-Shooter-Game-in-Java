import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Board extends JPanel implements ActionListener {
    
    boolean lose=false;
    AsteroidThread astThread=new AsteroidThread();
    public Board() {
        initBoard();
        astThread.start();
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        Font font=new Font("font",Font.PLAIN,50);
        g.setFont(font);
        FontMetrics fm=g.getFontMetrics();
        super.paintComponent(g);//I think this is very important
        g.drawImage(Main.getImage("src/assets/space.jpg"),
                0,0, Main.scrX, Main.scrY,null);//background
        if(!lose) {
        
//            ship.draw(g);
            for (Asteroid ast : Main.asteroids) {
                g.drawImage(ast.image,ast.x, ast.y, ast.width,ast.height,null);//something to draw
            }
            for(Bullet b:Main.bullets){
                g.drawImage(b.image,b.x,b.y,b.width,b.height,null);
            }g.drawImage(Main.rocket,ship.x, ship.y, ship.width,ship.height,null);//something to draw
            
        }else {//player died
            g.drawString(deathMessage,Main.scrX /2-fm.stringWidth(deathMessage)/2,Main.scrY /2);
            g.drawString("Highscore: "+readFile("src/assets/highScore.txt"),Main.scrX /2-fm.stringWidth("Highscore: "+readFile
                    ("src/assets/highScore.txt"))/2,Main.scrY /2+fm.getHeight());
            if(highScore) {
                g.setColor(Color.yellow);
                g.drawString("Highscore!",Main.scrX /2-fm.stringWidth("Highscore!")/2,Main.scrY /2-fm.getHeight());
                g.setColor(Color.white);
            }
        }
        g.drawString("Score: "+score,Main.scrX /2-fm.stringWidth("Score: "+score)/2,50);
        int reload= System.currentTimeMillis()>lastShootTime+ COOLDOWN *score ?100: (int) (((System.currentTimeMillis() - lastShootTime) / score) );
        g.drawString("Reload: "+reload+"%",Main.scrX /2-fm.stringWidth("Reload: "+reload+"%")/2,65+fm.getHeight());
        Toolkit.getDefaultToolkit().sync();
    }final long COOLDOWN=100L;
    Clip hit;
    /**
     * this method saves a string to a path
     * @param toSave the string to save
     * @param path the path and name
     */
    static void saveFile(String toSave, String path){
        try {
            Files.write( Paths.get(path), toSave.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * this method reads a file from a path
     * @param path the path
     * @return the contents of the file at path {@code path}
     */
    static String readFile(String path){
        File file = new File(path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder out= new StringBuilder();
        while (sc!=null&&sc.hasNextLine())
            out.append(sc.nextLine());
        return out.toString();
    }
    static int score=0;
//    int passedAsts=0;
    boolean highScore=false;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!lose) {
            ship.move();
            ArrayList<Asteroid>astsToRemove=new ArrayList<>();
            ArrayList<Bullet> bulletsToRemove=new ArrayList<>();
            
            for (Asteroid ast : Main.asteroids) {// ----------------------------------------
                ast.move();
                if (ast.rect.intersects(ship.rect)) {//player loses
                    lose=true;
                    music.stop();
                    loseSound.start();
                    deathMessage="You lose!";
                    astThread.interrupt();
                    if(score> Integer.parseInt(readFile("src/assets/highScore.txt"))) {
                        highScore = true;
                        saveFile(""+score,"src/assets/highScore.txt");
                    }
                    repaint();
                }
                for(Bullet bullet:Main.bullets){
                    
                    if(bullet.rect.intersects(ast.rect)){
                        astsToRemove.add(ast);
                        bulletsToRemove.add(bullet);score++;
                        playSound(hit);
                    }
                    if(bullet.y<-250)
                        bulletsToRemove.add(bullet);
                }
                if(ast.y>Main.scrY+5) {
                    astsToRemove.add(ast);
                }
            }//            ---------------------------------------------
            for(Bullet bullet:Main.bullets){
                bullet.move();
            }
            Main.asteroids.removeAll(astsToRemove);
            Main.bullets.removeAll(bulletsToRemove);
            if(ship.x<0)
                ship.x=0;
            if(ship.x>Main.scrX-ship.width)
                ship.x=Main.scrX-ship.width;
            // -----------------------------------
            repaint();
        }
    }
    static boolean spacePressed=false;
    String deathMessage="";
    
    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyReleased(KeyEvent e) {
            if(!lose) {
                ship.keyReleased(e);
                if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (!lose) {
                ship.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShootTime >= COOLDOWN *score) {
                        Main.bullets.add(new Bullet());
                        spacePressed = true;
                        playSound(shoot);
                        lastShootTime = currentTime;
                    }
                }
            }
        }
    }
    static long lastShootTime=0;
    private Timer timer;
    public static Ship ship;
    private final int DELAY = 10;
    Clip music;
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());
        
        setFocusable(true);
        
        ship = new Ship();
        shoot=getSound("src/assets/shoot.wav");
        music=getSound("src/assets/music.wav");
        music.loop(Clip.LOOP_CONTINUOUSLY);
        loseSound=getSound("src/assets/lose.wav");
        hit=getSound("src/assets/hit.wav");
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    Clip loseSound;
    
    Clip shoot;
    Clip getSound(String path) {
        try {
            System.out.println(path);
            File explosionFile = new File(path);
            System.out.println(1);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(explosionFile);
            System.out.println(2);
            Clip sound = AudioSystem.getClip();
            System.out.println(3);
            if (audioIn == null) {
                System.out.println("audio in is null");
            }
            sound.open(audioIn);
            System.out.println(4);
            return sound;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getClass());
            e.printStackTrace();
        }
        //line unavailable
        return null;
    }
    void playSound(Clip sound){
        if(sound.isRunning())
            sound.stop();
        Main.sleep(5);
        sound.setMicrosecondPosition(0);
        sound.start();
    }
}
