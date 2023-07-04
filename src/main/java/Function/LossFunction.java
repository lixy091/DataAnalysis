package Function;

public interface LossFunction {
    Double protoFun(Double standardRes, Double y);

    Double derivativeFun(Double standardRes, Double y);
}
