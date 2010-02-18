package de.itagile.conspect;

public interface ExceptionWrapper extends ConspectContract
{
  RuntimeException wrap(Exception exception);
}