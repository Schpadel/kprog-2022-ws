package prog.ex05.solution.masterworker;


import java.util.Queue;
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

      if (!taskQueue.isEmpty()) {

        Task taskToRun = taskQueue.poll();
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
      } else {
        try {
          Thread.sleep(Worker.WAIT_EMPTY_QUEUE);
        } catch (InterruptedException e) {
          System.err.print(e.getMessage());
        }
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
