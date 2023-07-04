package Function;

import Layer.NeuralLayer;

public class SoftMax implements ActivationFunction{
    @Override
    public Double protoFun(Double independentVar) {
        return null;
    }

    @Override
    public Double derivativeFun(Double dependentVar) {
        return dependentVar;
    }

    @Override
    public Double protoFun(NeuralLayer layer,int index){
        Double sum = 0d;
        for (int i =0;i<layer.getNumberOfNeuralNodes();i++){
            sum += Math.pow(Math.E,layer.getNodes().get(i).getInputSum());
        }
        Double y =Math.pow(Math.E,layer.getNodes().get(index).getInputSum())/sum;
        return y;
    }

}
