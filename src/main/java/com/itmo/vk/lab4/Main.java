package com.itmo.vk.lab4;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job analyzeStep = Job.getInstance(conf, "Analyze step");

        analyzeStep.setMapperClass(CustomMapper.class);
        analyzeStep.setReducerClass(AnalyzeReducer.class);

        analyzeStep.setMapOutputKeyClass(Text.class);
        analyzeStep.setMapOutputValueClass(Sell.class);
        analyzeStep.setOutputKeyClass(Text.class);
        analyzeStep.setOutputValueClass(Sell.class);

        FileInputFormat.addInputPath(analyzeStep, new Path(args[0]));
        Path intermediateOutput = new Path("intermediate_output_1");
        FileOutputFormat.setOutputPath(analyzeStep, intermediateOutput);

        if (!analyzeStep.waitForCompletion(true)) {
            System.exit(1);
        }

        Job sortStep = Job.getInstance(conf, "Sorting step");
        sortStep.setMapperClass(SortMapper.class);
        sortStep.setReducerClass(SortReducer.class);

        sortStep.setMapOutputKeyClass(CompositeKey.class);
        sortStep.setMapOutputValueClass(Sell.class);
        sortStep.setOutputKeyClass(Text.class);
        sortStep.setOutputValueClass(Sell.class);

        FileInputFormat.addInputPath(sortStep, intermediateOutput);
        FileOutputFormat.setOutputPath(sortStep, new Path(args[1]));

        System.exit(sortStep.waitForCompletion(true) ? 0 : 1);
    }
}
