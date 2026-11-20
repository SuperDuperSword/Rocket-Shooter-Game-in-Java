import javax.swing.*;
import java.awt.*;

/**
 * This is an {@code abstract class} whose inheritors' instances are sprites (objects, projectiles, characters, etc.)
 */
public abstract class Sprite {
    public int x;public int y;public int dx=0;public int dy=0;public Image image;public int width;public int height;
    public Rectangle rect;
    public Image getImage(String path){
        ImageIcon icon=new ImageIcon(path);
        return icon.getImage();
    }
    public void move(){
        x+=dx;
        y+=dy;
        rect.x=x;
        rect.y=y;
    }
}
