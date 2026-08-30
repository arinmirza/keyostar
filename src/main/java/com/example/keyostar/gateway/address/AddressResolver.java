package com.example.keyostar.gateway.address;

import java.net.URI;

public interface AddressResolver {
    URI resolve(int storeIndex);
}
