import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.input.PortableDataStream;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import scala.Tuple2;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asanand on 2/14/17.
 */
public class FileDetector {
    public static void main(String[] args) throws Exception {
        JavaSparkContext sc = new JavaSparkContext();
        JavaPairRDD<String, PortableDataStream> files = sc.binaryFiles("/Users/asanand/Downloads/sparkTikaTest/sparkTest3");

        List<Map<String, String>> results = files.map(new Function<Tuple2<String, PortableDataStream>, Map<String, String>>() {

            public Map<String, String> call(Tuple2<String, PortableDataStream> stringPortableDataStreamTuple2) throws Exception {
                DataInputStream dis = stringPortableDataStreamTuple2._2.open();
                Tika tika = new Tika();
                Map<String, String> metadata = new HashMap();
                Metadata tikaMetadata = new Metadata();
                tika.parse(dis, tikaMetadata);
                String[] names = tikaMetadata.names();
                metadata.put("File Name", stringPortableDataStreamTuple2._1);
                for (String name : names) {
                    metadata.put(name, tikaMetadata.get(name));
                }
                return metadata;
            }
        }).collect();
        System.out.println("\n\n");

        for (Map<String, String> fileMetadata : results
                ) {
            for (String key : fileMetadata.keySet()
                    ) {
                System.out.println(key + " : " + fileMetadata.get(key));
            }
            System.out.println();
        }

    }
}
