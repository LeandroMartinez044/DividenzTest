package application;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import model.EmployeeRole;

public class AllocatorTest {
    
    private Allocator allocator;

    @Before
    public void setup() {
        allocator = new Allocator(4, 3, 3);
    }

    @Test
    public void getEmployeeRoleAvailable_ok()  throws Exception  { 
        EmployeeRole role = allocator.getEmployeeRoleAvailable();

        assertEquals(EmployeeRole.OPERATOR, role);

        Field field = Allocator.class.getDeclaredField("operatorCount");;
        field.setAccessible(true); 
        AtomicInteger operatorCount = (AtomicInteger) field.get(allocator);

        assertEquals(3, operatorCount.intValue()); 
    }

    @Test
    public void release_ok()  throws Exception  { 
        allocator.release(EmployeeRole.OPERATOR);

        Field field = Allocator.class.getDeclaredField("operatorCount");
        field.setAccessible(true); 
        AtomicInteger operatorCount = (AtomicInteger) field.get(allocator);

        assertEquals(5, operatorCount.intValue()); 
    }

    @Test(expected = NullPointerException.class)
    public void release_whenEmployeeRoleIsNull(){ 
        allocator.release(null);        
    }
}



