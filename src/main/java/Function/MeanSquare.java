package Function;

public class MeanSquare implements LossFunction{

    @Override
    public Double protoFun(Double standardRes, Double y) {
        return 0.5*Math.pow(standardRes-y,2);
    }

    @Override
    public Double derivativeFun(Double standardRes, Double y) {
        return standardRes-y;
    }
}
