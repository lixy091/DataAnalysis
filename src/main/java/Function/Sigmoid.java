package Function;

public class Sigmoid implements ActivationFunction{

    public Double protoFun(Double independentVar){
        return 1d/(1d + Math.pow(Math.E,-1*independentVar));
    }

    public Double derivativeFun(Double dependentVar){
        return dependentVar*(1d-dependentVar);
    }
}
