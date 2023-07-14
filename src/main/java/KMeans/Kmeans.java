package KMeans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixy
 */
public class Kmeans {


    private int k; // 聚类数量
    private int maxIterations; // 最大迭代次数
    private List<Seed> seeds; // 数据点
    private List<Cluster> clusters; // 聚类结果

    public Kmeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.seeds = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public void addSeed(Seed seed) {
        seeds.add(seed);
    }

    public void cluster() {
        // 1. 初始化聚类中心
        initializeClusters();

        // 2. 迭代更新聚类结果
        int iteration = 0;
        while (iteration < maxIterations) {
            clearClusters();

            // 2.1 根据当前聚类中心将数据点分配到最近的聚类
            assignSeedsToClusters();


            // 2.2 更新聚类中心
            if (!updateClusterCenters()){
                System.out.println("迭代了"+ iteration + "次");
                break;
            }

            iteration++;

        }
    }

    private void initializeClusters() {
        // 随机选择k个数据点作为初始聚类中心
        for (int i = 0; i < k; i++) {
            Seed seed = seeds.get(i);
            Cluster cluster = new Cluster(i, seed);
            clusters.add(cluster);
        }
    }

    private void clearClusters() {
        // 清空聚类结果中的数据点
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }
    }

    private void assignSeedsToClusters() {
        // 遍历所有数据点
        for (Seed seed : seeds) {
            double minDistance = Double.MAX_VALUE;
            Cluster nearestCluster = null;

            // 遍历所有聚类中心，找到最近的聚类中心
            for (Cluster cluster : clusters) {
                double distance = cluster.getDistanceToCenter(seed);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCluster = cluster;
                }
            }

            // 将数据点分配给最近的聚类中心
            if (nearestCluster != null) {
                nearestCluster.addPoint(seed);
            }
        }
    }

    private boolean updateClusterCenters() {
        boolean flag = false;
        // 遍历所有聚类
        for (Cluster cluster : clusters) {
            // 计算聚类中所有数据点的均值，更新聚类中心
            if (cluster.updateCenter()){
                flag = true;
            }
        }
        return flag;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }
}



