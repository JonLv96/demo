package pers.kksg.demo.algorithm.ip.hash;


import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author lvqiang
 */
public class WeightedRoundRobinLoadBalancer {
    private List<String> servers;
    private List<Integer> weights;
    private int currentIndex;

    public WeightedRoundRobinLoadBalancer(List<String> servers, List<Integer> weights) {
        this.servers = new ArrayList<>(servers);
        this.weights = new ArrayList<>(weights);
        this.currentIndex = 0;
    }

    public String getServer() {
        int serverCount = servers.size();
        int maxWeight = weights.stream().max(Integer::compareTo).orElse(1);

        while (true) {
            String server = servers.get(currentIndex);
            int weight = weights.get(currentIndex);

            if (weight >= maxWeight) {
                weights.set(currentIndex, weight - 1);
                currentIndex = (currentIndex + 1) % serverCount;
                return server;
            }

            currentIndex = (currentIndex + 1) % serverCount;
        }
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("server1", "server2", "server3");
        List<Integer> weights = Arrays.asList(3, 5, 2);

        WeightedRoundRobinLoadBalancer loadBalancer = new WeightedRoundRobinLoadBalancer(servers, weights);
        Map<String, Integer> map = new HashMap<>();
        // 模拟多个请求
        for (int i = 0; i < 100; i++) {
            //若所有权重都为零，则重置权重
            if (rebuild(loadBalancer.getWeights())) {
                loadBalancer.setWeights(Arrays.asList(3, 5, 2));
            }
            String server = loadBalancer.getServer();
            map.compute(server, (k, v) -> Objects.isNull(v) ? 1 : v + 1);
        }
        System.out.println(JSON.toJSONString(map));
    }

    private static boolean rebuild(List<Integer> weights) {
        for (Integer weight : weights) {
            if (weight > 0) {
                return false;
            }
        }
        return true;
    }


    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public List<Integer> getWeights() {
        return weights;
    }

    public void setWeights(List<Integer> weights) {
        this.weights = weights;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}


