package com.andrei.reactive_test.random;

import com.andrei.reactive_test.models.Carrier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
class FluxControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getFluxedIntTestOne() {
        Flux<Integer> integerFlux = webTestClient.get()
                .uri("/api/flux")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    void getFluxedCarrierBasicTestOne() {
        Carrier one = new Carrier("Ok", "DataOne");
        Carrier two = new Carrier("Ok", "DataTwo");
        Carrier three = new Carrier("Ok", "DataThree");

        Flux<Carrier> carrierFlux = webTestClient.get()
                .uri("/api/flux/basic")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Carrier.class)
                .getResponseBody();

        StepVerifier.create(carrierFlux)
                .expectSubscription()
                .expectNext(one, two, three)
                .verifyComplete();
    }

    @Test
    void getFluxedCarrierBasicTestTwo() {
        Carrier one = new Carrier("Ok", "DataOne");
        Carrier two = new Carrier("Ok", "DataTwo");
        Carrier three = new Carrier("Ok", "DataThree");
        List<Carrier> expectedRes = Arrays.asList(one, two, three);

        webTestClient.get()
                .uri("/api/flux/basic")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Carrier.class)
                .consumeWith(res -> {
                            assertEquals(expectedRes, res.getResponseBody());
                        }
                );

//        StepVerifier.create(carrierFlux)
//                .expectSubscription()
//                .expectNext(one, two, three)
//                .verifyComplete();
    }
}
