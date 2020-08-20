package io.prathyusha.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.prathyusha.moviecatalogservice.models.CatalogItem;
import io.prathyusha.moviecatalogservice.models.Movie;
import io.prathyusha.moviecatalogservice.models.Rating;
import io.prathyusha.moviecatalogservice.models.UserRating;
import io.prathyusha.moviecatalogservice.services.MovieInfo;
import io.prathyusha.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		UserRating userRating = userRatingInfo.getUserRating(userId);

		return userRating.getRatings().stream().map(rating -> movieInfo.getCatalogItem(rating))

				.collect(Collectors.toList());

	}

	 
}
	
        
		/*
		 * Alternative web client way
		 * Movie movie = webClientBuilder.build()
			    .get()
			    .uri("http://localhost:8087/movies/foo",rating.getMovieId() )
			    .retrieve()
			    .bodyToMono(Movie.class)
			    .block();
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
         
		})
			   .collect(Collectors.toList());
		 */
		
	

