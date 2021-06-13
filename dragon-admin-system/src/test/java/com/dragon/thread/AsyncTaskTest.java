package com.dragon.thread;

import com.dragon.system.DragonAdminApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DragonAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AsyncTaskTest {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskTest.class);

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void asyncTest() {
        for (int i = 0; i < 100; i++) {
            asyncTask.doTask(i);
        }
        logger.info("All tasks finished.");
    }

}
