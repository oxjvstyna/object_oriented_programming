package agh.ics.oop;

import agh.ics.oop.model.util.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final Simulation simulation;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(Simulation simulation) {
        this.simulation = simulation;
    }

    public void runSync(){
        simulation.run();
    }

    public void runAsync(){
        Thread thread = new Thread(simulation);
        threads.add(thread);
        thread.start();
        awaitSimulationEnd();
    }


    public void runAsyncInThreadPool(){
        threadPool.submit(simulation);
        awaitSimulationEnd();
    }
    public void awaitSimulationEnd(){
        try{
            for(Thread thread : threads){
                thread.join();
            }
            threadPool.shutdown();
            if(!threadPool.awaitTermination(1, TimeUnit.HOURS)){
                threadPool.shutdownNow();
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
