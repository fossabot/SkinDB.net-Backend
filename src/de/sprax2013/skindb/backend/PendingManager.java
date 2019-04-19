package de.sprax2013.skindb.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import de.sprax2013.advanced_dev_utils.crypt.HashingUtils;
import de.sprax2013.advanced_dev_utils.mojang.MojangAPI;
import de.sprax2013.advanced_dev_utils.mojang.MojangProfile;
import de.sprax2013.skindb.backend.constructors.Pending;
import de.sprax2013.skindb.backend.constructors.PendingStatus;
import de.sprax2013.skindb.backend.constructors.Skin;
import de.sprax2013.skindb.backend.utils.DatabaseUtils;
import de.sprax2013.skindb.backend.utils.SkinAssetUtils;
import de.sprax2013.skindb.backend.utils.SkinUtils;

public class PendingManager {
	static ExecutorService pool;

	private static boolean checkDB = true;

	public static boolean init() {
		deInit();

		if (DatabaseUtils.canConnect()) {
			pool = Executors.newSingleThreadExecutor();
			pool.execute(new Runnable() {
				@Override
				public void run() {
					while (!pool.isShutdown()) {
						if (shouldCheckDB()) {
							System.out.println("Checking DB...");

							try {
								Pending pending = DatabaseUtils.getNextPending();

								if (pending != null) {
									System.out.println("Processing ID " + pending.getID());

									URL url = null;

									try {
										if (pending.getData().isURL()) {
											url = pending.getData().getAsURL();
										} else if (pending.getData().isUUID()) {
											MojangProfile p = MojangAPI.getProfile(pending.getData().getAsUUID());

											if (p.hasTextureProp() && p.getTextureProp().hasSkinURL()) {
												url = new URL(p.getTextureProp().getSkinURL());
											} else {
												pending.setStatus(PendingStatus.INVALID_DATA);
											}
										} else {
											pending.setStatus(PendingStatus.INVALID_DATA);
										}

										if (url != null) {
											File tmpFile = File.createTempFile("SkinDB-Backend_", ".png");
											FileUtils.copyURLToFile(url, tmpFile);

											BufferedImage img = ImageIO.read(tmpFile);
											tmpFile.delete();

											if (SkinUtils.hasSkinDimensions(img)) {
												BufferedImage newImg = SkinUtils.toCleanSkin(img);

												File newTmpFile = File.createTempFile("SkinDB-Backend_", ".png");
												ImageIO.write(newImg, "PNG", newTmpFile);

												String cleanHash = HashingUtils.getHash(newTmpFile);

												newTmpFile.delete();

												System.out.println("Saving a new Skin for ID " + pending.getID());
												new Skin(url.toString(), cleanHash, SkinUtils.hasOverlay(newImg),
														SkinUtils.hasSteveArms(newImg),
														DatabaseUtils.getDuplicate(cleanHash)).save();

												Skin skin = DatabaseUtils.getSkin(cleanHash);
												if (skin != null) {
													if (skin.isDuplicate()) {
														pending.setStatus(PendingStatus.DUPLICATE);
													} else {
														pending.setStatus(PendingStatus.SUCCESS);
													}

													try {
														SkinAssetUtils.create(skin, img);
													} catch (Throwable th) {
														th.printStackTrace();
													}

													pending.setSkinID(skin.getID());
												} else {
													pending.setStatus(PendingStatus.UNKNOWN_ERROR);
												}
											} else {
												pending.setStatus(PendingStatus.WRONG_DIMENSIONS);
											}
										}
									} catch (Throwable th) {
										th.printStackTrace();

										pending.setSkinID(null);
										pending.setStatus(PendingStatus.UNKNOWN_ERROR);
									}

									System.out.println("Saving changes of PendingID " + pending.getID());
									pending.save();
								} else {
									shouldCheckDB(false);
									System.out.println("Found non pending.. Changed 'checkDB' to 'false'");
								}
							} catch (Throwable th) {
								th.printStackTrace();
							}
						} else {
							// Splitting sleeps to quickly shutdown the thread
							for (int i = 0; i < 120; i++) {
								if (pool.isShutdown()) {
									break;
								}

								try {
									Thread.sleep(500);
								} catch (InterruptedException ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			});

			return true;
		}

		return false;
	}

	public static void deInit() {
		if (pool != null) {
			pool.shutdown();

			try {
				pool.awaitTermination(25, TimeUnit.SECONDS);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			pool.shutdownNow();
			pool = null;
		}
	}

	public static synchronized void shouldCheckDB(boolean shouldCheckDB) {
		checkDB = shouldCheckDB;
	}

	static synchronized boolean shouldCheckDB() {
		return checkDB;
	}
}