package com.softserve.hotels.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZipUtils {

    private static final Logger LOG = LogManager.getLogger(ZipUtils.class);

    private static List<String> fileList;
    private static byte[] buffer = new byte[1024];

    private ZipUtils() {
    }

    public static void compress(String sourceFolder, String resultFile) {
        fileList = new ArrayList<>();
        generateFileList(sourceFolder, new File(sourceFolder));
        try {
            FileOutputStream fos = new FileOutputStream(resultFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String file : fileList) {
                ZipEntry zipEntry = new ZipEntry(file);
                zos.putNextEntry(zipEntry);
                FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            }
            zos.closeEntry();
            zos.close();
            fos.close();
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    public static void decompress(String zipFile, String outputFolder) {
        fileList = new ArrayList<>();
        try {

            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(outputFolder + File.separator + zipEntry.getName());
                // create all non-exists folders
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    private static void generateFileList(String sourceFolder, File node) {
        // add only file into the list
        if (node.isFile()) {
            fileList.add(generateZipEntry(sourceFolder, node.getAbsoluteFile().toString()));
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(sourceFolder, new File(node, filename));
            }
        }
    }

    private static String generateZipEntry(String sourceFolder, String file) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }

}
