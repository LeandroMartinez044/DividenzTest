package application;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;

import model.EmployeeRole;


/**
 * The allocator is in charge of assigning and release an employee role available to a call.
 * It increments and decreases the quantity operators, supervisors and directors 
 * when assigning or deallocating a role.
 */
public class Allocator {
    
    /**
    * Represent the operators number.
    * Type AtomicInteger avoids race condition.
    */
    private AtomicInteger operatorCount;
    
    /**
    * Represent the supervisors number.
    * Type AtomicInteger avoids race condition.
    */
    private AtomicInteger supervisorCount;
    
    /**
    * Represent the directors number.
    * Type AtomicInteger avoids race condition.
    */
    private AtomicInteger directorCount;



    /**
     * Constructor.
     * It must have at least one employee available amoung operators, supervisors or directors.
     * 
     * @param theOperatorCount represents operators number. it could be 0.
     * 
     * @param theSupervisorCount represents supervisors number. it could be 0.
     * 
     * @param theDirectorCount represents directors number. it could be 0.
     */
    public Allocator(final int theOperatorCount, 
                     final int theSupervisorCount,
                     final int theDirectorCount) {
        
        boolean condition = theOperatorCount > 0 || theSupervisorCount > 0 || theDirectorCount > 0;
        Validate.isTrue(condition, "it must have at least one employee.");
        
        operatorCount = new AtomicInteger(theOperatorCount);
        supervisorCount = new AtomicInteger(theSupervisorCount);                           
        directorCount = new AtomicInteger(theDirectorCount); 
    }

    /**
     * Searches for and obtains the employee role available to answer the call, 
     * starting with the OPERATOR. If none are available, 
     * it continues with the next SUPERVISOR role and then with the DIRECTOR.
     * 
     * When an available role is assigned, the value of the assigned role is decreased.    
     * 
     * 
     * @return an EmployeeRole. In case there's not a role available, return null.
     */
    public EmployeeRole getEmployeeRoleAvailable() {
    
        if (operatorCount.intValue() > 0) {
            operatorCount.decrementAndGet(); 
            return EmployeeRole.OPERATOR;
        } 
        
        if (supervisorCount.intValue() > 0) {
            supervisorCount.decrementAndGet(); 
            return EmployeeRole.SUPERVISOR;
        } 
        
        if (directorCount.intValue() > 0) {
            directorCount.decrementAndGet(); 
            return EmployeeRole.DIRECTOR;
        } 

        return null;
    }
    
    /**
     * When an employee role is released, the value of the attribue role is incremented.    
     *  
     * @param role to increment. It cannot be null.
     */
    public void release(final EmployeeRole role) {
        Validate.notNull(role, "the employee role cannot be null");
        
        switch (role) {
            case OPERATOR:
                operatorCount.incrementAndGet();
                break;
            case SUPERVISOR:
                supervisorCount.incrementAndGet();
              break;
            
            case DIRECTOR:
                directorCount.incrementAndGet();
              break;
        }
    }
}
