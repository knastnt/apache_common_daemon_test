Запуск jar как сервиса Windows при помощи Apache Commons Daemon.

Ссылки:

https://commons.apache.org/proper/commons-daemon/procrun.html

https://cwiki.apache.org/confluence/display/commons/Daemon


# Установка как сервис Windows

Файл prunsrv.exe берётся отсюда https://dlcdn.apache.org/commons/daemon/binaries/windows/

Сделать mvn package
Скачать с сайта Oracle JDK: jdk-11.0.18_windows-x64_bin.zip
Разархивировать в target/jdk-11.0.18
Запустить install.bat для установки сервиса TEST
Включать/выключать/смотреть логи в target...
Запустить uninstall.bat для удаления сервиса TEST


Может быть проблемы при использовании х32 и х64 файла prunsrv.exe. Говорят, мол лучше юзать х32, чтобы он мог запуститься и там и там, но походу и сам JDK должен быть х32, но т.к. он х64, то нихрена не работает. Поэтому юзаю х64


# Установка как демон Linux

Использую Ubuntu 22.04 LTS:

скопировать в /home/user файлы:
apache_common_daemon_test-1.0-SNAPSHOT.jar
commons-daemon-1.3.3.jar
daemonctrl.service
daemonctrl.sh

скачать в /home/user jdk-11.0.18_linux-x64_bin.deb
распаковать sudo dpkg -i jdk-11.0.18_linux-x64_bin.deb
проверить работоспособность usr/lib/jvm/jdk-11/bin/java -version

проверить работоспособность /usr/lib/jvm/jdk-11/bin/java -jar apache_common_daemon_test-1.0-SNAPSHOT.jar


установить jsvc: sudo apt-get install jsvc -y

сделать фикс для решения ошибки "Cannot find any VM in Java Home":
sudo mkdir /usr/lib/jvm/jdk-11/lib/amd64
sudo ln -s /usr/lib/jvm/jdk-11/lib/server /usr/lib/jvm/jdk-11/lib/amd64/

дать daemonctrl.sh права на выполнение: chmod +x daemonctrl.sh

запуск:
./daemonctrl.sh start
остановка:
./daemonctrl.sh start
перезапуск:
./daemonctrl.sh restart


установка daemonctrl.service (создание ссылки):
sudo ln -s /home/user/daemonctrl.service /etc/systemd/system/daemonctrl.service

установка и запуск сервиса:
systemctl enable daemonctrl.service
systemctl start daemonctrl.service

проверка:
systemctl status daemonctrl.service