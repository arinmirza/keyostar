package com.example.valonis.gateway.address;

import java.net.URI;

public interface AddressResolver {
    URI resolve(int storeIndex);
}
