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

    /**
     * 去除因果图当中由于predicates transform重复导致的变量重复
     * @param filename causalMap.txt文件所在路径
     */
    public static void removeRepet(String filename) {
        List<List<String>> causalMap = createCausalMap(filename);
        assert causalMap != null;
        for (int i = 0; i< causalMap.size(); i ++) {
            List<String> list = causalMap.get(i);
            String last = list.get(0);
            List<Integer> index = new ArrayList<>();
            for (int j = 1; j < list.size(); j++) {
                if (!last.equals(list.get(j))) {
                    index.add(j);
                }
                last = list.get(j);
            }
            List<String> newList = new ArrayList<>();
            newList.add(list.get(0));
            for (int key : index) {
                newList.add(list.get(key));
            }
            causalMap.set(i, newList);
        }
        writecausalMap(causalMap, filename);
    }

    /**
     * 把因果图列表写入文件
     * @param causalMap 因果图列表
     * @param filename 写入文件路径
     */
    public static void writecausalMap(List<List<String>> causalMap, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> list:causalMap) {
            for (String str:list) {
                stringBuilder.append(str).append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        }

        BufferedOutputStream buff =null;
        try {
            buff = new BufferedOutputStream(new FileOutputStream(filename));
            buff.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> createCausalMap(String filename) {
        List<List<String>> causalMap = new ArrayList<>();

        BufferedReader reader;
        try {
            // Change name of data file accordingly
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                String[] row = line.split(",");
                causalMap.add(Arrays.asList(row));
                line = reader.readLine();
            }
            reader.close();
            return causalMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
