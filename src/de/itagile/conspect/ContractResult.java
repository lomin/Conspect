package de.itagile.conspect;

import java.util.List;

/**
 * The result of a parameter contract check
 */
public interface ContractResult
{
  /**
   * @return <tt>true</tt> if contract is broken
   */
  boolean isNegative();

  void addMessage(String string);
  
  List<String> getMessages();
}
