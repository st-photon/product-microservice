package com.photon.product.services;

import com.photon.product.domain.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.UUID;
import java.util.stream.Stream;


public class SaveProductArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        final Product product = new Product();
        product.setId(UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1"));
        return Stream.of(Arguments.of(product));
    }
}
