## MSI Compilation

```shell
mkdir temp
del "VTerminal Palette Editor.msi" 2>nul
copy "VTerminal-jar-with-dependencies.jar" "temp\VTerminal-jar-with-dependencies.jar"

"C:\Program Files\Java\jdk-15\bin\jpackage.exe" ^
--type msi ^
--copyright "Valkryst, 2021" ^
--name "VTerminal Palette Editor" ^
--vendor "Valkryst" ^
--verbose ^
--input "%~dp0temp" ^
--main-class "com.valkryst.VTerminalPaletteEditor.Driver" ^
--main-jar "VTerminal-jar-with-dependencies.jar" ^
--win-dir-chooser ^
--win-shortcut

rmdir /S /Q "%~dp0temp
pause
```