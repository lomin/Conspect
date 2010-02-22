package de.itagile.conspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * Contract and exception handling aspect for component-based Spring projects.<p>
 * 
 * By including this aspect in a component-based Spring project, it is possible to check parameter 
 * and component contracts at runtime. This aspect also includes support for exception wrapping at
 * component boundaries.<p>
 * 
 * The Conspect class delegates all component calls, which have been specified by the pointcut expression
 * for this aspect, to corresponding contracts. The parameters of a call are checked against all 
 * parameter contracts, the call itself is checked against all component contracts.<p>
 * 
 * By default, parameter contracts are handled by the {@link ConspectParameterContractHandler}. It collects 
 * all results of broken parameter contracts and generates an exception, which is created by an 
 * implementation of the {@link ExceptionFactory}.<p>
 *
 * A component contract must implement {@link ComponentContract} <strong>and</strong> the component interface 
 * it is applicable for. The component contract is supposed to throw an exception, if the contract is broken.<p>
 * 
 * As long as the {@link ExitCondition} is not fulfilled and an exception wrapper for a component exists, 
 * all exceptions within the component are wrapped as specified by the wrapper.<p>
 * 
 * <strong>Example usage:</strong><p>
 * <pre>
 * {@code
 * <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.0.xsd">

    <aop:config>
        <aop:pointcut id="componentCall"
            expression="!within(de.itagile.conspect.ConspectContract+) and 
                execution(* de.itagile.conspect.components.*.*(..))" />
        <aop:advisor advice-ref="conspect" pointcut-ref="componentCall" />
    </aop:config>

    <bean id="conspect" class="de.itagile.conspect.Conspect">
        <property name="parameterContracts">
            <list>
                <ref bean="testParameterContract" />
            </list>
        </property>
        <property name="componentContracts">
            <list>
                <ref bean="testComponentContract" />
            </list>
        </property>
        <property name="exceptionWrapper">
            <list>
                <ref bean="testExceptionWrapper" />
            </list>
        </property>
    </bean>

    <bean id="testComponent" class="de.itagile.conspect.components.TestComponentDefault" />
    <bean id="testParameterContract" class="de.itagile.conspect.components.TestParameterContract" />
    <bean id="testComponentContract" class="de.itagile.conspect.components.TestComponentContract" />
    <bean id="testExceptionWrapper" class="de.itagile.conspect.components.TestExceptionWrapper" />
</beans>
 * }
 * </pre>
 */
public class Conspect implements MethodBeforeAdvice, ThrowsAdvice
{
  private ParameterContractHandler parameterContractHandler;
  private ExitCondition exitCondition;
  private List<ParameterContract> parameterContracts = new ArrayList<ParameterContract>();
  private List<ComponentContract> componentContracts = new ArrayList<ComponentContract>();
  private List<ExceptionWrapper> exceptionWrapper = new ArrayList<ExceptionWrapper>();

  /**
   * Instantiates Conspect with defaults.
   * 
   * @see ConspectParameterContractHandler
   * @see ConspectExitCondition
   */
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