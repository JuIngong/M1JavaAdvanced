package fr.unice.miage.m1.TP1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JuIngong on 05/10/2016.
 */
public class MyFilenameFilter implements FilenameFilter {

    private String filter;

    public MyFilenameFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean accept(File dir, String name) {
        Pattern pattern = Pattern.compile(filter);
        Matcher matcher = pattern.matcher(name);
        return new File(dir, name).isDirectory() || matcher.find();
    }
}
