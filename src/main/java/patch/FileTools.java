package patch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileTools {


    public static void outputMap(Map map) {
        Set keySet = map.keySet();
        Iterator iterator = keySet.iterator();
        while(iterator.hasNext()) {
            Object obj = iterator.next();
            System.out.println("<" + obj + ", " + map.get(obj) + ">");
        }
    }


    public static void writeToFile(String content, String filename) {
        BufferedOutputStream buff =null;
        try {
            buff = new BufferedOutputStream(new FileOutputStream(filename));
            buff.write(content.getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char[] readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            // 一次读多个字符
            char[] tempchars = new char[1024];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    sb.append(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            sb.append(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
//        System.out.println(sb);
        return sb.toString().toCharArray();
    }

    /**
     * 文件读取 去掉换行符
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                tempString.replace('\r', ' ');
                sb.append(tempString);
//                System.out.println(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 存储补丁中包含的buggy文件路径信息,补丁修改位置行号(起始行号,偏移量,..)
     * @param fileName
     * @return list-{filepath, change start line, offset, offset,..}
     */
    public static List<String> readPatchInfo(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;

        List<String> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int i = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                i++;
                if (tempString.startsWith("---")) {
                    String[] temp = tempString.replace("\t", " ").split(" ");
                    list.add(temp[1]);
                }
                if (tempString.startsWith("@@")) {
                    String[] temp = tempString.replace(" ", "").replaceAll("[,+-]", " ").split(" ");
                    list.add(temp[1]);
                }
                if (tempString.startsWith("+ ")) {
                    String temp = String.valueOf(i - 5);
                    list.add(temp);
                }
                if (tempString.startsWith("+ ")) {
                    String temp = String.valueOf(i - 5);
                    list.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }


    /**
     * 读取predicates列表
     * @param fileName
     * @return 谓词表达式
     */
    public static List<String> readPredicates(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;

        List<String> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString.substring(tempString.indexOf("("), tempString.lastIndexOf(")")+1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }

    /**
     * read .txt filepath
     * @param fileDir
     * @return String[]
     */
    public static List<String> getFilePaths(String fileDir, String postfix) {
        List<String> paths = new ArrayList<>();
        //读取输入路径的文件
        File[] list = new File(fileDir).listFiles();
        for(File file : list)
        {
            if(file.isFile())
            {
                if (file.getName().endsWith(postfix)) {
                    // 就输出该文件的绝对路径
                    paths.add(file.getAbsolutePath());
                }

            } else if (file.isDirectory()) {
                paths.addAll(getFilePaths(file.getAbsolutePath(), postfix));
            }
        }
        return paths;
    }

    public static List<String> getDirNames(String fileDir) {
        List<String> paths = new ArrayList<>();
        //读取输入路径的文件
        File[] list = new File(fileDir).listFiles();
        for(File file : list)
        {
            if (file.isDirectory()) {
                paths.add(file.getName());
            }
        }
        return paths;
    }

}
