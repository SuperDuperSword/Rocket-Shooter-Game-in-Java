import java.awt.*;

public class Bullet extends Sprite{
    public Bullet(){
        y=Board.ship.y;
        image=getImage("src/assets/bullets.png");
        width=80;
        height=150;
        {
            //getting x
            int avg= (Board.ship.x+Board.ship.x+Board.ship.width)/2;
            x=avg-width/2;
        }
        dx=0;
        dy=-7;
        rect=new Rectangle(x,y,width,height);
    }
    
}// --------------------------------------------------
