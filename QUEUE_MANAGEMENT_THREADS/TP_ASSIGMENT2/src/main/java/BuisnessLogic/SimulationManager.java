package BuisnessLogic;

import DataModel.Server;
import DataModel.Task;

import java.util.*;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {

    ///datele citite din interfata

    public int timeLimit; ///cat timp dureaza simularea
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public Scheduler.SelectionPolicy selectionPolicy;
    //boolean stop;

    ///entitate responsabila cu managemantul cozilor si distributia clientilor
    private Scheduler scheduler;
    private List<Task> generatedTasks;

    public SimulationManager(int timeLimit,int numberOfClients, int maxProcessingTime,int minProcessingTime,int minArrivalTime,int maxArrivalTime,int numberOfServers,String selection) {
        scheduler=new Scheduler(numberOfServers,numberOfClients);
        if(selection.equals("SHORTEST_QUEUE")) {
            this.selectionPolicy=Scheduler.SelectionPolicy.SHORTEST_QUEUE;
        }
        else if(selection.equals("SHORTEST_TIME")) {
            this.selectionPolicy=Scheduler.SelectionPolicy.SHORTEST_TIME;
        }
        scheduler.changeStrategy(selectionPolicy);

        this.timeLimit=timeLimit;
        this.numberOfClients=numberOfClients;
        this.maxProcessingTime=maxProcessingTime;
        this.minProcessingTime=minProcessingTime;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.numberOfServers=numberOfServers;

        generateNRandomTasks();
    }

    public void generateNRandomTasks() {
        generatedTasks=new ArrayList<>();
        Random rand =new Random();  ///generator de numere aleatoare

        for(int i=0;i<numberOfClients;i++) {

            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;

            /// timp aleator de procesare intre minProcessingTime si maxProcessingTime
            int serviceTime=rand.nextInt(maxProcessingTime-minProcessingTime+1)+minProcessingTime;

            Task t=new Task(i+1,arrivalTime,serviceTime);///cream un nou task cu id-ul clientului
            generatedTasks.add(t);

        }

        ///sortam dupa arrivalTime

        Collections.sort(generatedTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return Integer.compare(t1.getArrivalTime(), t2.getArrivalTime());
            }
        });

    }

    @Override
    public void run() {
        int currentTime=0;
        int totalServiceTime=0;
        int peakHour=0;
        int maxNrOfClientsPerQueue=0;
        for (Task t : generatedTasks) {

            totalServiceTime+=t.getServiceTime();
        }
        float avgServiceTime=totalServiceTime/(float)numberOfClients;
        Logger logger=Logger.getInstance();
        logger.log("SIMULARE PORNITA");

        while(currentTime<timeLimit) {

            Iterator<Task> iterator = generatedTasks.iterator();

            while (iterator.hasNext()) {
                Task t = iterator.next();

                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t); // trimitem task-ul la un server
                    //logger.log("TOTAL WAITING TIME: "+scheduler.getTotalWaitingTime());
                    iterator.remove();         // il eliminam din lista de asteptare
                }
            }

            ///Logam clientii care asteapta
            StringBuilder waiting = new StringBuilder("Waiting clients: ");
            for (Task t : generatedTasks) {
                waiting.append("(").append(t.getID()).append(",").append(t.getArrivalTime()).append(",").append(t.getServiceTime()).append("); ");
            }
            logger.log(waiting.toString());
            ///Logheaza timpul curent
            logger.log("Time: "+currentTime);
            int index = 1;
            int nrOfClients=0;

            for (Server server : scheduler.getServers()) {
                System.out.print("Queue " + index + ": ");
                String queue =server.afis();
                logger.log("Queue " + index + ": " +queue);
                index++;
                BlockingQueue<Task> t=server.getTask();
                for(Task task: t)
                {
                    nrOfClients++;
                }

            }
            if(nrOfClients>maxNrOfClientsPerQueue) {
                maxNrOfClientsPerQueue=nrOfClients;
                peakHour=currentTime;
            }

            logger.log("---------------------------------------------------------------");
            boolean coziGoale =true;
            for (Server server : scheduler.getServers()) {
                if(!server.getTask().isEmpty())
                    coziGoale =false;
            }

            if(coziGoale&&generatedTasks.isEmpty()) {
                scheduler.inchidereThreaduri(true);
                break;
            }

            currentTime++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
            ///apelez metoda de inchidere a treadurilor
            if(currentTime>=timeLimit) {
                scheduler.inchidereThreaduri(true);
            }

            float avgWaitingTime= scheduler.getTotalWaitingTime()/(float)numberOfClients;

            logger.log("Average waiting time: "+avgWaitingTime+", Peak hour: "+peakHour+", Average service time: "+avgServiceTime);
            logger.log("SIMULARE TERMINATA");
            logger.log("---------------------------------------------------------------------------------------------------------");
    }

}
