@echo off
echo ===================================================
echo     Android SDK Downloader (No Android Studio)
echo ===================================================
echo.

if not exist "C:\Android" (
    mkdir "C:\Android"
)

echo [1/4] Downloading Command Line Tools (130 MB)...
echo This might take a few minutes depending on your internet speed. Please wait...
curl -L -o "C:\Android\cmdline-tools.zip" "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"

echo.
echo [2/4] Extracting tools...
powershell -Command "Expand-Archive -Path 'C:\Android\cmdline-tools.zip' -DestinationPath 'C:\Android\temp' -Force"

mkdir "C:\Android\cmdline-tools\latest" >nul 2>&1
xcopy /E /I /Y /Q "C:\Android\temp\cmdline-tools\*" "C:\Android\cmdline-tools\latest\"
rmdir /S /Q "C:\Android\temp"
del "C:\Android\cmdline-tools.zip"

echo.
echo [3/4] Configuring your Bezubaan Project...
echo sdk.dir=C\:\\Android> "C:\Users\dell\Bezubaan\local.properties"

echo.
echo [4/4] Accepting Android Licenses...
powershell -Command "Write-Output 'y`ny`ny`ny`ny`ny`ny`ny' | & 'C:\Android\cmdline-tools\latest\bin\sdkmanager.bat' --licenses"

echo.
echo Downloading platform tools...
call "C:\Android\cmdline-tools\latest\bin\sdkmanager.bat" "platform-tools" "platforms;android-34" "build-tools;34.0.0"

echo.
echo ===================================================
echo ALL DONE! You can now run: .\gradlew installDebug
echo ===================================================
pause
