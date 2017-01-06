
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AveragePageCount {

    public static class TextMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        private final IntWritable pgs = new IntWritable();
        private final Text bookTitle = new Text();

        @Override
        public void map(Object key, Text bookInfo, Context context
        ) throws IOException, InterruptedException {
            String[] book = bookInfo.toString().split("\t");
            bookTitle.set(book[0]);
            pgs.set(Integer.parseInt(book[2]));
            context.write(bookTitle, pgs);
        }
    }

    public static class AverageReduce
            extends Reducer<Text, IntWritable, Text, FloatWritable> {

        private final FloatWritable finalAvg = new FloatWritable();
        Float average = 0f;
        Float count = 0f;
        int sum = 0;

        @Override
        public void reduce(Text key, Iterable<IntWritable> pageCnts,
                Context context
        ) throws IOException, InterruptedException {

            for (IntWritable cnt : pageCnts) {
                sum += cnt.get();
            }
            count += 1;
            average = sum / count;
            finalAvg.set(average);
            context.write(new Text("Average Page Count = "), finalAvg);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration con = new Configuration();
        Job bookJob = Job.getInstance(con, "Average Page Count");
        bookJob.setJarByClass(AveragePageCount.class);
        bookJob.setMapperClass(TextMapper.class);
        bookJob.setReducerClass(AverageReduce.class);
        bookJob.setOutputKeyClass(Text.class);
        bookJob.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(bookJob, new Path("C:/Hadoop/books.txt"));
        FileOutputFormat.setOutputPath(bookJob, new Path("C:/Hadoop/BookOutput"));
        if (bookJob.waitForCompletion(true)) {
            System.exit(0);
        }
    }
}
