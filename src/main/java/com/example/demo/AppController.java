package com.example.demo;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class AppController {


    @GetMapping("/linearsearch")
    public String linearSearch(@RequestParam String list, @RequestParam String value) {

        List<String> data = Arrays.asList(list.split(","));

        int result = -1;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(value)) {
                result = i;
                break;
            }
        }

        return "{ \"operation\": \"linearSearch\", " +
                "\"inputlist\": \"" + list + "\", " +
                "\"value\": \"" + value + "\", " +
                "\"output\": \"" + result + "\" }";
    }


    @GetMapping("/binarysearch")
    public String binarySearch(@RequestParam String list, @RequestParam String value) {

        List<String> data = Arrays.asList(list.split(","));

        int result = binaryRecursive(data, value, 0, data.size() - 1);

        return "{ \"operation\": \"binarySearch\", " +
                "\"inputlist\": \"" + list + "\", " +
                "\"value\": \"" + value + "\", " +
                "\"output\": \"" + result + "\" }";
    }


    private int binaryRecursive(List<String> data, String value, int left, int right) {

        if (left > right) return -1;

        int mid = (left + right) / 2;

        int cmp = data.get(mid).compareTo(value);

        if (cmp == 0) return mid;
        if (cmp > 0) return binaryRecursive(data, value, left, mid - 1);

        return binaryRecursive(data, value, mid + 1, right);
    }
}
