package fr.unice.miage.m1.TP1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JuIngong on 21/09/2016.
 */
public class MyLs {


    public MyLs() {
    }

    public String[] listFile(File file) {
        return file.list();
    }

    public String listFileAsString(File file) {
        StringBuilder sb = new StringBuilder();
        for (String s : listFile(file)) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public String deepList(File file) {
        StringBuilder sb = new StringBuilder();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                sb.append(f).append("\n");
            } else if (f.isDirectory()) {
                sb.append(deepList(f));
            }
        }
        return sb.toString();
    }

    public String deepListWithFilter(File file, String filter) {
        FilenameFilter fnf = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern pattern = Pattern.compile(filter);
                Matcher matcher = pattern.matcher(name);
                return new File(dir, name).isDirectory() || matcher.find();
            }
        };
        return this.loopForDeepList(file, fnf, filter);
    }

    public String deepListWithFilterExtern(File file, String filter) {
        FilenameFilter fnf = new MyFilenameFilter(filter);
        return this.loopForDeepList(file, fnf, filter);
    }

    public String deepListWithFilterIntern(File file, String filter) {
        FilenameFilter fnf = new MyFilenameFilterIntern(filter);
        return this.loopForDeepList(file, fnf, filter);
    }

    public String loopForDeepList(File file, FilenameFilter fnf, String filter){
        StringBuilder sb = new StringBuilder();
        for (File f : file.listFiles(fnf)) {
            if (f.isFile()) {
                sb.append(f).append("\n");
            } else if (f.isDirectory()) {
                sb.append(deepListWithFilter(f, filter));
            }
        }
        return sb.toString();
    }

    public class MyFilenameFilterIntern implements FilenameFilter {

        private String filter;

        public MyFilenameFilterIntern(String filter) {
            this.filter = filter;
        }

        @Override
        public boolean accept(File dir, String name) {
            Pattern pattern = Pattern.compile(filter);
            Matcher matcher = pattern.matcher(name);
            return new File(dir, name).isDirectory() || matcher.find();
        }
    }

    public static void main(String[] args) {
//        System.out.println(new MyLs().listFileAsString(new File(".")));
//        System.out.println(new MyLs().deepList(new File(".")));
        System.out.println("MyLs.main \n" + new MyLs().deepListWithFilter(new File("."), ".class"));
        System.out.println("MyLs.main \n" + new MyLs().deepListWithFilterExtern(new File("."), ".class"));
        System.out.println("MyLs.main \n" + new MyLs().deepListWithFilterIntern(new File("."), ".class"));
    }
}

