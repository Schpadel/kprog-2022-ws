package livesession.snake.provider;

import java.util.Timer;
import java.util.TimerTask;
import livesession.snake.SnakeListener;

/**
 * Simple implementation of the GameLoop interface for the game snake.
 */
public class SimpleGameLoop extends Thread implements GameLoop {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleGameLoop.class);

  private ExtendedSnakeService service;
  private int sleepTime;
  boolean stopped;
  boolean shouldBePaused;

  /**
   * Constructor.
   *
   * @param service   ExtendedSnakeService to be notified every loop
   * @param sleepTime time between two notifications in milliseconds
   */
  public SimpleGameLoop(final ExtendedSnakeService service,
                        final int sleepTime) {
    this.service = service;
    this.sleepTime = sleepTime;
    this.stopped = false;
    this.shouldBePaused = false;
  }

  @Override
  public void run() {

    Timer timer = new Timer();

    while (!stopped) {
      checkIfPaused();

      // only move if velocity criteria
      logger.info("Advancing snake via GameLoop!");


      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          service.triggeredByGameLoop();
        }
      }, service.getConfiguration().getVelocityInMilliSeconds());



      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void checkIfPaused() {
    if (shouldBePaused) {
      synchronized (service) {
        try {
          service.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void pauseGame() {
    this.shouldBePaused = true;
  }

  @Override
  public void resumeGame() {
    this.shouldBePaused = false;
    service.notify();
  }

  @Override
  public void stopGame() {
    this.stopped = true;
  }
}
