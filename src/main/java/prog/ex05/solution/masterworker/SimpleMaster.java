package prog.ex05.solution.masterworker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import prog.ex05.exercise.masterworker.Master;
import prog.ex05.exercise.masterworker.Task;
import prog.ex05.exercise.masterworker.TaskState;
import prog.ex05.exercise.masterworker.Worker;

/**
 * Simple and straight-forward implementation of the Master interface.
 */
public class SimpleMaster implements Master {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleMaster.class);

  private Map<String, SimpleWorker> workerMap;
  private Map<Integer, Task> taskMap;
  private ConcurrentLinkedQueue<Task> taskQueue;

  /**
   * Construct new SimpleMaster instance.
   *
   * @param numberOfWorkers to be used for this SimpleMaster
   */
  public SimpleMaster(int numberOfWorkers) {
    if (numberOfWorkers < 1) {
      throw new IllegalArgumentException("Number of workers can not be smaller than 1!");
    }

    taskQueue = new ConcurrentLinkedQueue<>();
    taskMap = new HashMap<>();
    workerMap = new HashMap<>();

    for (int i = 0; i < numberOfWorkers; i++) {
      SimpleWorker simpleWorker = new SimpleWorker("SimpleWorker" + i);
      simpleWorker.setQueue(taskQueue);
      workerMap.put(simpleWorker.getName(), simpleWorker);
      simpleWorker.start();
    }

  }

  @Override
  public Task addTask(final Runnable runnable) throws IllegalArgumentException {
    if (runnable == null) {
      throw new IllegalArgumentException("runnable should not be null!");
    }
    Task taskToBeAdded = new Task(runnable);
    taskMap.put(taskToBeAdded.getId(), taskToBeAdded);
    taskQueue.add(taskToBeAdded);
    return taskToBeAdded;
  }

  @Override
  public TaskState getTaskState(final int taskId) throws IllegalArgumentException {
    if (taskMap.containsKey(taskId)) {
      return taskMap.get(taskId).getState();
    }
    throw new IllegalArgumentException("Illegal Argument, Task was not found!");
  }

  @Override
  public Task getTask(final int taskId) throws IllegalArgumentException {
    if (taskMap.containsKey(taskId)) {
      return taskMap.get(taskId);
    }
    throw new IllegalArgumentException("Illegal Argument, Task was not found!");

  }

  @Override
  public int getNumberOfWorkers() {
    return workerMap.size();
  }

  @Override
  public List<String> getWorkerNames() {
    return new ArrayList<>(workerMap.keySet());
  }

  @Override
  public int getNumberOfQueuedTasks() {
    return this.taskQueue.size();
  }

  @Override
  public void shutdown() {
    for (SimpleWorker worker : workerMap.values()) {
      worker.terminate();
      try {
        worker.join();
      } catch (InterruptedException e) {
        System.err.print(e.getMessage());
      }
    }
  }
}
