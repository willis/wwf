package com.ebizserve.test;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import junit.framework.TestFailure;

public class TestClass {
	
	public static void main(String[] agrv) throws Exception{
		testF();
	}
	
	public static void testF(){

	}
	
	public static void testA() throws IOException, URISyntaxException{
		 final Desktop desktop = Desktop.getDesktop();

	     TrayIcon trayIcon = null;
	     if (SystemTray.isSupported()) {
	         // get the SystemTray instance
	         SystemTray tray = SystemTray.getSystemTray();
	         // load an image
	         java.awt.Image image =  Toolkit.getDefaultToolkit().getImage("E:\\tools\\素材\\gif\\gif-0065.gif");
	         // create a action listener to listen for default action executed on the tray icon
	         java.awt.event.ActionListener listener = new  ActionListener() {
	             public void  actionPerformed(java.awt.event.ActionEvent e) {
	                 // execute default action of the application
	                 // ...
	            	 System.out.println("test");
	        		 if(desktop.isDesktopSupported()){
	        			 try {
							desktop.edit(new File("E:\\tools\\素材\\gif\\gif-0065.gif"));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	        		 }
	             }
	         };
	         /*
	         // create a popup menu
	         java.awt.PopupMenu popup = new  PopupMenu();
	         // create menu item for the default action
	         MenuItem defaultItem = new MenuItem("MenuItem");
	         defaultItem.addActionListener(listener);
	         popup.add(defaultItem);
	         */
	         /// ... add other items
	         // construct a TrayIcon
	         trayIcon = new  TrayIcon(image, "Tray Demo");
	         // set the TrayIcon properties
	         trayIcon. addActionListener(listener);
	         // ...
	         // add the tray image
	         try {
	             tray. add(trayIcon);
	         } catch (AWTException e) {
	             System.err.println(e);
	         }
	         // ...
	     } else {
	         // disable tray option in your application or
	         // perform other actions

	     }
	     // ...
	     // some time later
	     // the application state has changed - update the image
	   /*
	     if (trayIcon != null) {
	    	 java.awt.Image updatedImage =  Toolkit.getDefaultToolkit().getImage("E:\\tools\\素材\\gif\\gif-0009.gif");
	         trayIcon.setImage(updatedImage);
	     }
	     
*/
	     // ...
	}
	
	public static void testB() throws Exception{
		Process p = new ProcessBuilder("ipconfig").start();
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
	
	public static void testC(){
		for (Map.Entry entry: System.getenv().entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
	public static void testD(){
	
		Executor executor = Executors.newFixedThreadPool(10);   
		Runnable task = new Runnable() {   
		    @Override  
		    public void run() {   
		        System.out.println("task over");   
		    }   
		};   
		executor.execute(task);   
		  
		executor = Executors.newScheduledThreadPool(10);   
		ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;   
		scheduler.scheduleAtFixedRate(task, 10, 10, TimeUnit.SECONDS);

		Callable<Integer> func = new Callable<Integer>(){   
			    public Integer call() throws Exception {   
			    	System.out.println("inside callable");   
			        Thread.sleep(1000);   
			        return new Integer(8);   
			    }          
			};         
			FutureTask<Integer> futureTask  = new FutureTask<Integer>(func);   
			Thread newThread = new Thread(futureTask);   
			newThread.start();   
			  
			try {   
			    System.out.println("blocking here");   
			    Integer result = futureTask.get();   
			    System.out.println(result);   
			} catch (InterruptedException ignored) {   
			} catch (ExecutionException ignored) {   
			} 
	}


}
