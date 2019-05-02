package com.sundsvall.repo;

import com.sundsvall.model.Subscription;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Repo {

    private final Map<Long,Subscription> subscriptions = new HashMap<>();
    private final Map<Long, Subscription> targeSubscriptions = new HashMap<>();
    public List<Subscription> getSubscriptions() {

        IntStream.range(1,1000).forEach(i->{
            Subscription subscription = new Subscription(i, String.valueOf(ThreadLocalRandom.current().nextInt()));
            subscriptions.put(subscription.getId(),subscription);
        });

        return  new ArrayList<>(subscriptions.values());
    }

    public void removeSubscription(long id){
        subscriptions.remove(id);

    }

    public void saveToTarget(Subscription subscription){
        targeSubscriptions.put(subscription.getId(),subscription);
    }

    public Map<Long, Subscription> getTargeSubscriptions() {
        return targeSubscriptions;
    }
}
