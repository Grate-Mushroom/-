@echo off
cd /d "%~dp0"
echo Запуск WeatherApp...
mvnw.cmd clean compile javafx:run
pause