package com.zimug.bootlaunch.flux.dao;

import com.zimug.bootlaunch.flux.model.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CityRepository extends ReactiveMongoRepository<City, Long> {
    Flux<City> findByCityName(String cityName);
}