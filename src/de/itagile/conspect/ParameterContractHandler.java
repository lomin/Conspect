package de.itagile.conspect;


public interface ParameterContractHandler
{
  /**
   * @param result the result of a parameter contract check
   */
  void add(ContractResult result);

  /**
   * @param target the target of a component call
   */
  void handle(Object target);
}
