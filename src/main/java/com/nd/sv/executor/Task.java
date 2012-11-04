package com.nd.sv.executor;

import com.nd.sv.serialization.Message;

/**
 * Any task that can be executed by the executor framework
 */
public interface Task {

    Message execute();
}
