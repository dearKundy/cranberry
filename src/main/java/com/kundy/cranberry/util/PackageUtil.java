package com.kundy.cranberry.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author kundy
 * @date 2019/10/14 4:10 PM
 */
public class PackageUtil {

    public static void main(String[] args) {
        String packageName = "com.oracle.tools.packager";
        List<String> classNames = getPackageAllClassName(packageName, true);
        classNames.forEach(System.out::println);
    }

    public static List<String> getPackageAllClassName(String packageName, boolean isScanChildPackage) {
        List<String> result = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url == null) {
            return null;
        }
        String type = url.getProtocol();
        if (type.equals("file")) {
            return getAllClassNameFromFile(url.getPath(), isScanChildPackage);
        }
        if (type.equals("jar")) {
            return getAllClassNameFromJar(url.getPath(), isScanChildPackage);
        }
        return null;
    }

    /**
     * 从项目文件获取某包下所有类名
     */
    private static List<String> getAllClassNameFromFile(String fileName, boolean isScanChildPackage) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(fileName);
        File[] childFiles = file.listFiles();
        if (childFiles != null && childFiles.length > 0) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    if (isScanChildPackage) {
                        myClassName.addAll(getAllClassNameFromFile(childFile.getPath(), isScanChildPackage));
                    }
                } else {
                    String childFilePath = childFile.getPath();
                    if (childFilePath.endsWith(".class")) {
                        childFilePath = childFilePath.substring(childFilePath.indexOf("classes") + 8, childFilePath.lastIndexOf("."));
                        childFilePath = childFilePath.replace("/", ".");
                        myClassName.add(childFilePath);
                    }
                }
            }
        }
        return myClassName;
    }

    private static List<String> getAllClassNameFromJar(String jarPath, boolean isScanChildPackage) {
        List<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1];
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (isScanChildPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }


}
