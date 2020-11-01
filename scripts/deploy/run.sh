#!/bin/sh

red=`tput setaf 1`
green=`tput setaf 2`
yellow=`tput setaf 3`
blue=`tput setaf 4`


# deleting files
echo "${red}DELETING EXISTING FILES ............................"
tput setaf 7
hdfs dfs -rm -r  /user/achref/result/finalData
hdfs dfs -rm  /user/achref/HS-DEMO-1.0.0-spark.jar

# packaging the jar file
echo "${green}PACKAGING ..........................................."
tput setaf 7
cd ~/Bureau/formation/spark-sql-goodpractices-lansrod-demo
mvn clean install

# moving the jar to hdfs
echo "${yellow}MOVING JAR TO HDFS .................................."
tput setaf 7
hdfs dfs -put file:///home/lansrod/Bureau/formation/spark-sql-goodpractices-lansrod-demo/target/HS-DEMO-1.0.0-spark.jar  /user/achref/

# running the app
echo "${blue}RUNNING SPARK JOB....................................."
tput setaf 7
/usr/hdp/current/spark2-client/bin/spark-submit --class com.hs.pipeline.demo.applications.Main  hdfs://achref:8020/user/achref/HS-DEMO-1.0.0-spark.jar 20102020

