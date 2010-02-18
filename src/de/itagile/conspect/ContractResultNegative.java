package de.itagile.conspect;

public class ContractResultNegative extends AbstractContractResult implements ContractResult
{
  public ContractResultNegative()
  {
  }
  
  public ContractResultNegative(String message)
  {
    addMessage(message);
  }

  public boolean isNegative()
  {
    return true;
  }
}