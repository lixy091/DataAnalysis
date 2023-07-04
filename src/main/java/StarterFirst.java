import Function.ActivationFunction;
import Function.LossFunction;
import Function.MeanSquare;
import Function.Sigmoid;
import Layer.HiddenLayer;
import Layer.InputLayer;
import Layer.NeuralLayer;
import Layer.OutputLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class StarterFirst {
    private static final Double[][] sample= {{0d,0d,1d,0d},{0d,1d,1d,1d},{1d,0d,1d,1d},{1d,1d,1d,0d}};

    //输入层神经元个数
    private static final int InputLayerNumber = 3;
    //隐含层神经元个数
    private static final int HiddenLayerNumber = 4;
    //输出层神经元个数
    private static final int OutputLayerNumber = 1;
    //激活函数
    private static final ActivationFunction activationFunction = new Sigmoid();
    //损失函数
    private static final LossFunction LOSS_FUNCTION = new MeanSquare();
    //步长(学习率)
    private static final Double stepLength = 0.2;
    //训练次数
    private static final int cycleTimes = 100000;

    //随机生成权重
    private static ArrayList<ArrayList<Double>> generateWeight(NeuralLayer layer){
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();
        int numberOfNextNodes;
        if (layer.getNextLayer() == null)
            numberOfNextNodes = layer.getNumberOfNeuralNodes();
        else
            numberOfNextNodes = layer.getNextLayer().getNumberOfNeuralNodes();
        for (int i = 0;i<layer.getNumberOfNeuralNodes();i++){
            ArrayList<Double> weight = new ArrayList<>();
            for (int j =0;j<numberOfNextNodes;j++){
                weight.add(Math.random()*2-1);
            }
            weights.add(weight);
        }
        return weights;
    }

    //初始化整个神经网络
    private static void init(InputLayer inputLayer, HiddenLayer hiddenLayer, OutputLayer outputLayer){
        inputLayer.setNextLayer(hiddenLayer);
        hiddenLayer.setNextLayer(outputLayer);
        outputLayer.setPreLayer(hiddenLayer);
        hiddenLayer.setPreLayer(inputLayer);
        inputLayer.init(InputLayerNumber,activationFunction);
        hiddenLayer.init(HiddenLayerNumber,activationFunction);
        outputLayer.init(OutputLayerNumber,activationFunction);
        inputLayer.distributeWeight(generateWeight(inputLayer));
        hiddenLayer.distributeWeight(generateWeight(hiddenLayer));

    }
    //获取样本值形成的矩阵
    private static LinkedList<LinkedList<Double>> getSampleMatrix(){
        LinkedList<LinkedList<Double>> samples = new LinkedList<>();
        for (Double[] doubles : sample) {
            LinkedList<Double> sam = new LinkedList<>(Arrays.asList(doubles).subList(0, doubles.length - 1));
            samples.add(sam);
        }
        return samples;
    }

    //获取样本标准输出的矩阵
    private static LinkedList<LinkedList<Double>> getResultMatrix(){
        LinkedList<LinkedList<Double>> results = new LinkedList<>();
        for (Double[] doubles : sample) {
            LinkedList<Double> result = new LinkedList<>();
            result.add(doubles[doubles.length - 1]);
            results.add(result);
        }
        return results;
    }

    //深度学习算法实现
    private static void deepLearning(InputLayer inputLayer, HiddenLayer hiddenLayer, OutputLayer outputLayer){
        LinkedList<LinkedList<Double>> samples = getSampleMatrix();
        LinkedList<LinkedList<Double>> results = getResultMatrix();
        outputLayer.setLossFunction(LOSS_FUNCTION);
        //深度学习循环开始
        for (int i = 0 ; i<cycleTimes; i++){
            for (int j = 0 ; j<sample.length;j++){
                //正向传播
                LinkedList<Double> resList = forwardPro(samples.get(j),inputLayer,hiddenLayer,outputLayer);
                //反向传播开始
                outputLayer.handleInput(resList,results.get(j));
                //计算隐含层增量
                hiddenLayer.culDelta();
                //更新输出层权重
                inputLayer.updateWeights(stepLength);
                //更新隐含层权重
                hiddenLayer.updateWeights(stepLength);
            }
        }
    }

    //正向传播算法
    private static LinkedList<Double> forwardPro(LinkedList<Double> input,InputLayer inputLayer, HiddenLayer hiddenLayer, OutputLayer outputLayer){
        //将样本值传入输入层
        inputLayer.handleInput(input);
        //激活神经元
        inputLayer.activate();
        //输入层的值传输到隐含层
        hiddenLayer.handleInput(inputLayer.getOutput());
        //激活神经元
        hiddenLayer.activate();
        //隐含层的值传输到输出层
        outputLayer.handleInput(hiddenLayer.getOutput());
        //激活神经元
        outputLayer.activate();
        //获取正向传播结果集
        LinkedList<Double> resList = new LinkedList<>();
        for (ArrayList<Double> res : outputLayer.getOutput()){
            resList.add(res.get(0));
        }
        return resList;
    }


    public static void main(String args[]){
        //创建输入层，隐含层和输出层
        InputLayer inputLayer = new InputLayer();
        HiddenLayer hiddenLayer = new HiddenLayer();
        OutputLayer outputLayer = new OutputLayer();
        //初始化神经网络
        init(inputLayer,hiddenLayer,outputLayer);
        //深度学习开始
        deepLearning(inputLayer,hiddenLayer,outputLayer);
        //获取样本值形成的矩阵
        LinkedList<LinkedList<Double>> samples = getSampleMatrix();
        //输出训练后结果
        for (int i =0;i<samples.size();i++){
            LinkedList<Double> resultByLearning = forwardPro(samples.get(i),inputLayer,hiddenLayer,outputLayer);
            System.out.println("The result of the test set "+(i+1)+":");
            System.out.println(resultByLearning);
        }


    }
}
