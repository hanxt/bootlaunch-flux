package com.zimug.bootlaunch.flux;

import com.zimug.bootlaunch.flux.model.City;
import com.zimug.bootlaunch.flux.service.CityHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebFluxTest
public class CityWebFluxControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CityHandler cityHandler;

    private static Map<String, City> cityMap = new HashMap<>();

    @BeforeClass
    public static void setup() throws Exception {
        City bj= new City();
        bj.setId(1L);
        bj.setProvinceId(2L);
        bj.setCityName("北京");
        bj.setDescription("welcome to beijing");
        cityMap.put("BJ", bj);
    }

    @Test
    public void testSave() throws Exception {
        //打桩
        Mockito.when(this.cityHandler.save(cityMap.get("BJ")))
                .thenReturn(Mono.create(cityMonoSink -> cityMonoSink.success(cityMap.get("BJ"))));


        City expectCity = webClient.post().uri("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(cityMap.get("BJ")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(City.class).returnResult().getResponseBody();

        Assert.assertNotNull(expectCity);
        Assert.assertEquals(expectCity.getId(), cityMap.get("BJ").getId());
        Assert.assertEquals(expectCity.getCityName(), cityMap.get("BJ").getCityName());
    }

}