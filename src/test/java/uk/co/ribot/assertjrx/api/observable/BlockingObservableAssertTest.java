package uk.co.ribot.assertjrx.api.observable;

import org.assertj.core.api.AbstractListAssert;
import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingObservableAssertTest {

    @Test
    public void failsAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.error(new IllegalAccessError()).toBlocking())
                .fails();
    }

    @Test(expected = AssertionError.class)
    public void failsAssertionShouldFail() {
        new BlockingObservableAssert<>(Observable.just(1, 2).toBlocking())
                .fails();
    }

    @Test
    public void failsWithErrorAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.error(new IllegalAccessError()).toBlocking())
                .failsWithError(IllegalAccessError.class);
    }

    @Test(expected = AssertionError.class)
    public void failsWithErrorAssertionShouldFail() {
        new BlockingObservableAssert<>(Observable.error(new IllegalArgumentException()).toBlocking())
                .failsWithError(IllegalAccessError.class);
    }

    @Test
    public void completesAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .completes();
    }

    @Test(expected = AssertionError.class)
    public void completesAssertionShouldFail() {
        new BlockingObservableAssert<>(Observable.error(new IllegalArgumentException()).toBlocking())
                .completes();
    }

    @Test
    public void valuesCountIsAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .valuesCountIs(2);
    }

    @Test(expected = AssertionError.class)
    public void valuesCountIsAssertionShouldFail() {
        new BlockingObservableAssert<>(Observable.just("a").toBlocking())
                .valuesCountIs(2);
    }

    @Test
    public void emitsNoValuesAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.empty().toBlocking())
                .emitsNoValues();
    }

    @Test(expected = AssertionError.class)
    public void emitsNoValuesAssertionShouldFail() {
        new BlockingObservableAssert<>(Observable.just("a").toBlocking())
                .emitsNoValues();
    }

    @Test
    public void emitsSingleValueAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.just("a").toBlocking())
                .emitsSingleValue("a");
    }

    @Test(expected = AssertionError.class)
    public void emitsSingleValueAssertionShouldFailBecauseOfSize() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .emitsSingleValue("a");
    }

    @Test(expected = AssertionError.class)
    public void emitsSingleValueAssertionShouldFailBecauseOfValue() {
        new BlockingObservableAssert<>(Observable.just("a").toBlocking())
                .emitsSingleValue("b");
    }

    @Test
    public void listOfValuesEmittedReturnsValidAssertion() {
        AbstractListAssert<?, ?, ?> listAssert = new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .listOfValuesEmitted();
        assertThat(listAssert).isNotNull();
    }

    @Test
    public void listOfValuesEmittedContainsExactly() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .listOfValuesEmitted().containsExactly("a", "b");
    }
}
