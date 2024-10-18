package model;

/**
 * Represents a call. 
 * The call duration must be between 5 and 10 seconds.
 */
public final class Call {
    
    /**
    * Represent the call duration. It must be between 5 or 10 seconds.
    */
    private int duration;

   /**
   * Constructor. 
   * 
   * The filed duration is generated automatically between a random number between 5 and 10.
   *
   **/
    public Call() {
        duration = (int) (Math.random() * 6 + 5);
    }

   /**
    * Processes the call. 
    * Waits the call duration.
    *
    * @throws InterruptedException
    */
    public void process() throws InterruptedException {
        
        //Call duration
        Thread.sleep(duration * 1000);
    }
}
