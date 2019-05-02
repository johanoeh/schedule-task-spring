package com.sundsvall.multitask;

import com.sundsvall.repo.Repo;
import com.sundsvall.model.Subscription;
import java.util.logging.Logger;

/**
 *
 * @author johan
 */
class Action {

    private Repo repo;
    private static final Logger LOGGER = Logger.getLogger(Action.class.getName());

    public static Action build(){      
        return new Action();
    }

    private Action() {
    }
    
    public Action withRepo(Repo repo){
        this.repo = repo;
        return this;
    }

    public Action(Repo repo) {
        this.repo = repo;
    }

    public void perform(Subscription subscription) {
        try {
            Thread.sleep(300);
            repo.saveToTarget(subscription);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Finished processing subcription ");
        repo.removeSubscription(subscription.getId());
        LOGGER.info("Removed subcription ");
    }
    
    

}
