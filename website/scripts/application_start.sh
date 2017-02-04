#!/bin/bash

set -e

WAR=website.war
WEBAPPS=/usr/share/tomcat8/webapps

rm -rfv $WEBAPPS/ROOT
cp $WAR $WEBAPPS/ROOT.war

service tomcat8 start