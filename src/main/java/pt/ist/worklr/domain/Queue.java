package pt.ist.worklr.domain;

public abstract class Queue extends Queue_Base {

    public void init(String name) {
        setName(name);
    }

    public static Queue getQueueByName(String name) {
        for (Queue queue : Worklr.getInstance().getQueueSet()) {
            if (queue.getName().toLowerCase().equals(name.toLowerCase())) {
                return queue;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

}
