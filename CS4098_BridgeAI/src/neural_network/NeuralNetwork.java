package neural_network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.*;

public class NeuralNetwork {
	final static int DUMMY_INDEX = -1;
	final static double DUMMY_WEIGHT = 0.5d;
	final static double CURVE_P_VALUE = 1.0d;
	private HashMap<Point, Double> weights;
	private ArrayList<Layer> layers;
	private double[] nodes;
	private double[] deltas;
	
	//These are only stored for JSON saving
	int num_inputs, num_outputs, num_hidden_layers, neurons_per_hidden_layer;
	
	public NeuralNetwork(int num_inputs, int num_outputs, int num_hidden_layers, int neurons_per_hidden_layer){
		
		this.num_inputs = num_inputs;
		this.num_outputs = num_outputs;
		this.num_hidden_layers = num_hidden_layers;
		this.neurons_per_hidden_layer = neurons_per_hidden_layer;
		
		setup();
	}
	
	public NeuralNetwork(JSONObject j_obj){
		num_inputs = j_obj.getInt("num_inputs");
		num_outputs = j_obj.getInt("num_outputs");
		num_hidden_layers = j_obj.getInt("num_hidden_layers");
		neurons_per_hidden_layer = j_obj.getInt("neurons_per_hidden_layer");
		
		setup();
		
		JSONArray j_weights = j_obj.getJSONArray("weights");
		for(int i = 0; i < j_weights.length(); i++){
			JSONObject j_weight = j_weights.getJSONObject(i);
			int pointx = j_weight.getInt("pointx");
			int pointy = j_weight.getInt("pointy");
			double weight = j_weight.getDouble("weight");
			
			Point key = new Point(pointx, pointy);
			weights.put(key,  weight);
		}
	}
	
	private void setup(){
		layers = new ArrayList<Layer>();
		weights = new HashMap<Point, Double>();
		
		int num_nodes = 0;
		
		Layer input_layer = new Layer(0, num_inputs);
		layers.add(input_layer);
		num_nodes += num_inputs;
		
		for(int i = 0; i < num_hidden_layers; i++){
			for(int j = 0; j < neurons_per_hidden_layer; j++){
				num_nodes++;
			}
			Layer hidden_layer = new Layer(num_nodes - neurons_per_hidden_layer, num_nodes);
			layers.add(hidden_layer);
		}
		
		Layer output_layer = new Layer(num_nodes, num_nodes + num_outputs);
		layers.add(output_layer);
		num_nodes += num_outputs;
		
		nodes = new double[num_nodes];
		deltas = new double[num_nodes];
		
		for(int i = 0; i < layers.size(); i++){
			addDummyWeights(layers.get(i));
			if (i < layers.size() - 1)
				linkLayers(layers.get(i), layers.get(i + 1));
		}
	}
	
	public void train(double[] inputs, double[] outputs, int test_number, int total_tests){
		//Train neural network using the given inputs and their corresponding correct outputs
		propogateForwards(inputs);
		propogateBackwards(outputs, (double)test_number, (double)total_tests);
	}
	
	public double test(double[] inputs, double[] expected_outputs){
		propogateForwards(inputs);
		
		//Calculate total difference between the expected outputs and those
		//returned by the neural network
		Layer output_layer = layers.get(layers.size() - 1);
		double total_error = 0d;
		for(int i = 0; i < expected_outputs.length; i++){
			int node_index = output_layer.getStart_index() + i;
			double error = Math.abs(nodes[node_index] - expected_outputs[i]);
			total_error += error;
		}
		
		return total_error;
	}
	
	private double[] propogateForwards(double[] inputs){
		Layer input_layer = layers.get(0);
		
		for(int i = input_layer.getStart_index(); i < input_layer.getEnd_index(); i++){
			nodes[i] = inputs[i];
		}
		
		for(int i = 1; i < layers.size(); i++){
			Layer hidden_layer = layers.get(i);
			for(int j = hidden_layer.getStart_index(); j < hidden_layer.getEnd_index(); j++){
				double node_sum_input = getNodeSumInput(j);
				nodes[j] = sigmoidActivation(node_sum_input);
			}
		}
		
		Layer output_layer = layers.get(layers.size() - 1);
		double[] outputs = new double[output_layer.getSize()];
		for(int i = 0; i < outputs.length; i++){
			outputs[i] = nodes[output_layer.getStart_index() + i];
			//System.out.print(outputs[i] + " ");
		}
		//System.out.println();
		return outputs;
	}
	
