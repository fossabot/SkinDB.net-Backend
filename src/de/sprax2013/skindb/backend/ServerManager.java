package de.sprax2013.skindb.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerManager {
	private static final int PORT = 7999;

	static ServerSocket srvSocket;

	static ExecutorService thread, pool;

	public static boolean init() {
		deInit();

		thread = Executors.newSingleThreadExecutor();
		pool = Executors.newCachedThreadPool();

		try {
			srvSocket = new ServerSocket(PORT, 0, InetAddress.getLoopbackAddress());

			thread.execute(new Runnable() {
				@Override
				public void run() {
					while (srvSocket != null && !srvSocket.isClosed() && !thread.isShutdown() && !pool.isShutdown()) {
						try {
							Socket client = srvSocket.accept();

							pool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
										BufferedReader br = new BufferedReader(
												new InputStreamReader(client.getInputStream()));

										String data = br.readLine();

										if (data != null) {
											data = data.trim();

											if (data.equalsIgnoreCase("status")) {
												pw.println("{\"status\":\"Okay\"}");
											} else if (data.equalsIgnoreCase("newPending")) {
												PendingManager.shouldCheckDB(true);

												pw.println("{}");
											} else {
												pw.println("{\"error\":\"Invalid request\"}");
												System.err.println("Received invalid data on Socket: " + data);
											}
										} else {
											System.err.println("Received no data on Socket!");
										}

										br.close();
										pw.close();

										client.close();
									} catch (IOException ex) {
										if (!ex.getMessage().equals("Connection reset")) {
											ex.printStackTrace();
										}
									}
								}
							});
						} catch (IOException ex) {
							if (!ex.getMessage().equals("Socket closed")
									&& !ex.getMessage().equals("Socket is closed")) {
								ex.printStackTrace();
							}
						}
					}
				}
			});

			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public static void deInit() {
		if (srvSocket != null) {
			try {
				srvSocket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			srvSocket = null;
		}

		if (thread != null) {
			thread.shutdownNow();
			thread = null;
		}

		if (pool != null) {
			pool.shutdown();

			try {
				pool.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			pool.shutdownNow();
			pool = null;
		}
	}
}