package hewtoy;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import vineyard.hadoop.Vineyardized;

public class UUIDToMD5Mapper implements Vineyardized {

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        public void map(LongWritable key,
                        Text MD5ToUUID,
                        Context context)
            throws IOException, InterruptedException {
            String[] MD5AndUUID = MD5ToUUID.toString().split(",");
            String MD5 = MD5AndUUID[0];
            String UUID = MD5AndUUID[1];
            context.write(new Text(UUID),
                          new Text(String.format("md5: %s", MD5)));
        }
    }

    public Job createJob(java.util.Map<String, String> properties) {
        try {
            Job job = new Job();
            job.setJobName("map-uuid-to-md5");
            job.setJarByClass(UUIDToMD5Mapper.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            job.setMapperClass(Map.class);
            // job.setReducerClass(IdentityReducer.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);

            FileInputFormat.setInputPaths(job, new Path("md5-to-uuid"));
            FileOutputFormat.setOutputPath(job, new Path("uuid-to-md5"));

            return job;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
