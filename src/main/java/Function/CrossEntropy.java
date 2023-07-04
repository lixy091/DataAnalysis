package Function;

public class CrossEntropy implements LossFunction{
    @Override
    public Double protoFun(Double standardRes, Double y) {
        return null;
    }

    @Override
    public Double derivativeFun(Double standardRes, Double y) {
        return standardRes-y;
    }
}
