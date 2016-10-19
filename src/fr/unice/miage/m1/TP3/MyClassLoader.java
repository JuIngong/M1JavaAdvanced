package fr.unice.miage.m1.TP3;

import java.io.*;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class created on 19/10/2016
 *
 * @author JuIngong
 */
public class MyClassLoader extends SecureClassLoader {
    private List<File> path = new ArrayList<>();

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassData(name);
        return super.defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        for (File f : path) {
            //            if (f.isFile() && f.getName().endsWith(".jar")) {
            //                JarFile file = null;
            //                try {
            //                    file = new JarFile(f);
            //                    JarEntry entry = file.getJarEntry(File.separatorChar + name.replace('.', File.separatorChar) + ".class");
            //                    if (entry != null) {
            //                        file.getInputStream(entry);
            //                    }
            //                } catch (IOException e) {
            //                    e.printStackTrace();
            //                }
            //            } else
            if (f.isFile() && (f.getName().endsWith(".zip") || f.getName().endsWith(".jar"))) {
                ZipFile file = null;
                try {
                    file = new ZipFile(f);
                    ZipEntry entry = file.getEntry(name.replace('.', '/') + ".class");
                    if (entry != null) {
                        InputStream is = file.getInputStream(entry);
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        int nRead;
                        byte[] data = new byte[16384];
                        while ((nRead = is.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                        }
                        buffer.flush();

                        return buffer.toByteArray();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!f.isDirectory()) {
                System.out.println("Not a directory");
            } else {
                try {
                    File file = new File(f.getAbsolutePath() + File.separatorChar + name.replace('.', File.separatorChar) + ".class");
                    if (file.exists()) {
                        DataInputStream is = new DataInputStream(new FileInputStream(file));
                        int len = (int) file.length();
                        byte[] buff = new byte[len];
                        is.readFully(buff);
                        is.close();
                        return buff;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new ClassNotFoundException();
    }

    public List<File> getPath() {
        return path;
    }

    public void setPath(List<File> path) {
        this.path = path;
    }

    public static void main(String[] args) {
        MyClassLoader mc = new MyClassLoader();
        List<File> files = new ArrayList<>();
        files.add(new File("C:\\Users\\JuIngong\\Documents\\Miage\\M1\\Pattern\\TPs\\out\\production\\TPs"));
        files.add(new File("C:\\Users\\JuIngong\\Documents\\Miage\\M1\\Pattern\\TPs\\out\\production\\TPs\\fr.zip"));
        mc.setPath(files);
        try {
            System.out.println(mc.loadClass("fr.unice.miage.m1.TP1.MyLs"));
            System.out.println(mc.loadClass("fr.unice.miage.m1.TP1.DummyWriter"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}