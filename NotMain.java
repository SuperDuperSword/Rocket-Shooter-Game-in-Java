import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NotMain {
    static MyFrame frame=new MyFrame();
    static Graphics win= frame.getGraphics();
    static  BufferedImage rocket=getImage("assets/Rocket.png");
    static BufferedImage getImage(String path){
         BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            img=null;
        }
        return img;
    }
    
    static void sleep(int milli){
        try {
            Thread.sleep(milli);
        }
        catch (Exception ignored) {
        }
    }
    public static void paint(){
        win.drawLine(0,0,500,500);
        win.drawRect(170,150,600,500);
        win.drawImage(rocket,100,100,500,700,null);
    }
    
    public static void main(String []args){
        sleep(20);//important
        paint();
        
    }//end of main meth
    
    static class MyFrame extends JFrame {
        public MyFrame() {
            setVisible(true);
            setSize(1400, 800);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        }
    }
    
}//end of class ----------------------------------------------------------------------------------------------

