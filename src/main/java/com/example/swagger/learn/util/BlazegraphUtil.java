package com.example.swagger.learn.util;

import com.bigdata.blueprints.BigdataGraphClient;
import com.tinkerpop.blueprints.Edge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.bigdata.blueprints.BigdataGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * blazegraph工具类
 */
@Component
public class BlazegraphUtil {

    @Value("${blaze.url}")
    private String serviceURl;

    /**
     * 返回所有边
     * @return
     */
    public List<String> returnAllEdge(){


        final BigdataGraph graph =new BigdataGraphClient(serviceURl + "/sparql");
        List<String> edgeList = new ArrayList<>();
        try{
            Iterable<Edge> edges = graph.getEdges();
            edges.forEach(System.out::println);
            edges.forEach(edge -> edgeList.add(edge.toString()));
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            return edgeList;
        }
    }

    // 条件查询
    public List<String> returnSomeEdge(){
        List<String> edgeList = new ArrayList<>();
        final BigdataGraph graph =new BigdataGraphClient(serviceURl + "/sparql");
        final String queryStr = "";
        graph.getEdges("3",0);

        return edgeList;
    }
}
