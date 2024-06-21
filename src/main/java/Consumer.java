import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    private final static String QUEUE_NAME = "crud_queue";

    public static void receiveMessage() throws Exception {
        ConnectionFactory factory = RabbitMQConfig.getConnectionFactory();
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");

                processMessage(message);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processMessage(String message) {
        String[] parts = message.split(" ");
        String operation = parts[0];
        switch (operation) {
            case "CREATE":
                String value = parts[1];
                int id = CrudService.create(value);
                System.out.println("Created ID: " + id + " with value: " + value);
                break;
            case "READ":
                id = Integer.parseInt(parts[1]);
                value = CrudService.read(id);
                System.out.println("Read ID: " + id + " with value: " + value);
                break;
            case "UPDATE":
                id = Integer.parseInt(parts[1]);
                value = parts[2];
                CrudService.update(id, value);
                System.out.println("Updated ID: " + id + " with value: " + value);
                break;
            case "DELETE":
                id = Integer.parseInt(parts[1]);
                CrudService.delete(id);
                System.out.println("Deleted ID: " + id);
                break;
            default:
                System.out.println("Unknown operation: " + operation);
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        receiveMessage();
    }
}
