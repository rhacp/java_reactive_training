package com.andrei.reactive_test.controllers;

import com.andrei.reactive_test.models.Carrier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/flux")
public class FluxController {

    //    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getFluxedInt() {
        return Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping("/basic")
    public Flux<Carrier> getFluxedCarrierBasic() {
        return source();
    }

    @GetMapping(value = "/ndjson", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Carrier> getFluxedCarrierNDJSOn() {
        return source();
    }

    @GetMapping(value = "/string", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Carrier> getFluxedCarrierString() {
        return source();
    }

    @GetMapping(value = "/infinite")
    public Flux<Long> getFluxedInfinite() {
        return Flux.interval(Duration.ofSeconds(1)).log();
    }

    private Flux<Carrier> source() {
        return Flux.just(
                        new Carrier("Ok", "DataOne"),
                        new Carrier("Ok", "DataTwo"),
                        new Carrier("Ok", "DataThree")
                )
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
