package de.itagile.conspect.components;

import de.itagile.conspect.ContractResult;
import de.itagile.conspect.ContractResultNegative;
import de.itagile.conspect.ParameterContract;

public class TestParameterContract implements ParameterContract
{
  public static final String PARAMETER_MUST_NOT_BE_NULL = "Parameter must not be null";

  @Override
  public ContractResult check(Object argument)
  {
    return new ContractResultNegative(TestParameterContract.PARAMETER_MUST_NOT_BE_NULL);
  }

  @Override
  public boolean isApplicable(Object argument)
  {
    return argument == null;
  }
}