c:
cd C:\Workspace\fwilhelm\Projekte\Diplom\code\java

set LOCALCLASSPATH=.;..\lib\colt.jar;..\lib\jdom.jar;..\lib\junit.jar;..\lib\xalan.jar;..\lib\xerces.jar
set LOCALCLASSPATH=%LOCALCLASSPATH%;..\lib\jai_codec.jar;..\lib\jai_core.jar;..\lib\mlibwrapper_jai.jar

java -da -Xmx256m -classpath %LOCALCLASSPATH% org.wewi.medimg.viewer.Viewer %1