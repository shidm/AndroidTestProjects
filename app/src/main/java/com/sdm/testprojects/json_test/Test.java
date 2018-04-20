package com.sdm.testprojects.json_test;

import java.util.List;

/**
 * Created by shidongming on 18-4-17.
 */

public class Test {
    private List<String> name;

    public Test() {
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name=" + name +
                '}';
    }
}
