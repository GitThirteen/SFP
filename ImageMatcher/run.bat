@ECHO off
ECHO Creating executable .jar file...
ECHO.
JAVAC @FilesList.txt -d out
MKDIR temp
XCOPY MANIFEST.MF temp\*
XCOPY /s out\* temp\*
CD temp
JAR cvfm App.jar MANIFEST.MF exceptions\*.class run\*.class
XCOPY App.jar ..\*
CD ..
RMDIR /s /q temp

ECHO.
ECHO.
ECHO.
ECHO.
ECHO.
ECHO.
ECHO == SUCCESSFULLY FINISHED COMPILING ==
ECHO Execute 'App.jar' to run the program.
pause
