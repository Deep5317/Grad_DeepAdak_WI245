package BikeRacingGame;

import java.util.*;
import java.util.concurrent.*;

class RaceResult {
    String name;
    double speed;
    long finishTime;

    RaceResult(String name,double speed,long finishTime){
        this.name=name;
        this.speed=speed;
        this.finishTime=finishTime;
    }
}

class Racer implements Callable<RaceResult>{

    private String name;
    private double speed;
    private int distance;
    private CountDownLatch startLatch;

    public Racer(String name,int distance,CountDownLatch latch){
        this.name=name;
        this.distance=distance;
        this.startLatch=latch;

        this.speed = 5 + new Random().nextInt(51);
    }

    public RaceResult call() throws Exception{

        startLatch.await();

        int covered=0;
        int nextMilestone=100;

        long start=System.currentTimeMillis();

        while(covered < distance){

            Thread.sleep(1000);
            covered += speed;

            if(covered >= nextMilestone){
                System.out.println(
                    name+" crossed "+nextMilestone+" meters"
                );
                nextMilestone+=100;
            }
        }

        long finish=System.currentTimeMillis();

        System.out.println(name+" FINISHED!");

        return new RaceResult(name,speed,finish-start);
    }
}

public class Race {

    public static void main(String[] args) throws Exception{

        Scanner sc=new Scanner(System.in);

        System.out.print("Enter number of racers: ");
        int n=sc.nextInt();
        sc.nextLine();

        System.out.print("Enter race distance (Km): ");
        int distance=sc.nextInt();
        sc.nextLine();

        ExecutorService executor=Executors.newFixedThreadPool(n);

        CountDownLatch startLatch=new CountDownLatch(1);

        List<Future<RaceResult>> list=new ArrayList<>();

        for(int i=1;i<=n;i++){
            System.out.print("Enter racer "+i+" name: ");
            String name=sc.nextLine();

            list.add(
                executor.submit(
                    new Racer(name,distance*1000,startLatch)
                )
            );
        }
        Thread.sleep(500);

        System.out.println("\nGet Ready...");
        Thread.sleep(1000);
        System.out.println("3");
        Thread.sleep(1000);
        System.out.println("2");
        Thread.sleep(1000);
        System.out.println("1");
        Thread.sleep(1000);

        System.out.println("\n GO!!!\n");

       
        startLatch.countDown();

        List<RaceResult> results=new ArrayList<>();

        for(Future<RaceResult> f:list){
            results.add(f.get());
        }

        executor.shutdown();

        results.sort(Comparator.comparingLong(r->r.finishTime));

        System.out.println("\n===== LEADERBOARD =====");

        int rank=1;
        for(RaceResult r:results){
            System.out.println(
                "Rank "+rank++
                +" | "+r.name
                +" | Speed: "+r.speed+" m/s"
                +" | Time: "+r.finishTime/1000.0+" sec"
            );
        }
    }
}
