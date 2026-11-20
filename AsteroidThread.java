import java.util.Random;

/**
 * this thread creates asteroids*/
public class AsteroidThread extends Thread {
    public void run(){
        while (true){
            Main.sleep(new Random().nextInt(2700));
            for (int i=0;i<new Random().nextInt(1,3);i++)
                Main.asteroids.add(new Asteroid());
            
        }
    }
}
