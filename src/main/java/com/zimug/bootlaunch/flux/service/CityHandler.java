package com.zimug.bootlaunch.flux.service;

import com.zimug.bootlaunch.flux.dao.CityRepository;
import com.zimug.bootlaunch.flux.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class CityHandler {

    @Resource
    private MongoTemplate mongoTemplate;

    private final CityRepository cityRepository;

    @Autowired
    public CityHandler(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Mono<City> save(City city) {
        return cityRepository.save(city);
    }

    public Mono<City> findCityById(Long id) {
        return cityRepository.findById(id);
    }

    public Flux<City> findAllCity() {
        return cityRepository.findAll();
    }

    public Mono<City> modifyCity(City city) {
        return cityRepository.save(city);
    }

    public Mono<Long> deleteCity(Long id) {
        // 使用mongoTemplate来做删除,直接使用提供的删除方法不行
        Query query = Query.query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, City.class);

        //return cityRepository.deleteById(id); // 这个方法无法删除数据
        return Mono.create(cityMonoSink -> cityMonoSink.success(id));
    }
}