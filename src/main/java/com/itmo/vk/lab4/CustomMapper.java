package com.itmo.vk.lab4;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CustomMapper extends Mapper<LongWritable, Text, Text, Sell> {
    private final static String CSV_DELIMITER = ",";
    private final static int PARAMS_COUNT = 5;

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Sell>.Context context) throws IOException, InterruptedException {
        if (key.get() == 0) {
            return;
        }

        String[] params = value.toString().split(CSV_DELIMITER);
        if (params.length != PARAMS_COUNT) {
            return;
        }
        int quantity = Integer.parseInt(params[4]);
        Sell sell = new Sell(Double.parseDouble(params[3]) * quantity, quantity);
        context.write(new Text(params[2]), sell);
    }
}