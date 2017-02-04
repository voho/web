#!/bin/bash

set -e

# stop Tomcat

service tomcat8 stop

# remove previous versions

WEBAPPS=/usr/share/tomcat8/webapps
rm -rf $WEBAPPS/ROOT $WEBAPPS/ROOT.war