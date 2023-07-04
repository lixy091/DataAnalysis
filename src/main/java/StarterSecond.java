import Function.*;
import Layer.HiddenLayer;
import Layer.InputLayer;
import Layer.NeuralLayer;
import Layer.OutputLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class StarterSecond {
    //五个训练集
    private static final Double[][] sample1 = {{0d,1d,1d,0d,0d},{0d,0d,1d,0d,0d},{0d,0d,1d,0d,0d},{0d,0d,1d,0d,0d},{0d,1d,1d,1d,0d}};
    private static final Double[][] sample2 = {{1d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{0d,1d,1d,1d,0d},{1d,0d,0d,0d,0d},{1d,1d,1d,1d,1d}};
    private static final Double[][] sample3 = {{1d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{0d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{1d,1d,1d,1d,0d}};
    private static final Double[][] sample4 = {{0d,0d,0d,1d,0d},{0d,0d,1d,1d,0d},{0d,1d,0d,1d,0d},{1d,1d,1d,1d,1d},{0d,0d,0d,1d,0d}};
    private static final Double[][] sample5 = {{1d,1d,1d,1d,1d},{1d,0d,0d,0d,0d},{1d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{1d,1d,1d,1d,0d}};

    //五个测试集
    private static final Double[][] test1 = {{0d,0d,1d,1d,0d},{0d,0d,1d,1d,0d},{0d,1d,0d,1d,0d},{0d,0d,0d,1d,0d},{0d,1d,1d,1d,0d}};
    private static final Double[][] test2 = {{1d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{0d,1d,1d,1d,0d},{1d,0d,0d,0d,1d},{1d,1d,1d,1d,1d}};
    private static final Double[][] test3 = {{1d,1d,1d,1d,0d},{0d,0d,0d,0d,1d},{0d,1d,1d,1d,0d},{1d,0d,0d,0d,1d},{1d,1d,1d,1d,0d}};
    private static final Double[][] test4 = {{0d,1d,1d,1d,0d},{0d,1d,0d,0d,0d},{0d,1d,1d,1d,0d},{0d,0d,0d,1d,0d},{0d,1d,1d,1d,0d}};
    private static final Double[][] test5 = {{0d,1d,1d,1d,1d},{0d,1d,0d,0d,0d},{0d,1d,1d,1d,0d},{0d,0d,0d,1d,0d},{1d,1d,1d,1d,0d}};

    //结果集
    private static final Double[][] standardResult = {{1d,0d,0d,0d,0d},{0d,1d,0d,0d,0d},{0d,0d,1d,0d,0d},{0d,0d,0d,1d,0d},{0d,0d,0d,0d,1d}};
    //输入层神经元个数
    private static final int InputLayerNumber = 25;
    //隐含层神经元个数
    private static final int HiddenLayerNumber = 50;
    //输出层神经元个数
    private static final int OutputLayerNumber = 5;
    //激活函数
    private static final ActivationFunction activationFunction = new Sigmoid();
    //损失函数
    private static final LossFunction LOSS_FUNCTION = new CrossEntropy();
    //步长(学习率)
    private static final Double stepLength = 0.1;
    //训练次数
    private static final int cycleTimes = 50000;

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
        outputLayer.init(OutputLayerNumber,new SoftMax());
        inputLayer.distributeWeight(generateWeight(inputLayer));
        hiddenLayer.distributeWeight(generateWeight(hiddenLayer));

    }
    //获取样本值形成的矩阵
    private static LinkedList<LinkedList<Double>> getSampleMatrix(){
        LinkedList<LinkedList<Double>> samples = new LinkedList<>();
        samples.add(getSampleMatrix(sample1));
        samples.add(getSampleMatrix(sample2));
        samples.add(getSampleMatrix(sample3));
        samples.add(getSampleMatrix(sample4));
        samples.add(getSampleMatrix(sample5));
        return samples;
    }
    //获取测试集形成的矩阵
    private static LinkedList<LinkedList<Double>> getTestMatrix(){
        LinkedList<LinkedList<Double>> tests = new LinkedList<>();
        tests.add(getSampleMatrix(test1));
        tests.add(getSampleMatrix(test2));
        tests.add(getSampleMatrix(test3));
        tests.add(getSampleMatrix(test4));
        tests.add(getSampleMatrix(test5));
        return tests;
    }

    //获取样本值形成的矩阵
    private static LinkedList<Double> getSampleMatrix(Double[][] sample){
        LinkedList<Double> matrix = new LinkedList<>();
        for (Double[] doubles : sample) matrix.addAll(Arrays.asList(doubles));
        return matrix;
    }

    //获取结果集矩阵
    private static LinkedList<LinkedList<Double>> getResultMatrix(){
        LinkedList<LinkedList<Double>> result = new LinkedList<>();
        for (Double[] doubles: StarterSecond.standardResult){
            result.add(new LinkedList<>(Arrays.asList(doubles)));
        }
        return result;
    }


    //深度学习算法实现
    private static void deepLearning(InputLayer inputLayer, HiddenLayer hiddenLayer, OutputLayer outputLayer){
        LinkedList<LinkedList<Double>> samples = getSampleMatrix();
        LinkedList<LinkedList<Double>> results = getResultMatrix();
        outputLayer.setLossFunction(LOSS_FUNCTION);
        //深度学习循环开始
        for (int i = 0 ; i<cycleTimes; i++){
            for (int j = 0 ; j<samples.size();j++){
                //正向传播
                LinkedList<Double> resList = forwardPro(samples.get(j),inputLayer,hiddenLayer,outputLayer);
                //反向传播开始
                outputLayer.handleInput(resList,results.get(j));
                //计算隐含层增量
                hiddenLayer.culDelta();
                //更新输入层权重
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


    public static void main(String arg[]){
        //创建输入层，隐含层和输出层
        InputLayer inputLayer = new InputLayer();
        HiddenLayer hiddenLayer = new HiddenLayer();
        OutputLayer outputLayer = new OutputLayer();
        //初始化神经网络
        init(inputLayer,hiddenLayer,outputLayer);
        //深度学习开始
        deepLearning(inputLayer,hiddenLayer,outputLayer);
        //获取测试集形成的矩阵
        LinkedList<LinkedList<Double>> samples = getTestMatrix();
        //输出训练后结果
        for (int i = 0;i<samples.size();i++) {
            LinkedList<Double> resultByLearning = forwardPro(samples.get(i), inputLayer, hiddenLayer, outputLayer);
            System.out.println("第"+(i+1)+"个数字的结果概率向量："+resultByLearning);
        }

    }

}
