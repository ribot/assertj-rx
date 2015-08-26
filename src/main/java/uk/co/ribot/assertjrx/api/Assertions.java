package uk.co.ribot.assertjrx.api;

import uk.co.ribot.assertjrx.api.observable.BlockingObservableAssert;
import rx.observables.BlockingObservable;

public class Assertions {

    public static <T> BlockingObservableAssert<T> assertThat(BlockingObservable<T> actual) {
        return new BlockingObservableAssert<>(actual);
    }

    private Assertions() {
        throw new AssertionError("No instances.");
    }

}
