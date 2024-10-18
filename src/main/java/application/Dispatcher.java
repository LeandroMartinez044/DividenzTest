package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang3.Validate;

import model.Call;
import model.EmployeeRole;

/*
 * Dispatcher is in charge of dispatching the calls.
 */
public final class Dispatcher {

    /* Pools number. Each thread is available call concurrent. */
    final private int callsAvailable = 10;

    /* The allocator is in charge of assigning and release an employee role available to a call.*/
    final private Allocator allocator;
    
    /**
    * executorService manages the pool of threads,
    * providing methods to submit tasks for asynchronous execution.
    */
    final private ExecutorService executorService;
    
    /**
    * semaphore controls access to a set of resources by multiple threads.
    */
    final private Semaphore semaphore;    
    
    /**
     * Constructor.
     * 
     * @param theAllocator the allocator. cannot be null.
     */
    public Dispatcher(final Allocator theAllocator) {
        Validate.notNull(theAllocator, "the allocator cannot be null.");
        allocator = theAllocator;
        executorService = Executors.newFixedThreadPool(callsAvailable);
        semaphore = new Semaphore(callsAvailable);
    }

    /**
     * Executes the process of calling. Each thread is attending to a call.
     * in case there aren't any thread available to attend a call, waits until some it releases.
     */
    public void dispatchCall() {

        try {
            semaphore.acquire();
            executorService.submit(this::processCall); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        
        } finally {
            semaphore.release();

        }
        
    }

    /**
     * Look for an available employee role for the call.
     * Execute the called and when the called finished it releases the employee role.
     */
    private void processCall() {
        try {
            EmployeeRole role = allocator.getEmployeeRoleAvailable();
            Call call = new Call();
            
            call.process();
        
            System.out.println("Thread: " + Thread.currentThread().getName() + " finished -" + " Rol released: " +  role); 
            
            allocator.release(role);
        
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }       
    }
}
