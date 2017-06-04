import java.util.*;

/**
 * Created by ferenc on 2017.06.02..
 */
public class Timer extends Thread {
    private static Map<String, Timer> timerMap = new HashMap<>();
    private static Map<String, Thread> threadMap = new HashMap<>();
    private String name;
    private int id;
    private int seconds;

    public Timer(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public void run(){
        try{
            while(true) {
                Thread.sleep(1000);
                seconds += 1;
            }
        }catch (InterruptedException e){
            return;
        }
    }


    public void check(){
        System.out.println("Name: " + name + ", Thread ID: " + id + ", Seconds: " + seconds);
    }


    public static void main(String[] args) throws InterruptedException {
        int id = 0;
        while(true) {
            Scanner in = new Scanner(System.in);
            String input = null;
            input = in.next();
            String command = input.split("-")[0];
            if (command.equals("start")) {
                String name = input.split("-")[1];
                if (threadMap.containsKey(name)) {
                    for (String key : timerMap.keySet()) {
                        if (key.equals(name)) {
                            Timer timer = timerMap.get(key);
                            //
                            threadMap.remove(name);
                            //
                            Thread thread = new Thread(timer);
                            threadMap.put(name, thread);
                            thread.start();
                        }
                    }
                } else {
                    id += 1;
                    Timer timer = new Timer(name, id);
                    timerMap.put(name, timer);
                    Thread thread = new Thread(timer);
                    threadMap.put(name, thread);
                    thread.start();
                }

            } else if(command.equals("check")) {
                if (input.length() < 6) {
                    for (Map.Entry pair : timerMap.entrySet()) {
                        Timer timer = (Timer) pair.getValue();
                        timer.check();
                    }
                } else {
                    String name = input.split("-")[1];
                    for (Map.Entry pair : timerMap.entrySet()) {
                        Timer timer = (Timer) pair.getValue();
                        if (timer.name.equals(name)) {
                            timer.check();
                        }
                    }
                }
            }
            else if(command.equals("stop")) {
                String name = input.split("-")[1];
                for (String key : threadMap.keySet()) {
                    if (key.equals(name)) {
                        Thread thread = threadMap.get(key);
                        thread.interrupt();
                    }
                }

            } else if(command.equals("exit")) {
                for (Map.Entry pair : timerMap.entrySet()) {
                    Timer timer = (Timer) pair.getValue();
                    timer.check();
                }
                System.exit(1);
            } else {
                System.out.println("Incorrect command");
            }
        }
    }
}