package de.itagile.conspect.components;

import de.itagile.conspect.ConspectException;
import de.itagile.conspect.ExceptionWrapper;

public class TestExceptionWrapper implements ExceptionWrapper
{
  @Override
  public RuntimeException wrap(Exception exception)
  {
    return new ConspectException(exception);
  }

  @Override
  public boolean isApplicable(Object object)
  {
    return true;
  }
}