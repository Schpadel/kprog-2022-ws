package livesession.snake.provider;

/**
 * Simple implementation of the GameLoop interface for the game snake.
 */
public class SimpleGameLoop extends Thread implements GameLoop {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleGameLoop.class);
  boolean stopped;
  boolean shouldBePaused;
  private ExtendedSnakeService service;
  private int sleepTime;

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
    this.start();
  }

  @Override
  public void run() {

    while (!stopped) {
      checkIfPaused();

      logger.info("Advancing snake via GameLoop!");

      service.triggeredByGameLoop();

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
    synchronized (service) {
      service.notify();
    }
  }

  @Override
  public void stopGame() {
    resumeGame();
    this.stopped = true;
  }
}
