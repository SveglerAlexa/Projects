package BuisnessLogic;

import DataModel.Server;
import DataModel.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers= new ArrayList<Server>();
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    private boolean stop=false;
    private int totalWaitingTime=0;

    public enum SelectionPolicy {
        SHORTEST_QUEUE,SHORTEST_TIME
    }

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        int i=0;
        for(i=0;i<maxNoServers;i++)
        {
            Server server = new Server();
            Thread thread = new Thread(server);
            thread.start();
            servers.add(server);
        }
    }


    void inchidereThreaduri(boolean stop)
    {
        for (Server server : servers) {
            server.setStop(stop);
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy= new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME)
        {
            strategy= new TimeStrategy();
        }
    }

    public synchronized void dispatchTask(Task task)
    {
        strategy.addTask(servers,task);
        for(Server server : servers)
        {
            for(Task t: server.getTask())
           {
                if(t==task)
                {
                   totalWaitingTime=totalWaitingTime+server.getWaintingPeriod().get()-t.getServiceTime();

               }
            }
        }
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public List<Server> getServers() {
        return servers;
    }
}
