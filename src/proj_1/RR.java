package proj_1;

import java.util.*;
/**
 * RR (Round Robin) scheduling algorithm.
 */
public class RR implements Algorithm {
    // list to hold all processes to be scheduled
    private final Queue<Process> readyQueue;
    // list of processes that either have not arrived
    // or have not been scheduled
    private final Queue<Process> processesToSchedule;
    // total number of processes in this simulation
    private final int totalNumProcesses;
    private final int timeQuantum;
    private int currentTime;

    // constructor
    public RR(List<Process> allProcessList) {
        // initialize the ready queue using a LinkedList
        readyQueue = new LinkedList<>();
        // initialize the processes to schedule queue using a LinkedList
        // and populate it with the provided list of processes
        processesToSchedule = new LinkedList<>();
        // initialize the total number of processes
        totalNumProcesses = allProcessList.size();
        // add all processes to the 'processes to schedule' queue
        for (Process p : allProcessList) {
            processesToSchedule.add(p);
        }
        // set time quantum
        this.timeQuantum = 10;
        // initialize current time to 0
        currentTime = 0;
    }

    // method to start scheduling process
    public void schedule() {
        System.out.println("Round Robin Scheduling (Time Quantum: " + timeQuantum + ")\n");
        int totalWaitingTime = 0;
        int currentTime = 0;
        // as long as there are processes to schedule or processes in the ready queue
        while (!processesToSchedule.isEmpty() || !readyQueue.isEmpty()) {
            // as long as there are processes to schedule and the next process has arrived
            while (!processesToSchedule.isEmpty() && processesToSchedule.peek().getArrivalTime() <= currentTime) {
                // add the next process to the ready queue
                readyQueue.add(processesToSchedule.remove());
            }
            // if there are processes in the ready queue
            if (!readyQueue.isEmpty()) {
                // remove the next process from the ready queue
                Process currentProcess = readyQueue.remove();
                // calculate waiting time of the selected process
                int wTime = Math.max(0, currentTime - currentProcess.getArrivalTime());
                // update total waiting time
                totalWaitingTime += wTime;

                // run the selected process for the specified time quantum
                int remainingBurstTime = Math.min(timeQuantum, currentProcess.getCPUBurstTime());
                // fast-forward to the arrival time of the next task
                System.out.printf("Start running %s at time %d\n", currentProcess, currentTime);
                // update current time
                currentTime += remainingBurstTime;

                // update the burst time of the current process
                currentProcess.setBurst(currentProcess.getCPUBurstTime() - remainingBurstTime);

                // if the process has not finished executing
                if (currentProcess.getCPUBurstTime() > 0) {
                    // add the process back to the ready queue
                    readyQueue.add(currentProcess);
                } else {
                    // if the process has finished executing
                    System.out.println(currentProcess.getName() + " finished at time " +
                            currentTime + ". Its waiting time is: " + wTime);
                }
            } else {
                // fast-forward to the arrival time of the next task
                currentTime = Math.max(currentTime, processesToSchedule.peek().getArrivalTime());
            }
        }

        // calculate average waiting time
        double averageWaitingTime = totalWaitingTime / (double) totalNumProcesses;
        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);
    }


    // method to select the next process to execute (not used for RR scheduling)
    public Process pickNextProcess() {
        return null;
    }
}
