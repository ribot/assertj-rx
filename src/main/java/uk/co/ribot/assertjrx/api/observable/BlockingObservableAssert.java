package uk.co.ribot.assertjrx.api.observable;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractListAssert;
import rx.observables.BlockingObservable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assertions for {@link BlockingObservable}.
 */
public class BlockingObservableAssert<T> extends AbstractAssert<BlockingObservableAssert<T>, BlockingObservable<T>> {

    private BlockingObservableExecutor<T> blockingObservableExecutor;

    public BlockingObservableAssert(BlockingObservable<T> actual) {
        super(actual, BlockingObservableAssert.class);
    }

    /**
     * Verifies that the Observable emits an error
     *
     * @return this assertion object
     */
    public BlockingObservableAssert<T> fails() {
        isNotNull();
        assertThat(getBlockingObservableExecutor().hasFailed())
                .overridingErrorMessage("Observable has not failed")
                .isTrue();
        return this;
    }

    /**
     * Verifies that the Observable emits an error that matches an specific type.
     *
     * @param errorType expected error class that is an extension of Throwable
     * @return this assertion object
     */
    public BlockingObservableAssert<T> failsWithError(Class<? extends Throwable> errorType) {
        isNotNull();
        fails();
        assertThat(getBlockingObservableExecutor().getError())
                .isInstanceOf(errorType);
        return this;
    }

    /**
     * Verifies that the observable completes successfully without emitting errors
     *
     * @return this assertion object
     */
    public BlockingObservableAssert<T> completes() {
        isNotNull();
        assertThat(getBlockingObservableExecutor().hasCompletedSuccessfully())
                .overridingErrorMessage("Observable has not completed successfully")
                .isTrue();
        return this;
    }

    /**
     * Verifies that the number of values received onNext() is equal to expected one
     *
     * @param count expected number of values onNext()
     * @return this assertion object
     */
    public BlockingObservableAssert<T> valuesCountIs(int count) {
        isNotNull();
        assertThat(getBlockingObservableExecutor().getValuesEmitted().size())
                .isEqualTo(count);
        return this;
    }

    /**
     * Verifies that the observable doesn't receive any value onNext()
     *
     * @return this assertion object
     */
    public BlockingObservableAssert<T> emitsNoValues() {
        isNotNull();
        valuesCountIs(0);
        return this;
    }

    /**
     * Verifies that the observable calls onNext() only once with a value that is equal to the given one
     *
     * @param value expected value received onNext()
     * @return this assertion object
     */
    public BlockingObservableAssert<T> emitsSingleValue(T value) {
        isNotNull();
        valuesCountIs(1);
        assertThat(getBlockingObservableExecutor().getValuesEmitted().get(0))
                .isEqualTo(value);
        return this;
    }

    /**
     * Allows performing Assertj assertions over the list of values emitted onNext()
     *
     * @return an instance of @{link ListAssert} initialized with the values received onNext()
     */
    public AbstractListAssert<?, ? extends List<? extends T>, T> listOfValuesEmitted() {
        isNotNull();
        return assertThat(getBlockingObservableExecutor().getValuesEmitted());
    }

    private BlockingObservableExecutor<T> getBlockingObservableExecutor() {
        if (blockingObservableExecutor == null) {
            blockingObservableExecutor = new BlockingObservableExecutor<>(actual);
        }
        return blockingObservableExecutor;
    }

}
