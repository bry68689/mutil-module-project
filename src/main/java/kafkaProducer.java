import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;



public class kafkaProducer extends Thread{

	private String topic;
	
	public kafkaProducer(String topic){
		super();
		this.topic = topic;
	}
	
	
	@Override
	public void run() {
		Producer producer = createProducer();
		int i=0;
		while(true){
			producer.send(new KeyedMessage<Integer, String>(topic, "message: " + i));
			System.out.println(" " + i);
			try {
				TimeUnit.SECONDS.sleep(1);
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Producer createProducer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "172.17.6.197:2181");
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "172.17.6.197:9092,172.17.6.197:9093,172.17.6.197:9094");
		return new Producer<Integer, String>(new ProducerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		new kafkaProducer("topic2").start();
		
	}
	 
}
