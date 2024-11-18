package graph;

import static org.junit.Assert.*;
import java.util.Collections;
import org.junit.Test;

public abstract class GraphInstanceTest {

    // Testing strategy
    //   TODO

    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    

}
