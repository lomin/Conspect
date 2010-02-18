package de.itagile.conspect;


public interface ParameterContractHandler
{
  void add(ContractResult result);

  void handle(Object target);
}
