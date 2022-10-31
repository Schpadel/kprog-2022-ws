package prog.ex05.solution.masterworker;


import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import prog.ex05.exercise.masterworker.Task;
import prog.ex05.exercise.masterworker.TaskState;
import prog.ex05.exercise.masterworker.Worker;

/**
 * Simple and straight-forward implementation of the Worker interface.
 */


public class SimpleWorker extends Thread implements Worker {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleWorker.class);
  boolean running;
  private Queue<Task> taskQueue;

  public SimpleWorker(String name) {
    super(name);
    running = true;
  }

  @Override
  public void run() {
    while (running) {

      Task taskToRun = taskQueue.poll();
      if (taskToRun == null) {
          waitUntilNotified();
          //Thread.sleep(Worker.WAIT_EMPTY_QUEUE);
          continue;
      }

      System.out.println("Thread " + getName() + " woke up and is now working!");
      try {
        taskToRun.setState(TaskState.RUNNING);
        taskToRun.getRunnable().run();
      } catch (Exception exception) {
        taskToRun.crashed(exception);
        taskToRun.setState(TaskState.CRASHED);
      }
      if (taskToRun.getState() == TaskState.RUNNING) {
        taskToRun.setState(TaskState.SUCCEEDED);
      }
    }
  }

  private void waitUntilNotified() {
    synchronized (taskQueue) {
      try {
        this.taskQueue.wait();
      } catch (InterruptedException e) {
        // Thread was interrupted
        logger.info("Thread was interrupted!");
      }
    }

  }

  @Override
  public void setQueue(final ConcurrentLinkedQueue<Task> queue) {
    this.taskQueue = queue;
  }

  @Override
  public void terminate() {
    this.running = false;
  }


}
