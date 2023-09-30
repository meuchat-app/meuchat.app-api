package app.meuchat.server.controller;


import app.meuchat.server.service.TaskManegerService;
import clojure.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

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
	
	Class<?>[] allClasses = getClasses(targetPackageName);
	MeuChat mc = (MeuChat) createInstance(allClasses[0]);


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

	private static Class<?>[] getClasses(String packageName)  {
		String path = packageName.replace('.', '/');
		try {
			ClassLoader classLoader = Class.forName(baseClassPackageName).getClassLoader();
			java.net.URL resource = classLoader.getResource(path);

			String fullPath = resource.getFile();
			java.io.File directory = new java.io.File(fullPath);
			String classFiles = directory.list()[0];
			Class<?>[] classes = new Class<?>[classFiles.length()];
			
			for (int i = 0; i < classFiles.length(); i++) {
				String className = packageName + '.' + classFiles.substring(0, classFiles.length() - 6);
				classes[i] = Class.forName(className);
			}
			return classes;	
		} catch (ClassNotFoundException e) {
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
