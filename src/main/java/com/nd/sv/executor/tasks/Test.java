package com.nd.sv.executor.tasks;

import com.nd.sv.executor.Task;
import com.nd.sv.serialization.Message;

import java.util.Date;

/**
 */
public class Test implements Task {

    @Override
    public Message execute() {
        System.out.println("Hello " + new Date());
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
