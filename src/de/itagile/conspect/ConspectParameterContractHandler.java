package de.itagile.conspect;

import java.util.ArrayList;
import java.util.List;

public class ConspectParameterContractHandler implements ParameterContractHandler
{
  private List<String> messages = new ArrayList<String>();
  private ExceptionFactory exceptionFactory;

  /**
   * Instantiates ConspectParameterContractHandler with default {@link ExceptionFactory}.
   * 
   * @see ConspectExceptionFactory
   */
  public ConspectParameterContractHandler()
  {
    exceptionFactory = new ConspectExceptionFactory();
  }

  public ConspectParameterContractHandler(ExceptionFactory exceptionFactory)
  {
    this.exceptionFactory = exceptionFactory;
  }

  @Override
  public void add(ContractResult result)
  {
    if (result.isNegative())
    {
      messages.addAll(result.getMessages());
    }
  }

  @Override
  public void handle(Object target)
  {
    if (messages.isEmpty())
    {
      return;
    }

    RuntimeException exception = exceptionFactory.create(messages);
    messages = new ArrayList<String>();
    throw exception;
  }

  public void setExceptionFactory(ExceptionFactory exceptionFactory)
  {
    this.exceptionFactory = exceptionFactory;
  }
}