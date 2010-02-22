package de.itagile.conspect;

/**
 * Implementations of this interface are responsible for wrapping all exceptions for which they are
 * applicable.
 */
public interface ExceptionWrapper extends ConspectContract
{
  RuntimeException wrap(Exception exception);
}