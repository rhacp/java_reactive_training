package com.andrei.reactive_test.random;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxTest {

    @Test
    void fluxIntTest_NoError() {
        Flux<Integer> fluxInt = Flux.just(1,2,3).log();

        StepVerifier.create(fluxInt)
//                .expectNext(1)
//                .expectNext(2)
//                .expectNext(3)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void fluxIntTest_Error() {
        Flux<Integer> fluxInt = Flux.just(1,2,3)
                .concatWith(Flux.error(new RuntimeException("Bad boy!")))
                .log();

        StepVerifier.create(fluxInt)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyError(RuntimeException.class);
    }

    @Test
    void fluxTestOne() {
        Flux<Integer> fluxInt = Flux.just(1, 2, 3)
                .concatWith(Flux.just(4));
//                        .concatWith(Flux.error(new RuntimeException("Error bro.")));

        fluxInt.subscribe(
                System.out::println,
                e -> System.err.println("Oh noooo! :( " + e),
                () -> System.out.println("Completed.")
        );
    }
}
