package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("number1");
        list.add("number2");
        map.put("string","testString");
        map.put("list",list);
        List list1=(List)map.get("list");
        System.out.println(list1);
    }

}
