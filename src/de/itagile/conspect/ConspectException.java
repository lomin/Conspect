package de.itagile.conspect;

public class ConspectException extends RuntimeException implements ConspectExceptionMarker
{
  public ConspectException(String message)
  {
    super(message);
  }

  public ConspectException(Exception exception)
  {
    super(exception);
  }

  private static final long serialVersionUID = 1L;
}