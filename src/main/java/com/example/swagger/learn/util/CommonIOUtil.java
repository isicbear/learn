package com.example.swagger.learn.util;

import com.bigdata.rdf.sail.webapp.client.RemoteRepository;
import com.bigdata.rdf.sail.webapp.client.RemoteRepositoryManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.rio.RDFFormat;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CommonIOUtil {

    private static final String serviceURL = "http://182.92.118.11/blazegraph";
    final static RemoteRepositoryManager repo = new RemoteRepositoryManager(
            serviceURL, false /* useLBS */);
    final static RemoteRepository repository = repo.getRepositoryForNamespace("scistor");


    @Test
    public void createNameSpqce() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty("com.bigdata.rdf.sail.namespace", "qiaoqiao");
        repo.createRepository("qiaoqiao", properties);
    }

    @Test
    public void test() {
        File file = new File("E:\\scistor\\baiketriples\\baike_triples.txt");
        List<String[]> list = new ArrayList<>();
        try {
            int i = 0;
            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
            StringBuffer insert = new StringBuffer();
            insert.append("INSERT { \n");

            // 特殊字符太多了 转不完
            while (lineIterator.hasNext()) {
                i++;
                String next = lineIterator.next();
                String replace = next.replace('\"', '\'').replace("“", "'").replace("”", "'").replace(" ", "").replace("<", "").replace(">", "");
                String[] strings = replace.split("\t");
                // todo   load data to blazegraph  6500000条数据比较合适
                if (strings[1].equals("BaiduTAG")) {
                    insert.append("<http://scistor.com/").append(strings[0]).append("> rdf:type <http://scistor.type.com/").append(strings[2]).append(">.\n");
                } else if (strings[1].equals("BaiduCARD")) {
                    insert.append("<http://scistor.com/").append(strings[0]).append(">  <http://scistor.type.com/desc> \"").append(strings[2]).append("\".\n");
                } else {
                    insert.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.attr.com/").append(strings[1]).append("> <http://scistor.com/").append(strings[2]).append(">.\n");
                }
                list.add(strings);
                if (i > 16) break;
            }
            insert.append("}where{ ?s  ?p ?o  }");

            System.out.println(insert);
            System.out.println("--------------------------" + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不可行 .n3 对于格式要求太多 todo 不使用前缀的情况下 可以完美解决问题 只需要替换下特殊字符
     */
    @Test
    public void test1() {
        File file = new File("E:\\scistor\\baiketriples\\baike_triples.txt");
        List<String[]> list = new ArrayList<>();
        try {
            int i = 0;
            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
            StringBuffer n3Str = new StringBuffer();
           /* n3Str.append("PREFIX : <http://scistor.com/>\n");
            n3Str.append("PREFIX type: <http://scistor.type.com/>\n");
            n3Str.append("PREFIX attr: <http://scistor.attr.com/>\n");
            n3Str.append("PREFIX desc: <http://scistor.desc.com>\n");*/
            // 特殊字符太多了 转不完
            while (lineIterator.hasNext()) {
                i++;
                String next = lineIterator.next();
               // String encode = URLEncoder.encode(next, StandardCharsets.UTF_8);
                String replace = next.replace('\"', '_').replace("“", "_").replace("”", "_").replace(" ", "");
                String[] strings = replace.split("\t");
                // todo   load data to blazegraph  6500000条数据比较合适
                if (strings[1].equals("BaiduTAG")) {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> a <http://scistor.type.com/").append(strings[2]).append("> .\n");
                } else if (strings[1].equals("BaiduCARD")) {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.desc.com> \"").append(strings[2]).append("\" .\n");
                } else {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.com/").append(strings[1]).append("> <http://scistor.com/").append(strings[2]).append("> .\n");
                }
                list.add(strings);
                if (i > 16) break;
            }
            System.out.println(n3Str);
            System.out.println("--------------------------" + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        /*File file = new File("E:\\scistor\\2baike_triples.txt");
        try {
            int i = 0;
            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
            while (lineIterator.hasNext()) {
                String next = lineIterator.next();
                System.out.println(next);
                String encode = URLEncoder.encode(next, StandardCharsets.UTF_8);
                String[] strings = encode.split("%09");
                System.out.println(strings[0]);
                System.out.println(strings[1]);
                System.out.println(strings[2]);
                System.out.println(URLDecoder.decode(strings[0], StandardCharsets.UTF_8));
                System.out.println(URLDecoder.decode(strings[1], StandardCharsets.UTF_8));
                System.out.println(URLDecoder.decode(strings[2], StandardCharsets.UTF_8));
                System.out.println(URLEncoder.encode("\t", StandardCharsets.UTF_8));
                System.out.println(encode);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    // todo useful 效率高 但是数据量过大的时候会造成数据丢失
    public static void loadByn3(String fileName) throws Exception {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(12);
        File file = new File(fileName);
        List<String[]> list = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
        try {
            int i = 0;
            StringBuilder n3Str = new StringBuilder();
            while (lineIterator.hasNext()) {
                i++;
                String next = lineIterator.next();
                String replace = next.replace('\"', '\'').replace("“", "'").replace("”", "'")
                        .replace(" ", "").replace("<", "").replace(">", "")
                        .replace("\\", "").replace("{", "").replace("}", "")
                        .replace("|", "").replace("/", "");
                String[] strings = replace.split("\t"); // %09
                // todo   load data to blazegraph  6500000条数据比较合适
                if (strings[1].equals("BaiduTAG")) {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> a <http://scistor.type.com/").append(strings[2]).append("> .\n");
                } else if (strings[1].equals("BaiduCARD")) {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.desc.com> \"").append(strings[2]).append("\" .\n");
                } else {
                    n3Str.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.attr.com/").append(strings[1]).append("> <http://scistor.com/").append(strings[2]).append("> .\n");
                }

                if (i % 2000 == 0) {
                    StringBuilder finalN3Str = n3Str;
                    newFixedThreadPool.execute(() -> {
                        try {
                            final InputStream is = IOUtils.toInputStream(finalN3Str.toString(), StandardCharsets.UTF_8.name());
                            try {
                                repository.add(new RemoteRepository.AddOp(is, RDFFormat.N3));
                            } finally {
                                is.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    n3Str = new StringBuilder();
                }
            }
            StringBuilder finalN3Str = n3Str;
            newFixedThreadPool.execute(() -> {
                try {
                    final InputStream is = IOUtils.toInputStream(finalN3Str.toString(), StandardCharsets.UTF_8.name());
                    try {
                        repository.add(new RemoteRepository.AddOp(is, RDFFormat.N3));
                    } finally {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            newFixedThreadPool.shutdown();
            try {
                // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
                while (!newFixedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----------------------------" + fileName + " game over--------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lineIterator.close();
        }
    }

    // todo 效率低 占用过多资源 导致堆内存溢出 非常不好
    public static void load(String fileName) throws Exception {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(12);
        File file = new File(fileName);
        List<String[]> list = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
        try {
            int i = 0;
            StringBuilder insert = new StringBuilder();
            insert.append("INSERT { \n");
            while (lineIterator.hasNext()) {
                i++;
                String next = lineIterator.next();
                String replace = next.replace('\"', '\'').replace("“", "'").replace("”", "'")
                        .replace(" ", "").replace("<", "").replace(">", "")
                        .replace("\\", "").replace("{", "").replace("}", "")
                        .replace("|", "").replace("/", "");
                String[] strings = replace.split("\t"); // %09
                // todo   load data to blazegraph  6500000条数据比较合适
                if (strings[1].equals("BaiduTAG")) {
                    insert.append("<http://scistor.com/").append(strings[0]).append("> rdf:type <http://scistor.type.com/").append(strings[2]).append(">.\n");
                } else if (strings[1].equals("BaiduCARD")) {
                    insert.append("<http://scistor.com/").append(strings[0]).append(">  <http://scistor.desc.com> \"").append(strings[2]).append("\".\n");
                } else {
                    insert.append("<http://scistor.com/").append(strings[0]).append("> <http://scistor.attr.com/").append(strings[1]).append("> <http://scistor.com/").append(strings[2]).append(">.\n");
                }

                if (i % 3000 == 0) {
                    insert.append("}where{ ?s  ?p ?o  }");
                    try {
                        StringBuilder finalInsert = insert;
                        newFixedThreadPool.execute(() -> {
                            try {
                                repository.prepareUpdate(finalInsert.toString()).evaluate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    insert = new StringBuilder();
                    insert.append("INSERT { \n");
                }

                // list.add(strings);
                //if (i > 1000) break;
            }

            insert.append("}where{ ?s  ?p ?o  }");
            StringBuilder finalInsert = insert;
            newFixedThreadPool.execute(() -> {
                try {
                    repository.prepareUpdate(finalInsert.toString()).evaluate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lineIterator.close();
        }
    }

    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 31; i <= 36; i++) {
            String fileName = "E:\\scistor\\" + i + "baike_triples.txt";
            newCachedThreadPool.execute(() -> {
                try {
                    loadByn3(fileName);
                    //System.out.println("----------------------------------------------------------------------------"+Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        newCachedThreadPool.shutdown();
        try {
            // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
            while (!newCachedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------------------game over-----------------------------------------");
    }

    @Test
    public void test4() {
        List<File> files = CommonIOUtil.splitDataToSaveFile(1000000, new File("E:\\scistor\\baiketriples\\baike_triples.txt"), "E:\\scistor");
        System.out.println(files.size());
    }

    /**
     * 按行分割文件
     */
    public static List<File> splitDataToSaveFile(int rows, File sourceFile, String targetDirectoryPath) {
        long startTime = System.currentTimeMillis();
        List<File> fileList = new ArrayList<>();
        File targetFile = new File(targetDirectoryPath);
        if (!sourceFile.exists() || rows <= 0 || sourceFile.isDirectory()) {
            return null;
        }
        if (targetFile.exists()) {
            if (!targetFile.isDirectory()) {
                return null;
            }
        } else {
            targetFile.mkdirs();
        }

        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder stringBuilder = new StringBuilder();
            String lineStr;
            int lineNo = 1, fileNum = 1;
            while ((lineStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineStr).append("\r\n");
                if (lineNo % rows == 0) {
                    File file = new File(targetDirectoryPath + File.separator + fileNum + sourceFile.getName());
                    writeFile(stringBuilder.toString(), file);
                    //清空文本
                    stringBuilder.delete(0, stringBuilder.length());
                    fileNum++;
                    fileList.add(file);
                }
                lineNo++;
                // 这里进行数据处理
            }
            if ((lineNo - 1) % rows != 0) {
                File file = new File(targetDirectoryPath + File.separator + fileNum + sourceFile.getName());
                writeFile(stringBuilder.toString(), file);
                fileList.add(file);
            }
            long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private static void writeFile(String text, File file) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter, 1024)
        ) {
            bufferedWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo 读bz中数据 写入到mysql 类目
    public static List<String> test6() {

        String queryStr = "select (count(?s)  as ?s) ?o {?s rdf:type ?o. } group by ?o  having( count(?s) > 10000 ) order by desc(count(?s)) limit 100";
        List<String> categorys = new ArrayList<>();

        try {
            TupleQueryResult result = repository.prepareTupleQuery(queryStr).evaluate();
            try {
                while (result.hasNext()) {
                    BindingSet next = result.next();
                    String category = next.getBinding("o").getValue().stringValue();
                    String substring = category.substring(category.lastIndexOf('/') + 1);
                    categorys.add(substring);
                }
            } finally {
                result.close();
            }
            return categorys;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorys;
    }

    // todo 读bz中数据 写入到mysql 类目 知识
    public static Map<String, String> test7(String category) {
        Map<String, String> map = new HashMap<>();
        String queryStr = "select ?s ?o { ?s rdf:type <http://scistor.type.com/" + category + ">." +
                "?s <http://scistor.desc.com> ?o." +
                " } limit 1000";

        try {
            TupleQueryResult result = repository.prepareTupleQuery(queryStr).evaluate();
            try {
                while (result.hasNext()) {
                    BindingSet next = result.next();
                    String know = next.getBinding("s").getValue().stringValue();
                    String desc = next.getBinding("o").getValue().stringValue();
                    String substring = know.substring(know.lastIndexOf('/') + 1);
                    map.put(substring, desc);
                }
            } finally {
                result.close();
            }
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // todo 读bz中数据 写入到mysql 类目 属性
    public static List<String> test8(String category) {
        List<String> list = new ArrayList<>();
        String queryStr = "select   distinct ?attr  {\n" +
                "  ?s rdf:type <http://scistor.type.com/" + category + "> .\n" +
                "  ?s ?attr ?o\n" +
                "  filter(?attr != rdf:type)\n" +
                "  filter( ?attr != <http://scistor.desc.com>)\n" +
                "} group by ?attr order by desc(count(?s)) limit 20";

        try {
            TupleQueryResult result = repository.prepareTupleQuery(queryStr).evaluate();
            try {
                while (result.hasNext()) {
                    BindingSet next = result.next();
                    String attr = next.getBinding("attr").getValue().stringValue();
                    String substring = attr.substring(attr.lastIndexOf('/') + 1);
                    list.add(substring);
                }
            } finally {
                result.close();
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // todo randomAccessFile 结合线程池 实现 多段文件拷贝
    public void test9() throws Exception {

        RandomAccessFile file = new RandomAccessFile("", "");


    }


    public static Map<String,String> test10(String key) {

        String queryStr = "select * where {<http://scistor.com/" + key + ">  ?p ?o }";
        Map<String,String> map = new HashMap<>();
        try {
            TupleQueryResult result = repository.prepareTupleQuery(queryStr).evaluate();
            while (result.hasNext()) {
                BindingSet next = result.next();
                String o = next.getBinding("o").getValue().stringValue();
                String p = next.getBinding("p").getValue().stringValue();
                // type 和 desc 要特殊处理
                getProp(map,o,p);
            }
            result.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static void getProp(Map<String,String> map,String o,String p) {
        if(p.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
            map.put("type",o.substring(o.lastIndexOf('/') + 1));
        } else if(p.equals("http://scistor.desc.com")){
            map.put("desc",o);
        }else {
            map.put(p.substring(p.lastIndexOf('/') + 1),o.substring(o.lastIndexOf('/') + 1));
        }
    }


}
