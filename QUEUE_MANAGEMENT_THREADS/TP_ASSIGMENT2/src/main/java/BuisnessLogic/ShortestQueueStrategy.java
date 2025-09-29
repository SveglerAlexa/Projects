package BuisnessLogic;

import DataModel.Server;
import DataModel.Task;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ShortestQueueStrategy implements Strategy {

    public void addTask(List<Server> servers, Task task){

        int min=Integer.MAX_VALUE;
        Server bestServer=servers.get(0); ///initializam cu primul server din list
        BlockingQueue<Task> t;
        for(Server server : servers){

            t=server.getTask();
            int nr=t.size(); ///cate elemente are coada

            if(nr<min)
            {
                bestServer=server;
                min=nr;
            }

        }

        bestServer.addTask(task); ///adaugam taskul in coada serverului

    }

}
