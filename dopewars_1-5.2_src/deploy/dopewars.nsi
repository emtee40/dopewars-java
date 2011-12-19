; Generated NSIS script file (generated by makensitemplate.phtml 0.20)
; by 61.9.128.141 on Nov 13 01 @ 05:03

; NOTE: this .NSI script is designed for NSIS v1.3+

Name "Dopewars"
OutFile "dopewars.exe"

; Some default compiler settings (uncomment and change at will):
; SetCompress auto ; (can be off or force)
; SetDatablockOptimize on ; (can be off)
; CRCCheck on ; (can be off)
; AutoCloseWindow false ; (can be true for the window go away automatically at end)
; ShowInstDetails hide ; (can be show to have them shown, or nevershow to disable)
; SetDateSave off ; (can be on to have files restored to their orginal date)

InstallDir "$PROGRAMFILES\dopewars"
InstallDirRegKey HKEY_LOCAL_MACHINE "SOFTWARE\codecowboy.com\Dopewars" ""
DirShow show ; (make this hide to not let the user change it)
DirText "Select the directory to install dopewars in:"

Section "" ; (default section)
SetOutPath "$INSTDIR"
;
File "L:\java_development\dopewars\dist\dopewars.jar"
WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\codecowboy.com\Dopewars" "" "$INSTDIR"
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\Dopewars" "DisplayName" "Dopewars (remove only)"
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\Dopewars" "UninstallString" '"$INSTDIR\uninst.exe"'
SectionEnd ; end of default section


; begin uninstall settings/section
UninstallText "This will uninstall dopewars from your system"
UninstallExeName "uninst.exe"

Section Uninstall
; add delete commands to delete whatever files/registry keys/etc you installed here.
Delete "$INSTDIR\uninst.exe"
DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\codecowboy.com\Dopewars"
DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\Dopewars"
RMDir "$INSTDIR"
SectionEnd ; end of uninstall section

; eof
