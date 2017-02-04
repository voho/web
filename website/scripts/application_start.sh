#!/bin/bash

set -e

STAGING="/tmp/staging"
WAR="$STAGING/website.war"
WEBAPPS=/usr/share/tomcat8/webapps

rm -rf $WEBAPPS/ROOT $WEBAPPS/ROOT.war
cp $WAR $WEBAPPS/ROOT.war

service tomcat8 start