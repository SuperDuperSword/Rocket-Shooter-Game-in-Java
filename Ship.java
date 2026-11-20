import java.awt.*;
import java.awt.event.KeyEvent;

public class Ship extends Sprite{
    public Ship() {
        x = Main.scrX / 2 - 120 / 2;
        y = Main.scrY - 250;
        dx = 0;
        dy = 0;
        image=Main.rocket;
        rect=new Rectangle(x,y,120,200);
        width=120;
        height=200;
    }
    
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        int nx=0;int ny=0;
        if (key == KeyEvent.VK_LEFT) {
            dx=-Main.speed;
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            dx=Main.speed;
        }
        
        if (key == KeyEvent.VK_UP) {
            ny -=Main.speed;
        }
        
        if (key == KeyEvent.VK_DOWN) {
            ny +=Main.speed;
        }
        //dy=ny;
    }
    
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            dx =0;
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            dx =0;
        }
        
        if (key == KeyEvent.VK_UP) {
            dy =0;
        }
        
        if (key == KeyEvent.VK_DOWN) {
            dy =0;
        }
    }

}// ------------------------------------------------------------------------------
