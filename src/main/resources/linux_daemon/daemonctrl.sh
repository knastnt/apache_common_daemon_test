#!/bin/sh

# Setup variables
EXEC=/usr/bin/jsvc
DESC="MyScheduler service"
JAVA_HOME=/usr/lib/jvm/jdk-11
CLASS_PATH="/home/user/commons-daemon-1.3.3.jar":"/home/user/apache_common_daemon_test-1.0-SNAPSHOT.jar"
CLASS=org.example.Main
USER=root
PID=/home/user/example.pid
LOG_OUT=/home/user/example.out
LOG_ERR=/home/user/example.err

do_exec()
{
    $EXEC -home "$JAVA_HOME" -cp $CLASS_PATH -user $USER -outfile $LOG_OUT -errfile $LOG_ERR -pidfile $PID $1 $CLASS
}

case "$1" in
    start)
		echo "Starting the $DESC..."
        do_exec
        echo "The $DESC has started."
            ;;
    stop)
		echo "Stopping the $DESC..."
        do_exec "-stop"
        echo "The $DESC has stopped."
            ;;
    restart)
        if [ -f "$PID" ]; then
			echo "Restarting the $DESC..."
            do_exec "-stop"
            do_exec
			echo "The $DESC has restarted."
        else
            echo "Service $DESC not running, will do nothing"
            exit 1
        fi
            ;;
    *)
            echo "usage: daemon {start|stop|restart}" >&2
            exit 3
            ;;
esac