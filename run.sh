#!/bin/sh

javac -cp lib/jsoup-1.7.3.jar:lib/commons-codec-1.9.jar GUI.java BAuth.java
jar cvfm MIF-LDAP.jar manifest.txt *.class