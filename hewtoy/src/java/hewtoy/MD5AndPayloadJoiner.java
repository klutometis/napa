package hewtoy;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class MD5AndPayloadJoiner {
    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text UUID,
                           Iterator<Text> values,
                           Context context)
            throws IOException, InterruptedException {
            StringBuilder data = new StringBuilder();
            while (values.hasNext()) {
                data.append(String.format("%s ", values.next()));
            }
            context.write(UUID, new Text(data.toString()));
        }
    }

    public Job createJob(java.util.Map<String, String> properties)
        throws IOException {
        try {
            Job job = new Job();
            job.setJobName("map-uuid-to-payload");
            job.setJarByClass(MD5AndPayloadJoiner.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            // job.setMapperClass(IdentityMapper.class);
            job.setReducerClass(Reduce.class);

            job.setInputFormatClass(SequenceFileInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            // job.setOutputFormat(SequenceFileOutputFormat.class);

            FileInputFormat.addInputPath(job, new Path("uuid-to-md5"));
            FileInputFormat.addInputPath(job, new Path("uuid-to-payload"));
            FileOutputFormat.setOutputPath(job, new Path("md5-and-payload"));

            return job;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
