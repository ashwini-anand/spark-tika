# spark-tika
This source code is for tika on spark. <br />
To detect ORC files also, clone https://github.com/ashwini-anand/tika and install it using maven and include it in spark-tika-app's pom.xml .<br />
Example of how to run spark-tika on hadoop cluster:
$spark-submit --class FileDetectorHdfs --master yarn --deploy-mode cluster --driver-memory 4g --executor-memory 2g --executor-cores 1 --queue default jars/spark-tika.jar hdfs:/user/admin2 hdfs:/user/admin/output <br/>

After .jar , first param is input path and second param is output path.

Misc: <br />
1) Setting spark version
$export SPARK_MAJOR_VERSION=version_num <br />
 where version_num can be 1 or 2<br />

2) see first 400 line of output file <br />
$hdfs dfs -cat /user/root/output/* | head -400

3) To see time , prefix the command with time <br />
e.g. $time spark-submit --class FileDetectorHdfs --master yarn --deploy-mode cluster --driver-memory 4g --executor-memory 2g --executor-cores 1 --queue default jars/spark-tika.jar hdfs:/user/admin2 hdfs:/user/admin/output <br/>

4)running spark-tika on 1GB pdf files + some amount of ORC files with --driver-memory 8g and --executor-memory 6g takes around 38 seconds. <br />
 
