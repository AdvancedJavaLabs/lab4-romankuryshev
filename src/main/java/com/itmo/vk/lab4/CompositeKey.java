package com.itmo.vk.lab4;

import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CompositeKey implements WritableComparable<CompositeKey> {
    private double revenue;
    private String category;

    public CompositeKey() {}

    public CompositeKey(double revenue, String category) {
        this.revenue = revenue;
        this.category = category;
    }

    public double getRevenue() {
        return revenue;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeDouble(revenue);
        out.writeUTF(category);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        revenue = in.readDouble();
        category = in.readUTF();
    }

    @Override
    public int compareTo(CompositeKey o) {
        int cmp = Double.compare(o.revenue, this.revenue);
        if (cmp != 0) {
            return cmp;
        }
        return this.category.compareTo(o.category);
    }

    @Override
    public String toString() {
        return category + "\t" + revenue;
    }
}

