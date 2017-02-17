# spark-tika
This source code is for tika on spark. <br />
To detect ORC files also, clone https://github.com/ashwini-anand/tika and install it using maven and include it in spark-tika-app's pom.xml .<br />
Example of how to run spark-tika on hadoop cluster:
$spark-submit --class FileDetectorHdfs --master yarn --deploy-mode cluster --driver-memory 4g --executor-memory 2g --executor-cores 1 --queue default jars/spark-tika.jar hdfs:/user/admin2 hdfs:/user/admin/output <br/>

After .jar , first param is input path and second param is output path.
