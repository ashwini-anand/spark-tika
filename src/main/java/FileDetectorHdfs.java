import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.input.PortableDataStream;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import scala.Tuple2;

import java.io.DataInputStream;

/**
 * Created by asanand on 2/14/17.
 */
public class FileDetectorHdfs {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext();

        try {
            FileSystem fs = FileSystem.get(sc.hadoopConfiguration());
            int minPartitions = Integer.parseInt(args[2]);
            for (String arg : args[0].split(",")) {
                JavaPairRDD<String, PortableDataStream> files;
                if(minPartitions != -1){
                    files = sc.binaryFiles(arg,minPartitions); //like "hdfs:/user/admin2"
                }else{
                    files = sc.binaryFiles(arg); //like "hdfs:/user/admin2"
                }

                System.out.println("Partition size: " + files.partitions().size());
                JavaRDD<MetadataStore> results = files.map(new Function<Tuple2<String, PortableDataStream>, MetadataStore>() {
                    public MetadataStore call(Tuple2<String, PortableDataStream> stringPortableDataStreamTuple2) throws Exception {
                        DataInputStream dis = stringPortableDataStreamTuple2._2.open();
                        Tika tika = new Tika();
                        MetadataStore metadataStore = new MetadataStore();
                        Metadata tikaMetadata = new Metadata();
                        tika.parse(dis, tikaMetadata);
                        String[] names = tikaMetadata.names();
                        metadataStore.getMetadata().put("File Name", stringPortableDataStreamTuple2._1);
                        for (String name : names) {
                            metadataStore.getMetadata().put(name, tikaMetadata.get(name));
                        }
                        return metadataStore;
                    }
                });
                Path outputPath = new Path(args[1] + "/" + arg);
                if (fs.exists(outputPath)) {
                    fs.delete(outputPath, true);
                }
                results.saveAsTextFile(args[1] + "/" + arg); // is coalesce(1,true).saveAsTextFile needed ?
            }
        } catch (Exception e) {
            System.err.println("exception in FileDetectorHdfs");
            e.printStackTrace();
        }

    }
}
