import java.awt.*;
import java.util.Random;

public class Asteroid extends Sprite{
    public Asteroid(){
        Random rand=new Random();
        x= rand.nextInt(Main.scrX -160);
        y=-200;
        if(rand.nextBoolean())
            dx=rand.nextInt(4);
        else
            dx=-rand.nextInt(7);
        dy=rand.nextInt(3,7);
        width=160;height=160;
        image=getImage("src/assets/asteroid.png");
        rect =new Rectangle(x,y,width,height);
    }

}
