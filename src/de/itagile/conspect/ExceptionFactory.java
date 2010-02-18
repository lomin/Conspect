package de.itagile.conspect;

import java.util.List;

public interface ExceptionFactory
{

  RuntimeException create(List<String> messages);

}
