package Layer;

import Function.ActivationFunction;
import Node.NeuralNode;
import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class NeuralLayer {
    //该层节点(神经元)数
    protected int numberOfNeuralNodes;
    //节点的集合
    protected ArrayList<NeuralNode> nodes;
    //上一神经网络层
    protected NeuralLayer preLayer;
    //下一神经网络层
    protected NeuralLayer nextLayer;

    //初始化本层的神经元
    public void init(int layerNodeNumber,ActivationFunction activationFunction){
        nodes = new ArrayList<>();
        numberOfNeuralNodes = layerNodeNumber;
        for (int i = 0;i<layerNodeNumber;i++){
            nodes.add(new NeuralNode(activationFunction));
        }
    }
    //将随机生成的权重分配到该层的各结点中
    public void distributeWeight(ArrayList<ArrayList<Double>> weights){
        for (int i =0;i<numberOfNeuralNodes;i++){
            nodes.get(i).setWights(weights.get(i));
        }
    }
    //获取并分配上一层的输入值
    public void handleInput(ArrayList<ArrayList<Double>> inputList){
        int numberOfPreNodes = preLayer.numberOfNeuralNodes;
        for (int i = 0;i<numberOfNeuralNodes;i++){
            Double input = 0d;
            for (int j = 0;j<numberOfPreNodes;j++){
                input += inputList.get(j).get(i);
            }
            nodes.get(i).setInputSum(input);
        }
    }

    //让该层所有神经元进行激活函数运算
    public void activate(){
        for (int i = 0;i<numberOfNeuralNodes;i++){
            nodes.get(i).activate();
        }
    }

    //集合该层神经元的输出值
    public ArrayList<ArrayList<Double>> getOutput(){
        ArrayList<ArrayList<Double>> outputList = new ArrayList<>();
        for (int i = 0 ; i<numberOfNeuralNodes ; i++){
            outputList.add(nodes.get(i).getOutput());
        }
        return outputList;
    }

    //计算并赋值增量
    public void culDelta(){
        int numberOfNextNodes = nextLayer.numberOfNeuralNodes;
        for (int i = 0;i<numberOfNeuralNodes;i++){
            Double delta = 0d;
            for (int j = 0;j<numberOfNextNodes;j++){
                delta += nodes.get(i).getWights().get(j)*nextLayer.nodes.get(j).getDelta();
            }
            delta *= nodes.get(i).getActivationFunction().derivativeFun(nodes.get(i).getActivationFunction().protoFun(nodes.get(i).getInputSum()));
            nodes.get(i).setDelta(delta);
        }
    }
    //更新该层节点的所有权重
    public void updateWeights(Double stepLength){
        for (int i = 0;i<numberOfNeuralNodes;i++){
            NeuralNode node = nodes.get(i);
            for (int j = 0;j<nextLayer.numberOfNeuralNodes;j++){
                NeuralNode nextNode = nextLayer.getNodes().get(j);
                node.getWights().set(j,stepLength*node.getActiveValue()*nextNode.getDelta() + node.getWights().get(j));
            }
        }
    }


}
