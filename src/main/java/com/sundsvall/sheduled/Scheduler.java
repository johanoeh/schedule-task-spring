
package com.sundsvall.sheduled;

import com.sundsvall.multitask.AgentHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author johan
 */
@Component
public class Scheduler {
    
    
    private static final String DATE_FORMAT = "yyyy-MM-DD HH:mm:ss.SSS";
    
    private static final String START_CRON = "0 38 19 * * ?";
    private static final String STOP_CRON = "0 39 19 * * ?";
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    
    @Value("${agent.handler.numOfAgents:2}")
    private int numOfAgents;
    
    private AgentHandler agentHandler ;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
        
    
      
    @Scheduled(cron = START_CRON)
    public void start(){
        agentHandler = new AgentHandler(numOfAgents);
        LOGGER.info("Starts sceduled task {} {}", dateTimeFormatter.format(LocalDateTime.now()), numOfAgents);
        try {
            agentHandler.start();
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    
    @Scheduled(cron = STOP_CRON)
    public void stop(){
        LOGGER.info("Stops scheduled task {}", dateTimeFormatter.format(LocalDateTime.now()));
        
        agentHandler.stop();
    }
   
}
