package com.YankaPt;

import java.util.Set;

/**
 * Created by jan on 12.2.18.
 */
public class Notifier {
    private Set<? extends Notifiable> notifiables;

    public Notifier(Set<? extends Notifiable> hashSet) {
        notifiables = hashSet;
    }

    public void doNotifyAll() {
        notifiables.forEach(i -> i.notify("This student is "));
    }
}
