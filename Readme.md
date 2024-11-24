github: https://github.com/DavidHePing/Taboola-Test

System Design: https://github.com/DavidHePing/Taboola-Test/blob/master/SystemDesign.md

helm repo add bitnami https://charts.bitnami.com/bitnami

cd db

java -cp ./hsqldb-2.5.1.jar org.hsqldb.server.Server --database.0 file:test/db --dbname.0 xdb

cd spark

mvn clean compile exec:java '-Dexec.mainClass=com.taboola.spark.SparkApp'

cd api

chmod +x build.sh 

build.sh 123

chmod +x run.sh

run.sh 123

cd redis

chmod +x run.sh

run.sh