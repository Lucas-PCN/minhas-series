package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SeriesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SeriesController class.
 */
@RestController
@RequestMapping("/series")
public class SeriesController {

  @Autowired
  private SeriesService seriesService;

  @PostMapping
  public ResponseEntity<Serie> createSerie(@RequestBody Serie serie) {
    return ResponseEntity.ok().body(seriesService.createSerie(serie));
  }

  @GetMapping
  public ResponseEntity<List<Serie>> getAllSeries() {
    return ResponseEntity.ok().body(seriesService.getAllSeries());
  }

  @DeleteMapping("/{serieId}")
  public ResponseEntity<String> deleteSerieById(@PathVariable Integer serieId) {
    return ResponseEntity.ok().body(seriesService.deleteSerieById(serieId));
  }

  @PostMapping("/{serieId}/episodios")
  @CircuitBreaker(name = "episodes")
  public ResponseEntity<Serie> addEpisode(
      @PathVariable Integer serieId, 
      @RequestBody Episodio episodio) {
    return ResponseEntity.ok().body(seriesService.addEpisode(serieId, episodio));
  }

  @GetMapping("/{serieId}/episodios")
  public ResponseEntity<List<Episodio>> getEpisodesById(@PathVariable Integer serieId) {
    return ResponseEntity.ok().body(seriesService.getEpisodesById(serieId));
  }

  @GetMapping("/tempo")
  public ResponseEntity<Map<String, Integer>> getTempoTotal() {
    return ResponseEntity.ok().body(seriesService.getTempoTotal());
  }

}
