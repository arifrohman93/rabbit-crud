import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer {
    private final static String QUEUE_NAME = "crud_queue";

    public static void sendMessage(String message) throws Exception {
        ConnectionFactory factory = RabbitMQConfig.getConnectionFactory();
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        sendMessage("CREATE value1");
        sendMessage("READ 1");
        sendMessage("UPDATE 1 newValue1");
        sendMessage("DELETE 1");
    }
}
