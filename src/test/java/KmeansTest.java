import KMeans.Cluster;
import KMeans.Kmeans;
import KMeans.Seed;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lixy
 */
public class KmeansTest {

    @Test
    public void testKmeans() {
        // 创建K-means对象
        Kmeans kMeans = new Kmeans(3, 20);
        File file = new File("C:\\Users\\idt\\Desktop\\抽屉\\数据表\\种子.xlsx");
        try (XSSFWorkbook sheets = new XSSFWorkbook(file)) {
            XSSFSheet sheet = sheets.getSheetAt(0);
            for (Row row : sheet) {
                kMeans.addSeed(new Seed(row.getCell(0).getNumericCellValue()
                        , row.getCell(1).getNumericCellValue()
                        , row.getCell(2).getNumericCellValue()
                        , row.getCell(3).getNumericCellValue()
                        , row.getCell(4).getNumericCellValue()
                        , row.getCell(5).getNumericCellValue()));
            }
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        // 执行聚类分析
        kMeans.cluster();

        // 获取聚类结果
        List<Cluster> clusters = kMeans.getClusters();

        // 输出聚类结果
        for (Cluster cluster : clusters) {
            System.out.println("Cluster " + (cluster.getId()+1) + ":");
            List<Seed> seeds = cluster.getSeeds();
            System.out.println("共"+seeds.size()+"条");
            for (Seed seed : seeds) {
                //System.out.println("(" + seed.getX() + ", " + seed.getY() + ")");
                System.out.println(seed.toString());
            }
            System.out.println();
        }
    }

    @Test
    public void testXSSF(){
        File file = new File("C:\\Users\\idt\\Desktop\\抽屉\\数据表\\种子.xlsx");
        try (XSSFWorkbook sheets = new XSSFWorkbook(file)) {
            XSSFSheet sheet = sheets.getSheetAt(0);
            for (Row row : sheet){
                for (Cell cell : row){
                    System.out.println(cell.getNumericCellValue());
                }
                System.out.println();
            }
        }catch (IOException | InvalidFormatException e){
            e.printStackTrace();
        }
    }
}
