package KMeans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixy
 */
public class Cluster {
    private int id;
    private Seed center;
    private List<Seed> seeds;

    public int getId() {
        return id;
    }

    public Cluster(int id, Seed center) {
        this.id = id;
        this.center = center;
        this.seeds = new ArrayList<>();
    }

    public void addPoint(Seed seed) {
        seeds.add(seed);
    }

    public void clearPoints() {
        seeds.clear();
    }

    public double getDistanceToCenter(Seed seed) {
        // 计算数据点到聚类中心的距离
        double da = center.getArea()-seed.getArea();
        double dp = center.getPerimeter()-seed.getPerimeter();
        double dl = center.getLengthOfKernel()-seed.getLengthOfKernel();
        double dw = center.getWidthOfKernel()-seed.getWidthOfKernel();
        double dc = center.getAsyC()-seed.getAsyC();
        double dg = center.getLengthOfGroove()-seed.getLengthOfGroove();
        return Math.sqrt(da*da + dp*dp + dl*dl + dw*dw + dc*dc + dg*dg);
    }

    public boolean updateCenter() {
        // 更新聚类中心为聚类中所有数据点的均值
        double totalA = 0.0;
        double totalP = 0.0;
        double totalL = 0.0;
        double totalW = 0.0;
        double totalC = 0.0;
        double totalG = 0.0;

        for (Seed seed : seeds) {
            totalA += seed.getArea();
            totalP += seed.getPerimeter();
            totalL += seed.getLengthOfKernel();
            totalW += seed.getWidthOfKernel();
            totalC += seed.getAsyC();
            totalG += seed.getLengthOfGroove();
        }

        double newCenterA = totalA / seeds.size();
        double newCenterP = totalP / seeds.size();
        double newCenterL = totalL / seeds.size();
        double newCenterW = totalW / seeds.size();
        double newCenterC = totalC / seeds.size();
        double newCenterG = totalG / seeds.size();
        Seed newCenter = new Seed(newCenterA,newCenterP,newCenterL,newCenterW,newCenterC,newCenterG);
        if (newCenter.equals(center)){
            return false;
        }
        center = newCenter;
        return true;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }
}
