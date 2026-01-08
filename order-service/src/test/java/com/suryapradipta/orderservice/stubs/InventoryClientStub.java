package com.suryapradipta.orderservice.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

    public static void stubInventoryCall(WireMockServer wireMockServer, String skuCode, Integer quantity) {
        wireMockServer.stubFor(get(urlEqualTo("/api/inventories/check?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }
}
