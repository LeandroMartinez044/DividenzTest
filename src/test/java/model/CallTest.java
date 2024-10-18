package model;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

public class CallTest {
    
    @Test
    public void create_ok() throws Exception {
        final Call call = new Call();
        
        Field field = Call.class.getDeclaredField("duration");
        field.setAccessible(true); 
        int duration = (int) field.get(call);
        
        boolean condition = duration >= 5 && duration <= 10;
        assertTrue("duration must be between 5 and 10", condition);
    }
}