	private void propogateBackwards(double[] expected_results, double test_number, double total_tests){
		Layer output_layer= layers.get(layers.size() - 1);
		for(int i = 0; i < expected_results.length; i++){
			int node_index = output_layer.getStart_index() + i;
			deltas[node_index] = derivateSigmoidActivation(getNodeSumInput(node_index)) * 
					(expected_results[i] - nodes[node_index]);	
		}
		
		for(int i = layers.size() - 2; i >= 0; i--){
			Layer cur_layer = layers.get(i);
			Layer next_layer = layers.get(i + 1);
			
			for(int j = cur_layer.getStart_index(); j < cur_layer.getEnd_index(); j++){
				
				double sum_weighted_errors = 0d;
				for(int k = next_layer.getStart_index(); k < next_layer.getEnd_index(); k++){
					sum_weighted_errors += weights.get(new Point(j, k)) * deltas[j];
				}
				
				deltas[j] = derivateSigmoidActivation(j) * sum_weighted_errors;
			}
		}
		
		//Update every weight in the network using deltas
		for(int i = 0; i < layers.size() - 1; i++){
			Layer cur_layer = layers.get(i);
			Layer next_layer = layers.get(i + 1);
			
			for(int j = cur_layer.getStart_index(); j < cur_layer.getEnd_index(); j++){
				for(int k = next_layer.getStart_index(); k < next_layer.getEnd_index(); k++){
					
					double alpha_value = total_tests / (total_tests + test_number);
					double old_weight = weights.get(new Point(j, k));
					double new_weight = old_weight + (alpha_value * nodes[j] * deltas[k]);
					
					weights.put(new Point(j, k), new_weight);
				}
			}
		}
		
		//Update dummy weights
		for(int i = 0; i < nodes.length; i++){
			double alpha_value = total_tests / (total_tests + test_number);
			double old_weight = weights.get(new Point(DUMMY_INDEX, i));
			double new_weight = old_weight + (alpha_value * -1d * deltas[i]);
			weights.put(new Point(DUMMY_INDEX, i), new_weight);
		}
	}
	
	private void linkLayers(Layer layer1, Layer layer2){
		//Adds weights between each node in 'layer1' and all nodes in 'layer2' 
		for(int i = layer1.getStart_index(); i < layer1.getEnd_index(); i++){
			for(int j = layer2.getStart_index(); j < layer2.getEnd_index(); j++){
				Point link = new Point(i, j);
				weights.put(link, getRandomWeight());
			}
		}
	}
	
	private void addDummyWeights(Layer layer){
		//Adds a dummy weight to all nodes in this layer
		for(int i = layer.getStart_index(); i < layer.getEnd_index(); i++){
			Point link = new Point(DUMMY_INDEX, i);
			weights.put(link, DUMMY_WEIGHT);
		}
	}
	
	private double getNodeSumInput(int node_index){
		//Calculate the weighted sum of all inputs to this node
		int nli = getNodeLayerIndex(node_index);
		Layer input_layer = layers.get(nli - 1);
		
		double sum_inputs = 0d;
		for(int i = input_layer.getStart_index(); i < input_layer.getEnd_index(); i++){
			sum_inputs += nodes[i] * weights.get(new Point(i, node_index));
		}
		
		//Add the dummy input
		sum_inputs += (-1d * weights.get(new Point(DUMMY_INDEX, node_index)));
		
		return sum_inputs;
	}
	
	private int getNodeLayerIndex(int node_index){
		//Returns the index in 'layers' which holds this node's layer
		for(int i = 0; i < layers.size(); i++){
			if (node_index >= layers.get(i).getStart_index() && node_index < layers.get(i).getEnd_index())
				return i;
		}
		return -1;
	}
	
	private static double derivateSigmoidActivation(double x){
		double sa = sigmoidActivation(x);
		return sa * (1d - sa);
	}
	
	private static double sigmoidActivation(double x){
		return (1d / (1d + Math.pow(Math.E, (-x / CURVE_P_VALUE))));
	}
	
	private static double thresholdActivation(double x){
		if(x >= 0.5) return 1;
		else return 0;
	}
	
	private static double getRandomWeight(){
		//Returns a random double value between 0 and 1
		return Math.random();
	}
	
	public JSONObject getJSONObject(){
		JSONObject j_obj = new JSONObject();
		
		j_obj.put("num_inputs", num_inputs);
		j_obj.put("num_outputs", num_outputs);
		j_obj.put("num_hidden_layers", num_hidden_layers);
		j_obj.put("neurons_per_hidden_layer", neurons_per_hidden_layer);

		//Add all weights and their associated hashmap keys
		Set<Point> keys = weights.keySet();
		Object[] o_points = keys.toArray();
		Point[] points = new Point[o_points.length];
		for(int i = 0; i < o_points.length; i++){
			Point point = (Point)o_points[i];
			points[i] = point;
		}
		
		JSONArray j_weights = new JSONArray();
		for (int i = 0; i < points.length; i++){
			JSONObject j_point = new JSONObject();
			j_point.put("pointx", points[i].x);
			j_point.put("pointy", points[i].y);
			j_point.put("weight", weights.get(points[i]));
			j_weights.put(j_point);
		}
		j_obj.put("weights", j_weights);
		
		return j_obj;
	}
	
}
