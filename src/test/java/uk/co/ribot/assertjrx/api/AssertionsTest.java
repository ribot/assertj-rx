package uk.co.ribot.assertjrx.api;

import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionsTest {

    @Test public void testAssertThatBlockingObservableReturnsNotNull() {
        assertThat(Assertions.assertThat(Observable.just(1).toBlocking()))
                .isNotNull();
    }

}
