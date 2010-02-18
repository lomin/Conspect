package de.itagile.conspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class Conspect implements MethodBeforeAdvice, ThrowsAdvice
{
  private ParameterContractHandler parameterContractHandler;
  private ExitCondition exitCondition;
  private List<ParameterContract> parameterContracts = new ArrayList<ParameterContract>();
  private List<ComponentContract> componentContracts = new ArrayList<ComponentContract>();
  private List<ExceptionWrapper> exceptionWrapper = new ArrayList<ExceptionWrapper>();

  public Conspect()
  {
    this.parameterContractHandler = new ConspectParameterContractHandler();
    this.exitCondition = new ConspectExitCondition();
  }

  public Conspect(ParameterContractHandler parameterContractHandler, ExitCondition exitCondition)
  {
    this.parameterContractHandler = parameterContractHandler;
    this.exitCondition = exitCondition;
  }

  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable
  {
    checkParameterContracts(args, target);
    checkComponentContracts(method, args, target);
  }

  private void checkParameterContracts(Object[] args, Object target)
  {
    for (Object argument : args)
    {
      for (ParameterContract contract : parameterContracts)
      {
        if (contract.isApplicable(argument))
        {
          parameterContractHandler.add(contract.check(argument));
        }
      }
    }
    parameterContractHandler.handle(target);
  }

  private void checkComponentContracts(Method method, Object[] args, Object target) throws Throwable
  {
    for (ComponentContract contract : componentContracts)
    {
      if (contract.isApplicable(target))
      {
        try
        {
          contract = contract.curry(target);
          method.invoke(contract, args);
        }
        catch (java.lang.reflect.InvocationTargetException e)
        {
          throw e.getCause();
        }
      }
    }
  }

  public void afterThrowing(Method method, Object[] args, Object target, Exception exception)
  {
    if (exitCondition.isTrue(method, args, target, exception))
    {
      return;
    }

    for (ExceptionWrapper wrapper : exceptionWrapper)
    {
      if (wrapper.isApplicable(target))
      {
        throw wrapper.wrap(exception);
      }
    }
  }

  public ParameterContractHandler getParameterContractHandler()
  {
    return parameterContractHandler;
  }

  public void setParameterContractHandler(ParameterContractHandler parameterContractHandler)
  {
    this.parameterContractHandler = parameterContractHandler;
  }

  public List<ParameterContract> getParameterContracts()
  {
    return parameterContracts;
  }

  public void setParameterContracts(List<ParameterContract> parameterContracts)
  {
    this.parameterContracts = parameterContracts;
  }

  public List<ComponentContract> getComponentContracts()
  {
    return componentContracts;
  }

  public void setComponentContracts(List<ComponentContract> componentContracts)
  {
    this.componentContracts = componentContracts;
  }

  public ExitCondition getExitCondition()
  {
    return exitCondition;
  }

  public void setExitCondition(ExitCondition exitCondition)
  {
    this.exitCondition = exitCondition;
  }

  public List<ExceptionWrapper> getExceptionWrapper()
  {
    return exceptionWrapper;
  }

  public void setExceptionWrapper(List<ExceptionWrapper> exceptionWrapper)
  {
    this.exceptionWrapper = exceptionWrapper;
  }
}