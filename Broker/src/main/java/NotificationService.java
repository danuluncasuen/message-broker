import org.json.JSONObject;

public class NotificationService {

    private static DataSource dataSource = DataSource.getDataSource();

    public static void processContent(String content, ClientThread socket) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            if (jsonObject.has("subscriptionRequest")) {
                if (dataSource.addSubscriber(socket, (String) jsonObject.get("subscriptionRequest"))) {
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
                ClientThread[] socketList = dataSource.getConnectionsByTopic(receivedSubjectEntity.getTitle());
                if (socketList != null) {
                    for (ClientThread socketLoop : socketList) {
                        if (!socketLoop.getClientSocket().isClosed()) {
                            JSONObject newlyPublished = new JSONObject();
                            newlyPublished.put("message", receivedSubjectEntity.getMessage());
                            socketLoop.getBufferedWriter().write(newlyPublished.toString());
                            socketLoop.getBufferedWriter().newLine();
                            socketLoop.getBufferedWriter().flush();
                        } else {
                            //remove subscriber
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cloud not process the content: " + content + " because " + e.getMessage());
        }
    }

}
