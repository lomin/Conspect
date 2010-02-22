package de.itagile.conspect;

/**
 * Implementations of this interface are called to check the contract of parameters for which they 
 * are applicable.
 */
public interface ParameterContract extends ConspectContract
{
  ContractResult check(Object argument);
}