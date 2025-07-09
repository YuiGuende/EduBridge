package config;

import jakarta.websocket.server.ServerContainer;
import jakarta.websocket.DeploymentException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;

@WebListener
public class WebSocketConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServerContainer container
                = (ServerContainer) sce.getServletContext().getAttribute("jakarta.websocket.server.ServerContainer");
        try {
            container.addEndpoint(ws.NotificationSocket.class);
            System.out.println("? WebSocket registered.");
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
