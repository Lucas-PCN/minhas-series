package com.trybe.acc.java.minhasseries.exception;

/**
 * ServicoIndisponivelException class.
 */
@SuppressWarnings("serial")
public class ServicoIndisponivelException extends RuntimeException {
  private static String message = "Serviço temporariamente indisponível";

  public ServicoIndisponivelException() {
    super(message);
  }
}
