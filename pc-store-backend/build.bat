@ECHO OFF

SET JAVA_HOME=C:\Program Files\Java\jdk-19
SET PATH=%PATH%;C:\Program Files\Java\jdk-19\bin;

CALL mvn clean package -Plocal
pause