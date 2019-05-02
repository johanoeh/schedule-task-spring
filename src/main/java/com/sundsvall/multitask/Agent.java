package com.sundsvall.multitask;

import com.sundsvall.repo.Repo;
import com.sundsvall.model.Subscription;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class Agent implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Agent.class.getName());
    private String agentName;
    private AgentHandler agentHandler;
    private Repo repo;
    private int processedEntriesCount;
    private volatile Boolean finished = false;

    public Agent(AgentHandler agentHandler, String agentName, Repo repo) {

        this.repo = repo;
        this.agentHandler = agentHandler;
        this.agentName = agentName;
        processedEntriesCount = 0;
    }

    public void run() {

        LOGGER.info("Agent " + agentName + "starts processing data");
        ConcurrentLinkedQueue<Subscription> subscriptionQueue = agentHandler.getSubscriptionQueue();
        Action action = Action.build().withRepo(repo);

        while (!finished && !subscriptionQueue.isEmpty()) {
            Subscription subscription = subscriptionQueue.poll();
            LOGGER.info("Agent " + agentName + " processing subscription " + subscription.toString());
            action.perform(subscription);
            processedEntriesCount++;
        }
    }

    public int getProcessedEntriesCount() {
        return processedEntriesCount;
    }

    public String getAgentName() {
        return agentName;
    }

    public void stop() {
        finished = true;
    }
}
