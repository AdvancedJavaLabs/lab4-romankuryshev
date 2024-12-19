package com.itmo.vk.lab4;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SortReducer extends Reducer<CompositeKey, Sell, Text, Sell> {

    @Override
    public void reduce(CompositeKey key, Iterable<Sell> values, Context context)
            throws IOException, InterruptedException {
        for (Sell value : values) {
            context.write(new Text(key.getCategory()), value);
        }
    }
}
