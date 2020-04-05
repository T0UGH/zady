package com.edu.neu.zady.pojo;

import com.edu.neu.zady.ZadyApplicationTests;
import org.junit.jupiter.api.Test;

public class BacklogTest extends ZadyApplicationTests {
    @Test
    void testStatusConverter(){
        Backlog.Status.valueOf("xxx");
    }
}
