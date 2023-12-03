package proj_1;
import java.util.*;
// Non-preemptive SJF (Shortest-Job First) scheduling algorithm.

public class SJF implements Algorithm {
    // list to hold all processes to be scheduled
    private final List<Process> allProcessList;

    // constructor to initialize SJF scheduler with a list of processes
    public SJF(List<Process> allProcessList) {
        // initialize the allProcessList with a copy of the provided list
        this.allProcessList = new ArrayList<>(allProcessList);
    }

    // method to start the scheduling process
    public void schedule() {
        System.out.println("Shortest-Job First Scheduling\n");

        int totalWaitingTime = 0;
        int currentTime = 0;

        // sort the processes by their burst time
        List<Process> sortedProcesses = new ArrayList<>(allProcessList);
        // sort the processes by their burst time
        sortedProcesses.sort(Comparator.comparingInt(Process::getCPUBurstTime));

        // loop through the sorted processes
        for (Process currentProcess : sortedProcesses) {
            // if the current time is less than the arrival time of the current process
            int wTime = Math.max(0, currentTime - currentProcess.getArrivalTime());
            // add the waiting time to the total waiting time
            totalWaitingTime += wTime;

            // run the current process for its CPU burst time
            CPU.run(currentProcess, currentProcess.getCPUBurstTime());

            // update the current time
            currentTime += currentProcess.getCPUBurstTime();

            // print the current process's name, the current time, and its waiting time
            System.out.println(currentProcess.getName() + " finished at time " +
                    currentTime + ". Its waiting time is: " + wTime);
        }

        // calculate the average waiting time
        double averageWaitingTime = totalWaitingTime / (double) sortedProcesses.size();
        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);
    }


    // method to select the next process to execute
    // (not used for SJF scheduling)
    public Process pickNextProcess() {
        return null;
    }
}
