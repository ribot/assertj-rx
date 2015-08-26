package uk.co.ribot.assertjrx.api.observable;

import rx.observables.BlockingObservable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class takes a {@link BlockingObservable}. It uses {@link BlockingObservable#getIterator()} to triggers the
 * subscription. Then it saves the emitted items in a List as well as a Throwable if an error happens.
 */
class BlockingObservableExecutor<T> {

    private BlockingObservable<T> blockingObservable;
    private Throwable error;
    private List<T> itemsEmitted;

    BlockingObservableExecutor(BlockingObservable<T> blockingObservable) {
        this.blockingObservable = blockingObservable;
        this.itemsEmitted = new ArrayList<>();
        iterateResults();
    }

    public Throwable getError() {
        return error;
    }

    public List<T> getValuesEmitted() {
        return itemsEmitted;
    }

    public boolean hasFailed() {
        return error != null;
    }

    public boolean hasCompletedSuccessfully() {
        return error == null;
    }

    private void iterateResults() {
        try {
            Iterator<T> iterator = blockingObservable.getIterator();
            while (iterator.hasNext()) {
                itemsEmitted.add(iterator.next());
            }
        } catch (Throwable throwable) {
            error = throwable;
        }
    }

}
