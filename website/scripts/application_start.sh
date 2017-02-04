#!/bin/bash

set -e

WAR=./website.war
WEBAPPS=/usr/share/tomcat8/webapps

rm -rf $WEBAPPS/ROOT
rm -rf $WEBAPPS/ROOT.war
pwd
ls -l
cp $WAR $WEBAPPS/ROOT.war

service tomcat8 start