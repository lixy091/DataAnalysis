package Layer;

import Function.ActivationFunction;
import Function.LossFunction;
import Function.SoftMax;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;

@Data
public class OutputLayer extends NeuralLayer{

    //损失函数
    private LossFunction lossFunction;
    //获取并分配增量
    public void handleInput(LinkedList<Double> inputList,LinkedList<Double> standardResult) {
        //损失函数乘上激活函数导数
        for (int i = 0;i<inputList.size();i++){
            double delta = lossFunction.derivativeFun(standardResult.get(i),inputList.get(i));
            inputList.set(i,delta);
        }
        //给delta赋值
        for (int i = 0 ; i<numberOfNeuralNodes ; i++){
            nodes.get(i).setDelta(inputList.get(i));
        }
    }

    @Override
    public void init(int layerNodeNumber, ActivationFunction activationFunction) {
        super.init(layerNodeNumber, activationFunction);
        //将输出层权重都置为1
        ArrayList<Double> outputWeight = new ArrayList<>();
        outputWeight.add(1d);
        for (int i =0;i<numberOfNeuralNodes;i++){
            nodes.get(i).setWights(outputWeight);
        }
    }

    //重写激活方法，若输出层激活方法为SoftMax则用特定方法计算
    @Override
    public void activate() {
        if (nodes.get(0).getActivationFunction().getClass()==SoftMax.class){
            for (int i=0;i<numberOfNeuralNodes;i++){
                nodes.get(i).setOutput(new ArrayList<>());
                nodes.get(i).setActiveValue(nodes.get(i).getActivationFunction().protoFun(this,i));
                nodes.get(i).getOutput().add(nodes.get(i).getActiveValue());
            }
        }else {
            super.activate();
        }
    }
}
