@echo off
chcp 65001 > nul
cd /d "%~dp0"

set "JAR_PATH=target\smart-campus-announcement-system-1.0-SNAPSHOT.jar"

:menu
cls
echo Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi
echo.
echo 1 - Java Console Uygulamasını Çalıştır
echo 2 - Web GUI Demo Aç
echo 3 - Çıkış
echo.
set /p secim=Seçiminiz: 

if "%secim%"=="1" goto console
if "%secim%"=="2" goto web
if "%secim%"=="3" exit /b 0
goto menu

:console
cls
if exist "%JAR_PATH%" (
    echo Java console uygulaması başlatılıyor...
    echo.
    echo Giriş bilgileri:
    echo Kullanıcı adı: admin
    echo Şifre: 1234
    echo.
    java -Dfile.encoding=UTF-8 -jar "%JAR_PATH%"
) else (
    echo Jar dosyası bulunamadı.
    echo Lütfen önce mvn clean package veya .\mvnw.cmd clean package çalıştırın.
)
echo.
pause
goto menu

:web
cls
if exist "docs\index.html" (
    echo Web GUI demo varsayılan tarayıcıda açılıyor...
    set "WEB_URL=file:///%cd:\=/%/docs/index.html"
    start "" "%WEB_URL%"
) else (
    echo docs\index.html bulunamadı.
    echo Lütfen docs klasöründeki web demo dosyalarını kontrol edin.
)
echo.
pause
goto menu
