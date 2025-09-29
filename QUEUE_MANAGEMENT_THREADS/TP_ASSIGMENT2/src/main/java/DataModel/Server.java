package DataModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waintingPeriod;
    boolean stop=false;


    public Server()
    {
        waintingPeriod = new AtomicInteger(0);
        tasks = new LinkedBlockingQueue<Task>();
    }

    public void addTask( Task newTask)
    {
        tasks.add(newTask);
        waintingPeriod.addAndGet(newTask.getServiceTime()); ///adaugam serviceTimeul noului Task
    }

    public void run()
    {
        while(!stop)
        {
            Task nextTask = tasks.peek();
            if(nextTask != null) {
                int processingTime = nextTask.getServiceTime();
                while (processingTime > 1) {

                    processingTime--;
                    nextTask.setServiceTime(processingTime);
                    waintingPeriod.decrementAndGet();///scadem din waitingPeriod global
                    try {
                        Thread.sleep( 1000); ///sleep 1 secunda pentru fiecare iteratie
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

                waintingPeriod.decrementAndGet();
                ///Taskul a fost procesat complet
                tasks.poll();///scoatem task-ul din coada
            }

            else{
                try {
                    Thread.sleep(1000); ///pauza cand coada e goala
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public BlockingQueue<Task> getTask()
    {
        return tasks;
    }

    public AtomicInteger getWaintingPeriod() {
        return waintingPeriod;
    }

    public String afis()
    {

        StringBuilder coada = new StringBuilder();
        if (tasks.isEmpty()) {
            coada.append("closed");
        } else {
            for (Task task : tasks) {
                coada.append("(")
                        .append(task.getID())
                        .append(", ")
                        .append(task.getArrivalTime())
                        .append(", ")
                        .append(task.getServiceTime())
                        .append(") ");
            }
        }
        return coada.toString();
    }

}
