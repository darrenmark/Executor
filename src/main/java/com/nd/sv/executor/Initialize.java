package com.nd.sv.executor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 */
public class Initialize {

    public static void main(String[] args) throws Exception{
        new Initialize().run();
    }

    public void run() throws Exception {
        String[] configFiles = new String[1];
        configFiles[0] = "spring-config.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
        TaskScheduler taskScheduler = applicationContext.getBean(TaskScheduler.class);
        for(Map.Entry<String, String> entry: taskAndSchedules().entrySet()) {
            taskScheduler.schedule(new Worker((Task) Class.forName(entry.getKey()).newInstance()), new CronTrigger(entry.getValue()));
        }
    }

    Map<String, String> taskAndSchedules() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/tasks.properties"));
        Map<String, String> result = new HashMap<>();
        for(Map.Entry<Object, Object> entry: properties.entrySet()) {
            result.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return result;
    }

}

