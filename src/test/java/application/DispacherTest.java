package application;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class DispacherTest {
    
    private Allocator allocator;
    private Dispatcher dispatcher;
    private ExecutorService executorService;

    @Before
    public void setup() throws Exception  {
        final int operatorCount = 4;
        final int supervisorCount = 3;
        final int directorCount = 3;
        allocator = new Allocator(operatorCount, supervisorCount, directorCount);
        
        dispatcher = new Dispatcher(allocator);

         //Reflection
         Field executorField = Dispatcher.class.getDeclaredField("executorService");
         executorField.setAccessible(true); 
         executorService = (ExecutorService) executorField.get(dispatcher);
         
    }

    
    @Test
    public void dispatchCall_ok()  throws Exception  {
        
        for (int i = 0; i < 10; i++) {
            dispatcher.dispatchCall();
        }

        // waits for the calls being processed
        executorService.shutdown(); 

        // wait for the threads to end.
        executorService.awaitTermination(20, TimeUnit.SECONDS); 
    }


    @Test
    public void dispatchCall_more10Calls() throws Exception {
        
        for (int i = 0; i < 12; i++) {
            dispatcher.dispatchCall();
        }

        // waits for the calls being processed
        executorService.shutdown(); 

        // wait for the threads to end.
        executorService.awaitTermination(20, TimeUnit.SECONDS); 
    }
}
