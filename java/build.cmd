@echo off
set LOCALCLASSPATH=%CLASSPATH%.;..\lib\ant.jar;..\lib\colt.jar;..\lib\jdom.jar;..\lib\junit.jar;..\lib\xalan.jar;..\lib\xerces.jar
set LOCALCLASSPATH=%LOCALCLASSPATH%;%JAVA_HOME%\lib\tools.jar

%JAVA_HOME%\bin\java -classpath %LOCALCLASSPATH%  org.apache.tools.ant.Main %1 %2 %3 %4 %5
