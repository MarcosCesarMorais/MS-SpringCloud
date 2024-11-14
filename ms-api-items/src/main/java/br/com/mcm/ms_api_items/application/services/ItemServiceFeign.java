package br.com.mcm.ms_api_items.application.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mcm.ms_api_items.api.dtos.Item;
import br.com.mcm.ms_api_items.api.dtos.Product;
import br.com.mcm.ms_api_items.application.clients.ProductFeignClient;

@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient client;

    @Override
    public List<Item> findAll() {
        return client.findAll()
                .stream()
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> finById(String id) {
        Product product = client.findById(id);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(new Item(product, new Random().nextInt(10) + 1));
    }
}
