package io.steem.client;

public class SteemClientException extends RuntimeException {

  public SteemClientException(String message, Throwable e) {
    super(message, e);
  }

  public SteemClientException(String message) {
    super(message);
  }
}
