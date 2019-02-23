package com.chau.abear;

import java.io.*;

public class FileTest {

    public static void main(String[] args) throws Exception {

//        System.out.println("utf8格式写入的文件的字符集编码为: " + getFilecharset(generateFile("E:/aabb.txt", "UTF-8")));
//        System.out.println("gbk格式写入的文件的字符集编码为: " + getFilecharset(generateFile("E:/ccdd.txt", "GBK")));
        String charset = getFilecharset(new File("E:/a.txt"));
        System.out.println("Windows系统创建的文件的默认字符集编码为: " + charset);
        System.out.println("文本内容为： " + readFileContent("E:/a.txt", charset));
    }

    private static String readFileContent(String filepath, String charset) throws IOException {

        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        StringBuffer sb = new StringBuffer();
        String str = null;
        while((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    private static File generateFile(String name, String charset) throws Exception {
        File file = new File(name);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = null;
        fos = new FileOutputStream(file);
        byte[] buff = "这是检验乱码问题的demo".getBytes(charset);
        fos.write(buff);
        fos.flush();
        fos.close();
        return file;
    }


    private static  String getFilecharset(File sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                    int loc = 0;
                    while ((read = bis.read()) != -1) {
                        loc++;
                        if (read >= 0xF0)
                            break;
                        if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                            break;
                        if (0xC0 <= read && read <= 0xDF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                                // (0x80
                                // - 0xBF),也可能在GB编码内
                                continue;
                            else
                                break;
                        } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                read = bis.read();
                                if (0x80 <= read && read <= 0xBF) {
                                    charset = "UTF-8";
                                    break;
                                } else
                                    break;
                            } else
                                break;
                        }
                    }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }




}
