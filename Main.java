import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {
    static Image rocket;
    public Main(){
        rocket=getImage("src/assets/Rocket.png");
        initUI();
    }
    public static int speed=5;
    public static ArrayList<Asteroid> asteroids=new ArrayList<>();
    public static ArrayList<Bullet> bullets=new ArrayList<>();
    
    static Image getImage(String path){
        ImageIcon icon=new ImageIcon(path);
        Image img=icon.getImage();
        return img;
    }
    static void sleep(int milli){
        try {
            Thread.sleep(milli);
        }
        catch (Exception ignored) {
        }
    }
    public static int scrX =1400;public static int scrY =800;
    
    private void initUI() {
        
        add(new Board());
        
        setSize(1400, 800);
        setTitle("Rocket Shooter Game!");
        setIconImage(rocket);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public static void main(String []args){
        EventQueue.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}//end of class _______________________________________________________________________________________
