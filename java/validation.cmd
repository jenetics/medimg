echo off
set LOCALCLASSPATH=.;..\lib\colt.jar;..\lib\jdom.jar;..\lib\junit.jar;..\lib\xalan.jar;..\lib\xerces.jar
set LOCALCLASSPATH=%LOCALCLASSPATH%;..\lib\jai_codec.jar;..\lib\jai_core.jar;..\lib\mlibwrapper_jai.jar
echo on
java -classpath %LOCALCLASSPATH% -Xmx256m  org.wewi.medimg.seg.validation.Batch "C:\Workspace\fwilhelm\Projekte\Diplom\validation\todo.xml" "C:\Workspace\fwilhelm\Projekte\Diplom\validation\protocols"