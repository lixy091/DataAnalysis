package Layer;

import Function.ActivationFunction;
import Function.ReLU;

import java.util.LinkedList;

public class InputLayer extends NeuralLayer{
    //获取并分配样本值
    public void handleInput(LinkedList<Double> inputList) {
        for (int i = 0 ; i<numberOfNeuralNodes ; i++){
            nodes.get(i).setInputSum(inputList.get(i));
        }
    }

    @Override
    public void init(int layerNodeNumber, ActivationFunction activationFunction) {
        activationFunction = new ReLU();
        super.init(layerNodeNumber, activationFunction);
    }
}
