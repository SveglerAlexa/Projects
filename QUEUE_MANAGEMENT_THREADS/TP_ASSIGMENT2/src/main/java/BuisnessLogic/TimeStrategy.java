package BuisnessLogic;

import DataModel.Server;
import DataModel.Task;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeStrategy implements Strategy {

    public void addTask(List<Server> servers, Task task){

        int min=99999999;
        Server bestServer=servers.get(0); ///initializam cu primul server din list
        BlockingQueue<Task> t;
        for(Server server : servers){

            int totalTime=(server.getWaintingPeriod()).get(); ///getWaintingPeriod returneaza un AtomicInteger deci trebuie sa chem .get() pentru a obtine valoarea numerica


            if(totalTime<min)
            {
                bestServer=server;
                min=totalTime;
            }

        }

        bestServer.addTask(task); ///adaugam taskul in coada serverului

    }
}
