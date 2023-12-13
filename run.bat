@echo off

echo Compiling the project...
call mvn clean compile

echo Running automated tests...
call mvn test

echo Opening report...
start "" "target\reports\demoblaze-chrome.html"
start "" "target\reports\demoblaze-edge.html"
start "" "target\reports\demoblaze-firefox.html"
