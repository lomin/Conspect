package de.itagile.conspect;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ConspectForParameterTest
{
  private final ParameterContractHandler parameterContractHandler = mock(ParameterContractHandler.class);
  private final Conspect conspect = new Conspect(parameterContractHandler, null);
  private final List<ParameterContract> parameterContracts = new ArrayList<ParameterContract>();
  private final List<ComponentContract> componentContracts = new ArrayList<ComponentContract>();
  private final ParameterContract contract = mock(ParameterContract.class);
  private final ContractResultNegative resultFalse = new ContractResultNegative();
  private final Method method = null;
  private final Object argument1 = "arg1";
  private final Object argument2 = "arg2";
  private final Object target = "target";
  private final ContractResultPositive resultTrue = new ContractResultPositive();

  @Before
  public void setUp()
  {
    conspect.setComponentContracts(componentContracts);
    conspect.setParameterContracts(parameterContracts);
    parameterContracts.add(contract);
    when(contract.isApplicable(argument1)).thenReturn(true);
    when(contract.isApplicable(argument2)).thenReturn(true);
    when(contract.check(argument1)).thenReturn(resultFalse);
    when(contract.check(argument2)).thenReturn(resultFalse);
  }

  @Test
  public void findsOneBrokenContractForOneParameter() throws Throwable
  {
    Object[] arguments = new Object[]
    { argument1 };

    conspect.before(method, arguments, target);

    verify(parameterContractHandler, times(1)).add(resultFalse);
    verify(parameterContractHandler, times(1)).handle(target);
  }

  @Test
  public void findsTwoBrokenContractsForTwoParameter() throws Throwable
  {
    Object[] arguments = new Object[]
    { argument1, argument2 };

    conspect.before(method, arguments, target);

    verify(parameterContractHandler, times(2)).add(resultFalse);
    verify(parameterContractHandler, times(1)).handle(target);
  }

  @Test
  public void findsBrokenContractForParameterWhenContractIsApplicable() throws Throwable
  {
    Object[] arguments = new Object[]
    { argument1, argument2 };
    when(contract.isApplicable(argument1)).thenReturn(true);
    when(contract.isApplicable(argument2)).thenReturn(false);

    conspect.before(method, arguments, target);

    verify(parameterContractHandler, times(1)).add(resultFalse);
    verify(parameterContractHandler, times(1)).handle(target);
  }

  @Test
  public void collectsResultsOfAllContractsForParameter() throws Throwable
  {
    ParameterContract secondContract = mock(ParameterContract.class);
    when(secondContract.isApplicable(argument1)).thenReturn(true);
    when(secondContract.check(argument1)).thenReturn(resultTrue);
    parameterContracts.add(secondContract);

    Object[] arguments = new Object[]
    { argument1 };

    conspect.before(method, arguments, target);

    verify(parameterContractHandler, times(1)).add(resultFalse);
    verify(parameterContractHandler, times(1)).add(resultTrue);
    verify(parameterContractHandler, times(1)).handle(target);
  }
}