import java.awt.*;

public class Iron extends Sprite {
    public Iron(int x,int y){
        image=getImage("src/assets/iron.png");
        this.x=x;
        this.y=y;
        width=image.getWidth(null);
        height=image.getHeight(null);
        rect=new Rectangle(x,y,width,height);
    }
}
