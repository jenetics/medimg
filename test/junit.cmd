@echo off
set TEST_CLASSPATH=devlib\ant-1.5.1.jar;%JAVA_HOME%\lib\tools.jar
set TEST_CLASSPATH=%TEST_CLASSPATH%;..\..\lib\colt-1.0.2.jar;..\..\lib\jdom-1.0b8.jar;..\..\lib\junit-3.8.1.jar;..\..\lib\xalan-2.4.0.jar;..\..\lib\xercesImpl-2.1.0.jar;..\..\lib\jcommon-0.7.1.jar;..\..\lib\jfreechart-0.9.4.jar;..\..\lib\metouia-1.0b.jar
set TEST_CLASSPATH=%TEST_CLASSPATH%;..\..\lib\j3dcore-1.3.jar;..\..\lib\j3dutils-1.3.jar;..\..\lib\jai_codec-1.1.1_01.jar;..\..\lib\jai_core-1.1.1_01.jar;..\..\lib\mlibwrapper_jai-1.1.1_01.jar;vecmath-1.3.jar;../java;.
@echo on

%JAVA_HOME%\bin\java -enableassertions -classpath %TEST_CLASSPATH% junit.swingui.TestRunner