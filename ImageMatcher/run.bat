javac @FilesList.txt -d out
@ECHO ========== ATTENTION! ==========

@ECHO Create a copy of your images before running this program.
@ECHO There have been rare occurrences of image corruption if
@ECHO the folder containing the images is connected to a cloud service.
@ECHO (e.g. Google Drive, etc.)

@ECHO ================================
ping 127.0.0.1 -n 11 > nul
cls
java -cp C:\Users\Michi\Desktop\Thirteen\Coding\SFP\ImageMatcher\out run.Run
pause
