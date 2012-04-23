#!/bin/sh

scriptdir=$(cd -P $(dirname $0) && pwd -P)

cd $scriptdir/seating-list
mvn install -Dmaven.test.skip=true > $scriptdir/kucho.build.log
cd $scriptdir/kucho-daemon
mvn install -Dmaven.test.skip=true >> $scriptdir/kucho.build.log
exec mvn exec:java -Dexec.mainClass=com.twitter.tokyo.kucho.daemon.Daemon > $scriptdir/kucho.out 2> $scriptdir/kucho.err

