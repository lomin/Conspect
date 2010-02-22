package de.itagile.conspect;

import java.util.List;

/**
 * Implementations of this interface should create <tt>RuntimeExceptions</tt> from a list of messages,
 * collected by the {@link ParameterContractHandler}. The messages presumably enumerate broken parameter
 * contracts. 
 */
public interface ExceptionFactory
{
  RuntimeException create(List<String> messages);
}
