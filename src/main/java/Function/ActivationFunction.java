package Function;

import Layer.NeuralLayer;

public interface ActivationFunction {
    Double protoFun(Double independentVar);

    Double derivativeFun(Double dependentVar);

    default Double protoFun(NeuralLayer layer, int index){return null;}
}
