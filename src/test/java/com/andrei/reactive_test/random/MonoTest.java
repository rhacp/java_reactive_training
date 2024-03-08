package com.andrei.reactive_test.random;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoTest {

    @Test
    void monoTestOne() {
        Mono<String> monoString = Mono.just("data");
//                .concatWith(Mono.error(new RuntimeException("Bad!")));

        StepVerifier.create(monoString)
                .expectNext("data")
                .verifyComplete();
    }

    @Test
    void monoStringTest_Error() {
        Mono<Error> monoString = Mono.error(new RuntimeException("bad"));
//                .concatWith(Mono.error(new RuntimeException("Bad!")));

        StepVerifier.create(monoString.log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
