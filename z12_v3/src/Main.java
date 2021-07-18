import java.util.Random;
import java.util.concurrent.*;

class Car implements Runnable
{
    private static final BlockingQueue<Car> parking = new ArrayBlockingQueue<>(4);
    private static int carCounter = 0;
    private final String name = "Car " + carCounter++;
    private static final Random random = new Random();
    private final int waitingTime = random.nextInt(500) + 200;
    private final int parkingTime = random.nextInt(1000) + 300;
    public Car()
    {
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("Машина " + name + " ожидает парковочное место");
            if(parking.offer(this,waitingTime,TimeUnit.MILLISECONDS))
            {
                System.out.println("Машина " + name + " получила парковочное место");
                Thread.sleep(parkingTime);
                parking.remove(this);
                System.out.println("Машина " + name + " освободила место");
            }
            else
            {
                System.out.println("Машина  " + name + " не дождалась места и уехала");
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
public class Main
{
    public static void main(String[] args)
    {
        for (int i = 0; i < 8; i++)
            new Car();
	}
}
