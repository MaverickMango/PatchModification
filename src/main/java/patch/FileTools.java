package patch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileTools {

    public static String formatSeperatar(String filePath) {
        return filePath.replaceAll("/", File.separator);
    }

    public static StringBuilder getMap2String(Map map) {
        Set keySet = map.keySet();
        Iterator iterator = keySet.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while(iterator.hasNext()) {
            Object obj = iterator.next();
            stringBuilder.append("<").append(obj).append(", ");
            Object value = map.get(obj);
            if (value instanceof Collection)
                stringBuilder.append(((Collection<?>) value).size()).append("-");
            stringBuilder.append(value).append(">").append("\n");
        }
        return stringBuilder;
    }

    public static void outputMap(Map map) {
        System.out.println(getMap2String(map).toString());
    }

    public static void outputMap2File(Map map, String filePath, boolean append) {
        StringBuilder stringBuilder = getMap2String(map);
        FileTools.writeToFile(stringBuilder.toString(), filePath, append);
    }


    public static void writeToFile(String content, String filename, boolean append) {
        filename = formatSeperatar(filename);
        BufferedOutputStream buff =null;
        try {
            buff = new BufferedOutputStream(new FileOutputStream(filename, append));
            buff.write(content.getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToFile(String content, String filename) {
        filename = formatSeperatar(filename);
        BufferedOutputStream buff =null;
        try {
            buff = new BufferedOutputStream(Files.newOutputStream(Paths.get(filename)));
            buff.write(content.getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char[] readFileByChars(String fileName) {
        fileName = formatSeperatar(fileName);
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
        fileName = formatSeperatar(fileName);
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

    public static List<String> readEachLine(String fileName) {
        fileName = formatSeperatar(fileName);
        File file = new File(fileName);
        BufferedReader reader = null;

        List<String> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
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

    public static String readOneLine(String fileName) {
        fileName = formatSeperatar(fileName);
        File file = new File(fileName);
        BufferedReader reader = null;

        String res = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            res = reader.readLine();
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
        return res == null ? "" : res;
    }


    /**
     * 读取predicates列表
     * @param fileName
     * @return 谓词表达式
     */
    public static List<String> readPredicates(String fileName) {
        fileName = formatSeperatar(fileName);
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
        fileDir = formatSeperatar(fileDir);
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
    public static List<String> getFilePath(String fileDir, String postfix) {
        fileDir = formatSeperatar(fileDir);
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

            }
        }
        return paths;
    }

    public static List<String> getDirNames(String fileDir) {
        fileDir = formatSeperatar(fileDir);
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

    public static boolean isFileExist(String filePath) {
        filePath = formatSeperatar(filePath);
        File file = new File(filePath);
        return file.exists();
    }

    public static List<String> execute(String[] command) {
        Process process = null;
        final List<String> message = new ArrayList<String>();
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            process = builder.start();
            final InputStream inputStream = process.getInputStream();

            Thread processReader = new Thread(){
                public void run() {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    try {
                        while((line = reader.readLine()) != null) {
                            message.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            processReader.start();
            try {
                processReader.join();
                process.waitFor();
            } catch (InterruptedException e) {
                return new LinkedList<>();
            }
        } catch (IOException e) {
        } finally {
            if (process != null) {
                process.destroy();
            }
            process = null;
        }

        return message;
    }
}
