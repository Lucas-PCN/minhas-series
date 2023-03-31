package com.trybe.acc.java.minhasseries.service;

import com.trybe.acc.java.minhasseries.exception.EpisodioExistenteException;
import com.trybe.acc.java.minhasseries.exception.SerieExistenteException;
import com.trybe.acc.java.minhasseries.exception.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.exception.ServicoIndisponivelException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.repository.SerieRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SeriesService class.
 */
@Service
public class SeriesService {
  @Autowired
  private SerieRepository serieRepository;

  /**
   * createSerie method.
   */
  public Serie createSerie(Serie serie) {
    if (serieRepository.existsByNome(serie.getNome())) {
      throw new SerieExistenteException();
    }
    
    return serieRepository.save(serie);
  }

  /**
   * getAllSeries method.
   */
  public List<Serie> getAllSeries() {
    return serieRepository.findAll();
  }

  /**
   * getSerieById method.
   */
  public Serie getSerieById(Integer serieId) {
    if (serieRepository.findById(serieId).isEmpty()) {
      throw new SerieNaoEncontradaException();
    }
    
    return serieRepository.findById(serieId).get();
  }

  /**
   * deleteSerieById method.
   */
  public String deleteSerieById(Integer id) {
    if (serieRepository.findById(id).isEmpty()) {
      throw new SerieNaoEncontradaException();
    }

    serieRepository.deleteById(id);
    return "Série deletada com sucesso!";
  }

  /**
   * addEpisode method.
   */
  public Serie addEpisode(Integer serieId, Episodio episodio) {

    if (!serieRepository.existsById(serieId)) {
      throw new SerieNaoEncontradaException();
    }

    Serie serie = serieRepository.findById(serieId).get();

    if (serie.getEpisodios().stream()
        .anyMatch(ep -> ep.getNumero() == episodio.getNumero())) {
      throw new EpisodioExistenteException();

    }
    
    episodio.setSerie(serie);
    serie.adicionarEpisodio(episodio);
    return serieRepository.save(serie);
  }

  /**
   * getEpisodesById method.
   */
  public List<Episodio> getEpisodesById(Integer serieId) {
    Serie serie = serieRepository.findById(serieId).get();

    if (serie.getEpisodios().isEmpty() || serie.getEpisodios() == null) {
      throw new ServicoIndisponivelException();
    }

    return serie.getEpisodios();

  }

  /**
   * getTempoTotal method.
   */
  public Map<String, Integer> getTempoTotal() {    
    return Map.of(
          "tempoEmMinutos", 
          serieRepository.findAll().stream().mapToInt(serie -> serie.getTempoTotal()).sum()
          );
  }

}
