package de.itagile.conspect;

public interface ParameterContract extends ConspectContract
{
  ContractResult check(Object argument);
}