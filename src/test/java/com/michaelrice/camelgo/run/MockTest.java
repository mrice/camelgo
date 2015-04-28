package com.michaelrice.camelgo.run;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by mrice on 4/27/15.
 */
public class MockTest extends Camelgo {

    @Override
    public RouteBuilder prepare() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://test").to("file://test");
            }
        };
    }
}
