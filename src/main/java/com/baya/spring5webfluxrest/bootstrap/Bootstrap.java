package com.baya.spring5webfluxrest.bootstrap;

import com.baya.spring5webfluxrest.domain.Category;
import com.baya.spring5webfluxrest.domain.Vendor;
import com.baya.spring5webfluxrest.repositories.CategoryRepository;
import com.baya.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("#### LOADING DATA ON BOOTSTRAP #####");

        loadCategories();
        loadVendors();

    }

    private void loadCategories() {
        Flux<Category> categoryFlux = Flux.just(
                Category.builder().description("Fruits").build(),
                Category.builder().description("Nuts").build(),
                Category.builder().description("Breads").build(),
                Category.builder().description("Meats").build(),
                Category.builder().description("Eggs").build()
        );

        categoryRepository
                .count()
                .subscribe(aLong -> {
                    if (aLong == 0) {
                        categoryRepository.saveAll(categoryFlux).subscribe();
                    }
                });
    }

    private void loadVendors() {
        Flux<Vendor> vendorFlux = Flux.just(
                Vendor.builder().firstName("Joe").lastName("Buck").build(),
                Vendor.builder().firstName("Micheal").lastName("Weston").build(),
                Vendor.builder().firstName("Jessie").lastName("Waters").build(),
                Vendor.builder().firstName("Bill").lastName("Nershi").build(),
                Vendor.builder().firstName("Jimmy").lastName("Buffett").build()
        );

        vendorRepository
                .count()
                .subscribe(count -> {
                    if (count == 0) {
                        vendorRepository.saveAll(vendorFlux).subscribe();
                    }
                });
    }
}
