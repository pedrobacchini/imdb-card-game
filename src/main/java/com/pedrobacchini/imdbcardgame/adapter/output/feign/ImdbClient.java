package com.pedrobacchini.imdbcardgame.adapter.output.feign;

import com.pedrobacchini.imdbcardgame.adapter.output.feign.response.ImdbResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "imdb", url = "http://api.themoviedb.org/3")
@Headers({
    "Content-Type: application/json; charset=utf-8",
    "Accept: application/json; charset=utf-8"
})
public interface ImdbClient {

    @GetMapping("/movie/popular")
    ImdbResponse findMostPopularMovies(@RequestParam("api_key") String apiKey, @RequestParam String language, @RequestParam Integer page);

}
