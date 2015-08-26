package uk.co.ribot.assertjrx.api.observable;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingObservableExecutorTest {

    @Test
    public void testHasErrorTrue() {
        BlockingObservable<?> blockingObservable = Observable.error(new IllegalAccessError()).toBlocking();
        BlockingObservableExecutor<?> blockingObservableExecutor = new BlockingObservableExecutor<>(blockingObservable);

        assertThat(blockingObservableExecutor.hasFailed())
                .isTrue();
    }

    @Test
    public void testHasErrorFalse() {
        BlockingObservable<String> blockingObservable = Observable.just("a").toBlocking();
        BlockingObservableExecutor<String> blockingObservableExecutor =
                new BlockingObservableExecutor<>(blockingObservable);

        assertThat(blockingObservableExecutor.hasFailed())
                .isFalse();
    }

    @Test
    public void testGetError() {
        Throwable expectedError = new IllegalAccessError();
        BlockingObservable<?> blockingObservable = Observable.error(expectedError).toBlocking();
        BlockingObservableExecutor<?> blockingObservableExecutor = new BlockingObservableExecutor<>(blockingObservable);

        assertThat(blockingObservableExecutor.getError())
                .isEqualTo(expectedError);
    }

    @Test
    public void testGetItemsEmittedWhenSuccessful() {
        BlockingObservable<String> blockingObservable = Observable.just("a", "b", "c").toBlocking();
        BlockingObservableExecutor<String> blockingObservableExecutor =
                new BlockingObservableExecutor<>(blockingObservable);

        assertThat(blockingObservableExecutor.getValuesEmitted())
                .containsExactly("a", "b", "c");
    }

    @Test
    public void testGetItemsEmittedWhenEmpty() {
        BlockingObservable<String> blockingObservable = Observable.<String>empty().toBlocking();
        BlockingObservableExecutor<String> blockingObservableExecutor =
                new BlockingObservableExecutor<>(blockingObservable);

        assertThat(blockingObservableExecutor.getValuesEmitted())
                .isEmpty();
    }

    @Test
    public void testGetItemsEmittedWhenFails() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("a");
                subscriber.onNext("b");
                subscriber.onError(new IllegalAccessError());
            }
        });

        BlockingObservableExecutor<String> blockingObservableExecutor =
                new BlockingObservableExecutor<>(observable.toBlocking());

        assertThat(blockingObservableExecutor.getValuesEmitted())
                .containsExactly("a", "b");
    }
}
