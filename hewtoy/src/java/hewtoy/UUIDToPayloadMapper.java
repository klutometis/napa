package hewtoy;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import vineyard.hadoop.MapReduceJobCreator;

public class UUIDToPayloadMapper implements MapReduceJobCreator {

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        public void map(LongWritable key,
                        Text MD5ToUUID,
                        Context context)
            throws IOException, InterruptedException {
            String[] MD5AndUUID = MD5ToUUID.toString().split(",");
            String MD5 = MD5AndUUID[0];
            String UUID = MD5AndUUID[1];
            context.write(new Text(UUID),
                          new Text(String.format("time: %s",
                                                 System.currentTimeMillis())));
        }
    }

    public Job createJob(Configuration conf) {
        try {
            Job job = new Job(conf);
            job.setJobName("map-uuid-to-payload");
            job.setJarByClass(UUIDToPayloadMapper.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            job.setMapperClass(Map.class);
            // job.setReducerClass(IdentityReducer.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);

            FileInputFormat.setInputPaths
                (job, new Path(conf.get("input_path")));
            FileOutputFormat.setOutputPath
                (job, new Path(conf.get("output_path")));

            return job;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
