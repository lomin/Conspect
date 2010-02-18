package de.itagile.conspect;

import java.util.List;

public interface ContractResult
{
  boolean isNegative();

  void addMessage(String string);
  
  List<String> getMessages();
}
