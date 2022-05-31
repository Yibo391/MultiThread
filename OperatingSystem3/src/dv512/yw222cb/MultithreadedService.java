package dv512.yw222cb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * File:	dv512.yw222cb.MultithreadedService.java
 * Course: 	20HT - Operating Systems - 1DV512
 * Author: 	Yibo Wang yw222cb
 * Date: 	April 2022
 */

// TODO: put this source code file into a new Java package with meaningful name (e.g., dv512.YourStudentID)!

// You can implement additional fields and methods in code below, but
// you are not allowed to rename or remove any of it!

// Additionally, please remember that you are not allowed to use any third-party libraries

public class MultithreadedService {




  // this is 3 signs for thread status



  List<Process> list = new ArrayList<Process>();

  // TODO: implement a nested public class titled Process here
  // which must have an integer ID and specified burst time (duration) in milliseconds,
  // see below
  // Add further fields and methods to it, if necessary
  // As the task is being executed for the specified burst time,
  // it is expected to simply go to sleep every X milliseconds (specified below)
  public class Process implements Runnable {

    int id;
    long burstTime;
    long sleepTimeMs;
    String startTime;
    String finishTime;
    int label ; // for status
    // 0 means waiting
    // 1 means complete
    // 2 means interrupt
    Calendar calendar = Calendar.getInstance();
    //we use simpledateformat to format the date.
    SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss:SSS");




    public Process(int id, long burstTime, long sleepTimeMs) {
      this.id = id;
      label = 0;
      this.burstTime = burstTime;
      this.sleepTimeMs = sleepTimeMs;

    }



    @Override
    public void run() {
      long counters;
//      System.out.println(Thread.currentThread().getName()+"\t"+ getId());
      // record start time for every process
      startTime = timeOnly.format(calendar.getTime());
      counters = burstTime;

      while (!(label ==1)) {
        try {
          Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
          break;
        }

        counters -= sleepTimeMs;

        //if there is no time for this process then interrupt
        if (counters > 0) {
          label =2;
        } else {
          finishTime = getTime();
          // if there is enough time for a process then complete
          label=1;
        }
      }
    }


    public int getLabel() { return label;}

    public int getId() {
      return id;
    }
    public long gerBurstTime() {
      return burstTime;
    }
    public String getStartTime() {
      return startTime;
    }
    public String getTime() {
      return timeOnly.format(calendar.getTime());
    }
  }



  // Random number generator that must be used for the simulation
  Random rng;

  // ... add further fields, methods, and even classes, if necessary


  public MultithreadedService (long rngSeed) {
    this.rng = new Random(rngSeed);
  }


  public void reset() {
    // TODO - remove any information from the previous simulation, if necessary
    list.clear();
  }


  // If the implementation requires your code to throw some exceptions,
  // you are allowed to add those to the signature of this method
  public void runNewSimulation(final long totalSimulationTimeMs,
      final int numThreads, final int numTasks,
      final long minBurstTimeMs, final long maxBurstTimeMs, final long sleepTimeMs) {
    reset();

    // TODO:
    // 1. Run the simulation for the specified time, totalSimulationTimeMs
    // 2. While the simulation is running, use a fixed thread pool with numThreads
    // (see https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Executors.html#newFixedThreadPool(int) )
    // to execute Tasks (implement the respective class, see above!)
    // 3. The total maximum number of tasks is numTasks,
    // and each task has a burst time (duration) selected randomly
    // between minBurstTimeMs and maxBurstTimeMs (inclusive)
    // 4. The implementation should assign sequential task IDs to the created tasks (0, 1, 2...)
    // and it should assign them to threads in the same sequence (rather any other scheduling approach)
    // 5. When the simulation time is up, it should make sure to stop all of the currently executing
    // and waiting threads!

    //create a thread pool
    ExecutorService pool = Executors.newFixedThreadPool(numThreads);
    // generate task into pool
    for (int i = 0; i < numTasks; i++) {
      //generate burst time from min to max.
      long burstTime = minBurstTimeMs + ((long)(rng.nextDouble()*(maxBurstTimeMs-minBurstTimeMs)));

      //create a process
      Process process = new Process(i, burstTime, sleepTimeMs);
      // add a process into task list
      list.add(process);
      pool.submit(process);

    }

    pool.shutdown();
    try {
      Thread.sleep(totalSimulationTimeMs);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }


  }


  public void printResults() {
    System.out.println("| Process Id\t Burst time\t Start data\t Finish data");
    // TODO:

    // 1. For each *completed* task, print its ID, burst time (duration),
    // its start time (moment since the start of the simulation), and finish time

    // 2. Afterwards, print the list of tasks IDs for the tasks which were currently
    // executing when the simulation was finished/interrupted


    // 3. Finally, print the list of tasks IDs for the tasks which were taskList for execution,
    // but were never started as the simulation was finished/interrupted



    for (int i = 0; i <3 ; i++) {

      switch (i) {
        case 1:
          System.out.println("Completed tasks:");
          for (Process task : list) {
            if (task.getLabel()==1) {
              System.out.println(
                  "   " + task.getId() + "\t  " + task.gerBurstTime() + "\t\t  " + task
                      .getStartTime() + "\t  " + task.finishTime);
            }
          }
          break;
        case 2:
          System.out.println("Interrupted tasks:");

          for (Process task : list) {
            if (task.getLabel()==2) {
              System.out.println(
                  "   " + task.getId() + "\t  " + task.gerBurstTime() + "\t\t  " + task
                      .getStartTime());
            }
          }
          break;
        case 0:
          System.out.println("Waiting tasks:");
          for (Process task : list) {
            if (task.getLabel()==0) {
              System.out.println("   " + task.getId() + "\t  " + task.gerBurstTime());
            }
          }
          break;
      }
    }
    }



  // If the implementation requires your code to throw some exceptions,
  // you are allowed to add those to the signature of this method
  public static void main(String args[]) {
    // TODO: replace the seed value below with your birth date, e.g., "20001001"
    final long rngSeed = 20000904;

    // Do not modify the code below — instead, complete the implementation
    // of other methods!
    MultithreadedService service = new MultithreadedService(rngSeed);

    final int numSimulations = 3;
    final long totalSimulationTimeMs = 15*1000L; // 15 seconds

    final int numThreads = 4;
    final int numTasks = 30;
    final long minBurstTimeMs = 1*1000L; // 1 second
    final long maxBurstTimeMs = 10*1000L; // 10 seconds
    final long sleepTimeMs = 100L; // 100 ms

    for (int i = 0; i < numSimulations; i++) {
      System.out.println("Running simulation #" + i);

      service.runNewSimulation(totalSimulationTimeMs,
          numThreads, numTasks,
          minBurstTimeMs, maxBurstTimeMs, sleepTimeMs);

      System.out.println("Simulation results:"
          + "\n" + "----------------------");
      service.printResults();

      System.out.println("\n");
    }

    System.out.println("----------------------");
    System.out.println("Exiting...");
    System.exit(0);
    // If your program has not completed after the message printed above,
    // it means that some threads are not properly stopped! -> this issue will affect the grade
  }
}
