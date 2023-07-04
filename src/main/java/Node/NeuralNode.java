package Node;

import Function.ActivationFunction;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NeuralNode {
    //节点输出权重
    private ArrayList<Double> wights;
    //输入权重和
    private Double inputSum;
    //激活后值
    private Double activeValue;
    //节点输出值
    private ArrayList<Double> output;
    //激活函数
    private ActivationFunction activationFunction;
    //反向传播中得到的增量
    private Double delta;

    //将输入值激活后与权重相乘
    public void activate(){
        activeValue = activationFunction.protoFun(inputSum);
        output = new ArrayList<>();
        for (int i =0 ; i<wights.size();i++){
            output.add(activeValue*wights.get(i));
        }
    }

    //构造器
    public NeuralNode(ArrayList<Double> wights, ActivationFunction activationFunction) {
        this.wights = wights;
        this.activationFunction = activationFunction;
    }

    public NeuralNode(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }
}
