import org.json.JSONObject;

import java.net.SocketException;

public class NotificationService {

    private static final DataSource dataSource = DataSource.getDataSource();

    public static void processContent(String content, ClientThread socket) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            if (jsonObject.has("subscriptionRequest")) {
                if (dataSource.addSubscriber(socket, (String) jsonObject.get("subscriptionRequest"))) {
                    System.out.println("A new subscriber has been added");
                    SubjectEntity messages = dataSource.getByTopic((String) jsonObject.get("subscriptionRequest"));
                    JSONObject response = new JSONObject();
                    response.put("topic", messages.getTitle());
                    response.put("response", messages.getMessages());
                    socket.getBufferedWriter().write(response.toString());
                } else {
                    socket.getBufferedWriter().write("Sorry, we don't have such topics");
                }
                socket.getBufferedWriter().newLine();
                socket.getBufferedWriter().flush();
            } else {
                ReceivedSubjectEntity receivedSubjectEntity = new ReceivedSubjectEntity();
                receivedSubjectEntity.setTitle((String) jsonObject.get("topic"));
                receivedSubjectEntity.setMessage((String) jsonObject.get("message"));
                dataSource.addSubject(receivedSubjectEntity);
                System.out.println("New message added on " + jsonObject.get("topic"));
                ClientThread[] socketList = dataSource.getConnectionsByTopic(receivedSubjectEntity.getTitle());
                if (socketList != null) {
                    for (ClientThread socketLoop : socketList) {
                        try {
                            JSONObject newlyPublished = new JSONObject();
                            newlyPublished.put("message", receivedSubjectEntity.getMessage());
                            socketLoop.getBufferedWriter().write(newlyPublished.toString());
                            socketLoop.getBufferedWriter().newLine();
                            socketLoop.getBufferedWriter().flush();
                            System.out.println("The news have been sent");
                        } catch (SocketException se) {
                            System.out.println("Removing the subscriber");
                            dataSource.removeConnection(jsonObject.get("topic").toString(), socketLoop);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cloud not process the content: " + content + " because " + e.getMessage());
        }
    }

}
