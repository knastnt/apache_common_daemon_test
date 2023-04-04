Запуск jar как сервиса Windows при помощи Apache Commons Daemon.

https://commons.apache.org/proper/commons-daemon/procrun.html
https://cwiki.apache.org/confluence/display/commons/Daemon

Файл prunsrv.exe берётся отсюда https://dlcdn.apache.org/commons/daemon/binaries/windows/

Сделать mvn package
Скачать с сайта Oracle JDK: jdk-11.0.18_windows-x64_bin.zip
Разархивировать в target/jdk-11.0.18
Запустить install.bat для установки сервиса TEST
Включать/выключать/смотреть логи в target...
Запустить uninstall.bat для удаления сервиса TEST