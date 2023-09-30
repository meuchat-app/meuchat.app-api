package app.meuchat.server.controller;




import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.meuchat.server.model.Message;
import app.meuchat.server.model.MeuChat;

import jakarta.servlet.ServletContext;

@Controller
public class ChatController {

	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	static String baseClassPackageName = "app.meuchat.server.model.MeuChat";
	String targetPackageName = "app.meuchat.server.service";
	
	List<Class<?>> allClasses = getMeuChatSubclasses(targetPackageName);
	MeuChat mc = (MeuChat) createInstance(allClasses.get(0));


	@MessageMapping("/private-message")
	private void receivePrivateMessage(@Payload Message message) {
		try {
			mc.receiveMessage(message);
			sendFilteredPrivateMessages(mc.getChat().getResponse(), "/user");
		
		} catch (Exception e) {
			
			e.printStackTrace();
			sendFilteredPrivateMessages(new Message("Ocorreu um erro :(", "author-server"), "/user");
			return;
		}
		
		
	}
	
	public void sendFilteredPrivateMessages(Message message, String destination) {
		simpMessagingTemplate.convertAndSend(destination, message);

		System.out.println(message.toString());
	}

	private static List<Class<?>> getClasses(String packageName) {
		String path = packageName.replace('.', '/');
		List<Class<?>> classes = new ArrayList<Class<?>>();

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			java.net.URL resource = classLoader.getResource(path);
			File directory = new File(resource.getFile());
			if(directory.exists() && directory.isDirectory()){
				for(File file : directory.listFiles()){
					if(file.isFile() && file.getName().endsWith(".class")){
						String className = packageName + "." + file.getName().replace(".class", "");
						Class<?> clazz = Class.forName(className);
						classes.add(clazz);
					}
				}
			}
			
			return classes;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private static List<Class<?>> getMeuChatSubclasses(String packageName)  {
		String baseClassName = "MeuChat";
		try {
			List<Class<?>> allClasses = getClasses(packageName);
			List<Class<?>> classes = new ArrayList<Class<?>>();

			for (Class<?> clazz : allClasses) {
				if (clazz.getSuperclass() != null && clazz.getSuperclass().getSimpleName().equals(baseClassName)) {
					classes.add(clazz);
				}

			};
			
			return classes;		
		
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Object createInstance(Class<?> clazz){
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
