import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;





public class kafkaConsumer extends Thread{

	private String topic;
	
	public kafkaConsumer(String topic){
		super();
		this.topic = topic;
	}
	
	
	@Override
	public void run() {
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		 Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);
		 KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
		 ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
		 while(iterator.hasNext()){
			 String message = new String(iterator.next().message());
			 System.out.println("" + message);
		 }
	}

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "172.17.6.197:2181");
		properties.put("group.id", "group1");
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		new kafkaConsumer("topic2").start();
		
	}
	 
}
