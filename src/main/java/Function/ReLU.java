package Function;

public class ReLU implements ActivationFunction{
    @Override
    public Double protoFun(Double independentVar) {
        return independentVar;
    }

    @Override
    public Double derivativeFun(Double dependentVar) {
        return 1d;
    }
}
