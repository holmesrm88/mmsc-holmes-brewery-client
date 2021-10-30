package com.example.msscholmesbreweryclient.web.client;

import com.example.msscholmesbreweryclient.web.model.BeerDto;
import com.example.msscholmesbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void getBeerById() {
        BeerDto beerDto = breweryClient.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void testSaveNewBeer() {
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
        URI uri = breweryClient.saveNewBeer(beerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void testUpdateBeer(){
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();

        breweryClient.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void testDeleteBeer(){
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDto dto = breweryClient.getCustomerById(UUID.randomUUID());

        System.out.println("getDTO " + dto);

        assertNotNull(dto);

    }

    @Test
    void testSaveNewCustomer(){
        CustomerDto customerDto = CustomerDto.builder().name("John Doe").build();
        URI uri = breweryClient.saveNewCustomer(customerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void testUpdateCustomer(){
        CustomerDto customerDto = CustomerDto.builder().name("John Doe").build();
        breweryClient.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void testDeleteCustomer(){
        breweryClient.deleteCustomer(UUID.randomUUID());
    }
}