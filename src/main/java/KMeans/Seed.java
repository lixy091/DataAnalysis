package KMeans;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * @author lixy
 */
@Data
@ToString
public class Seed {


    private double area;//面积
    private double perimeter;//周长
    private double lengthOfKernel;//内核长度
    private double widthOfKernel;//内核宽度
    private double asyC;//asymmetryCoefficient 不对称系数
    private double lengthOfGroove;//内核槽的长度

    public Seed(double area, double perimeter, double lengthOfKernel, double widthOfKernel, double asyC, double lengthOfGroove) {
        this.area = area;
        this.perimeter = perimeter;
        this.lengthOfKernel = lengthOfKernel;
        this.widthOfKernel = widthOfKernel;
        this.asyC = asyC;
        this.lengthOfGroove = lengthOfGroove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return Double.compare(seed.area, area) == 0 && Double.compare(seed.perimeter, perimeter) == 0 && Double.compare(seed.lengthOfKernel, lengthOfKernel) == 0 && Double.compare(seed.widthOfKernel, widthOfKernel) == 0 && Double.compare(seed.asyC, asyC) == 0 && Double.compare(seed.lengthOfGroove, lengthOfGroove) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, perimeter, lengthOfKernel, widthOfKernel, asyC, lengthOfGroove);
    }
}

