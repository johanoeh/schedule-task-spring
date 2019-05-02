package com.sundsvall.multitask;

import com.sundsvall.repo.Repo;
import com.sundsvall.model.Subscription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class AgentHandler {

    private int numOfAgents;
    private ConcurrentLinkedQueue<Subscription> subscriptionQueue;
    private final Repo repo;
    private final List<Thread> agentThreads;
    private final List<Agent> agents;
    private static final Logger LOGGER = Logger.getLogger(AgentHandler.class.getName());

    public AgentHandler(int numOfAgents) {
        
        agents = new ArrayList<>();
        agentThreads = new ArrayList<>();
        subscriptionQueue = new ConcurrentLinkedQueue<>();

        this.numOfAgents = numOfAgents;
        repo = new Repo();
        this.numOfAgents = numOfAgents;
        List<Subscription> subscriptions = repo.getSubscriptions();
        subscriptionQueue.addAll(subscriptions);
    }


    public void start() throws InterruptedException {
        

        for (int i = 0; i < numOfAgents; i++) {
            
            Agent agent;
            agent = new Agent(this, "agent " + i, repo);
            Thread agentThread = new Thread(agent);
            agentThread.start();
            agentThreads.add(agentThread);
            agents.add(agent);

        }
        // joins the threads so that all finish before continuing
        for (Thread agentThread : agentThreads) {
            agentThread.join();
        }

        LOGGER.info("All threads finished excecution sending email");

        StringBuilder sb = new StringBuilder();

        Collection<Subscription> values = repo.getTargeSubscriptions().values();

        Iterator<Subscription> iterator = values.iterator();

        for (int i = 0; iterator.hasNext(); ++i) {
            sb.append(iterator.next()).append("\n");
        }

        System.out.println(sb.toString());

        for(Agent agent : agents){
            
            System.out.println("Agent: "+agent.getAgentName()+" processed "+agent.getProcessedEntriesCount());
        }

    }
    
    public  void stop(){
        agents.forEach(agent-> {agent.stop();});
    }


    public ConcurrentLinkedQueue<Subscription> getSubscriptionQueue() {
        return subscriptionQueue;
    }
}
