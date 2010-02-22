package de.itagile.conspect;

/**
 * A simple default exception for exception wrapping. Marked by the {@link ConspectExceptionMarker} to work
 * together with the {@link ConspectExitCondition}.
 */
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