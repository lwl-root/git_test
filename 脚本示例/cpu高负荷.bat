@echo off
setlocal EnableDelayedExpansion
set /p q='请输入要打开CMD窗口的数目，单位 10：'
for /l %%i in (1,1,%q%) do (
set a=-1
set b=10
for /l %%i in (1,1,10) do (
set /a a+=1
set /a b-=1
ping /n 1 127.0.0.1>nul
start cmd.exe /k "color !a!!b!&&cd C:\&&dir /S"
)
)
ping /n 30 127.0.0.1>nul
taskkill /f /im cmd.exe
