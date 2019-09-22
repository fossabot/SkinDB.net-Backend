package de.sprax2013.skindb.backend.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// TODO Add Support for notch's skin

public class SkinUtils {
	/** X and Y coordinates that are used for the 3px-Skin */
	public static final Point[] SKIN_3PX = { new Point(0, 8), new Point(0, 9), new Point(0, 10), new Point(0, 11),
			new Point(0, 12), new Point(0, 13), new Point(0, 14), new Point(0, 15), new Point(0, 20), new Point(0, 21),
			new Point(0, 22), new Point(0, 23), new Point(0, 24), new Point(0, 25), new Point(0, 26), new Point(0, 27),
			new Point(0, 28), new Point(0, 29), new Point(0, 30), new Point(0, 31), new Point(1, 8), new Point(1, 9),
			new Point(1, 10), new Point(1, 11), new Point(1, 12), new Point(1, 13), new Point(1, 14), new Point(1, 15),
			new Point(1, 20), new Point(1, 21), new Point(1, 22), new Point(1, 23), new Point(1, 24), new Point(1, 25),
			new Point(1, 26), new Point(1, 27), new Point(1, 28), new Point(1, 29), new Point(1, 30), new Point(1, 31),
			new Point(2, 8), new Point(2, 9), new Point(2, 10), new Point(2, 11), new Point(2, 12), new Point(2, 13),
			new Point(2, 14), new Point(2, 15), new Point(2, 20), new Point(2, 21), new Point(2, 22), new Point(2, 23),
			new Point(2, 24), new Point(2, 25), new Point(2, 26), new Point(2, 27), new Point(2, 28), new Point(2, 29),
			new Point(2, 30), new Point(2, 31), new Point(3, 8), new Point(3, 9), new Point(3, 10), new Point(3, 11),
			new Point(3, 12), new Point(3, 13), new Point(3, 14), new Point(3, 15), new Point(3, 20), new Point(3, 21),
			new Point(3, 22), new Point(3, 23), new Point(3, 24), new Point(3, 25), new Point(3, 26), new Point(3, 27),
			new Point(3, 28), new Point(3, 29), new Point(3, 30), new Point(3, 31), new Point(4, 8), new Point(4, 9),
			new Point(4, 10), new Point(4, 11), new Point(4, 12), new Point(4, 13), new Point(4, 14), new Point(4, 15),
			new Point(4, 16), new Point(4, 17), new Point(4, 18), new Point(4, 19), new Point(4, 20), new Point(4, 21),
			new Point(4, 22), new Point(4, 23), new Point(4, 24), new Point(4, 25), new Point(4, 26), new Point(4, 27),
			new Point(4, 28), new Point(4, 29), new Point(4, 30), new Point(4, 31), new Point(5, 8), new Point(5, 9),
			new Point(5, 10), new Point(5, 11), new Point(5, 12), new Point(5, 13), new Point(5, 14), new Point(5, 15),
			new Point(5, 16), new Point(5, 17), new Point(5, 18), new Point(5, 19), new Point(5, 20), new Point(5, 21),
			new Point(5, 22), new Point(5, 23), new Point(5, 24), new Point(5, 25), new Point(5, 26), new Point(5, 27),
			new Point(5, 28), new Point(5, 29), new Point(5, 30), new Point(5, 31), new Point(6, 8), new Point(6, 9),
			new Point(6, 10), new Point(6, 11), new Point(6, 12), new Point(6, 13), new Point(6, 14), new Point(6, 15),
			new Point(6, 16), new Point(6, 17), new Point(6, 18), new Point(6, 19), new Point(6, 20), new Point(6, 21),
			new Point(6, 22), new Point(6, 23), new Point(6, 24), new Point(6, 25), new Point(6, 26), new Point(6, 27),
			new Point(6, 28), new Point(6, 29), new Point(6, 30), new Point(6, 31), new Point(7, 8), new Point(7, 9),
			new Point(7, 10), new Point(7, 11), new Point(7, 12), new Point(7, 13), new Point(7, 14), new Point(7, 15),
			new Point(7, 16), new Point(7, 17), new Point(7, 18), new Point(7, 19), new Point(7, 20), new Point(7, 21),
			new Point(7, 22), new Point(7, 23), new Point(7, 24), new Point(7, 25), new Point(7, 26), new Point(7, 27),
			new Point(7, 28), new Point(7, 29), new Point(7, 30), new Point(7, 31), new Point(8, 0), new Point(8, 1),
			new Point(8, 2), new Point(8, 3), new Point(8, 4), new Point(8, 5), new Point(8, 6), new Point(8, 7),
			new Point(8, 8), new Point(8, 9), new Point(8, 10), new Point(8, 11), new Point(8, 12), new Point(8, 13),
			new Point(8, 14), new Point(8, 15), new Point(8, 16), new Point(8, 17), new Point(8, 18), new Point(8, 19),
			new Point(8, 20), new Point(8, 21), new Point(8, 22), new Point(8, 23), new Point(8, 24), new Point(8, 25),
			new Point(8, 26), new Point(8, 27), new Point(8, 28), new Point(8, 29), new Point(8, 30), new Point(8, 31),
			new Point(9, 0), new Point(9, 1), new Point(9, 2), new Point(9, 3), new Point(9, 4), new Point(9, 5),
			new Point(9, 6), new Point(9, 7), new Point(9, 8), new Point(9, 9), new Point(9, 10), new Point(9, 11),
			new Point(9, 12), new Point(9, 13), new Point(9, 14), new Point(9, 15), new Point(9, 16), new Point(9, 17),
			new Point(9, 18), new Point(9, 19), new Point(9, 20), new Point(9, 21), new Point(9, 22), new Point(9, 23),
			new Point(9, 24), new Point(9, 25), new Point(9, 26), new Point(9, 27), new Point(9, 28), new Point(9, 29),
			new Point(9, 30), new Point(9, 31), new Point(10, 0), new Point(10, 1), new Point(10, 2), new Point(10, 3),
			new Point(10, 4), new Point(10, 5), new Point(10, 6), new Point(10, 7), new Point(10, 8), new Point(10, 9),
			new Point(10, 10), new Point(10, 11), new Point(10, 12), new Point(10, 13), new Point(10, 14),
			new Point(10, 15), new Point(10, 16), new Point(10, 17), new Point(10, 18), new Point(10, 19),
			new Point(10, 20), new Point(10, 21), new Point(10, 22), new Point(10, 23), new Point(10, 24),
			new Point(10, 25), new Point(10, 26), new Point(10, 27), new Point(10, 28), new Point(10, 29),
			new Point(10, 30), new Point(10, 31), new Point(11, 0), new Point(11, 1), new Point(11, 2),
			new Point(11, 3), new Point(11, 4), new Point(11, 5), new Point(11, 6), new Point(11, 7), new Point(11, 8),
			new Point(11, 9), new Point(11, 10), new Point(11, 11), new Point(11, 12), new Point(11, 13),
			new Point(11, 14), new Point(11, 15), new Point(11, 16), new Point(11, 17), new Point(11, 18),
			new Point(11, 19), new Point(11, 20), new Point(11, 21), new Point(11, 22), new Point(11, 23),
			new Point(11, 24), new Point(11, 25), new Point(11, 26), new Point(11, 27), new Point(11, 28),
			new Point(11, 29), new Point(11, 30), new Point(11, 31), new Point(12, 0), new Point(12, 1),
			new Point(12, 2), new Point(12, 3), new Point(12, 4), new Point(12, 5), new Point(12, 6), new Point(12, 7),
			new Point(12, 8), new Point(12, 9), new Point(12, 10), new Point(12, 11), new Point(12, 12),
			new Point(12, 13), new Point(12, 14), new Point(12, 15), new Point(12, 20), new Point(12, 21),
			new Point(12, 22), new Point(12, 23), new Point(12, 24), new Point(12, 25), new Point(12, 26),
			new Point(12, 27), new Point(12, 28), new Point(12, 29), new Point(12, 30), new Point(12, 31),
			new Point(13, 0), new Point(13, 1), new Point(13, 2), new Point(13, 3), new Point(13, 4), new Point(13, 5),
			new Point(13, 6), new Point(13, 7), new Point(13, 8), new Point(13, 9), new Point(13, 10),
			new Point(13, 11), new Point(13, 12), new Point(13, 13), new Point(13, 14), new Point(13, 15),
			new Point(13, 20), new Point(13, 21), new Point(13, 22), new Point(13, 23), new Point(13, 24),
			new Point(13, 25), new Point(13, 26), new Point(13, 27), new Point(13, 28), new Point(13, 29),
			new Point(13, 30), new Point(13, 31), new Point(14, 0), new Point(14, 1), new Point(14, 2),
			new Point(14, 3), new Point(14, 4), new Point(14, 5), new Point(14, 6), new Point(14, 7), new Point(14, 8),
			new Point(14, 9), new Point(14, 10), new Point(14, 11), new Point(14, 12), new Point(14, 13),
			new Point(14, 14), new Point(14, 15), new Point(14, 20), new Point(14, 21), new Point(14, 22),
			new Point(14, 23), new Point(14, 24), new Point(14, 25), new Point(14, 26), new Point(14, 27),
			new Point(14, 28), new Point(14, 29), new Point(14, 30), new Point(14, 31), new Point(15, 0),
			new Point(15, 1), new Point(15, 2), new Point(15, 3), new Point(15, 4), new Point(15, 5), new Point(15, 6),
			new Point(15, 7), new Point(15, 8), new Point(15, 9), new Point(15, 10), new Point(15, 11),
			new Point(15, 12), new Point(15, 13), new Point(15, 14), new Point(15, 15), new Point(15, 20),
			new Point(15, 21), new Point(15, 22), new Point(15, 23), new Point(15, 24), new Point(15, 25),
			new Point(15, 26), new Point(15, 27), new Point(15, 28), new Point(15, 29), new Point(15, 30),
			new Point(15, 31), new Point(16, 0), new Point(16, 1), new Point(16, 2), new Point(16, 3), new Point(16, 4),
			new Point(16, 5), new Point(16, 6), new Point(16, 7), new Point(16, 8), new Point(16, 9), new Point(16, 10),
			new Point(16, 11), new Point(16, 12), new Point(16, 13), new Point(16, 14), new Point(16, 15),
			new Point(16, 20), new Point(16, 21), new Point(16, 22), new Point(16, 23), new Point(16, 24),
			new Point(16, 25), new Point(16, 26), new Point(16, 27), new Point(16, 28), new Point(16, 29),
			new Point(16, 30), new Point(16, 31), new Point(16, 52), new Point(16, 53), new Point(16, 54),
			new Point(16, 55), new Point(16, 56), new Point(16, 57), new Point(16, 58), new Point(16, 59),
			new Point(16, 60), new Point(16, 61), new Point(16, 62), new Point(16, 63), new Point(17, 0),
			new Point(17, 1), new Point(17, 2), new Point(17, 3), new Point(17, 4), new Point(17, 5), new Point(17, 6),
			new Point(17, 7), new Point(17, 8), new Point(17, 9), new Point(17, 10), new Point(17, 11),
			new Point(17, 12), new Point(17, 13), new Point(17, 14), new Point(17, 15), new Point(17, 20),
			new Point(17, 21), new Point(17, 22), new Point(17, 23), new Point(17, 24), new Point(17, 25),
			new Point(17, 26), new Point(17, 27), new Point(17, 28), new Point(17, 29), new Point(17, 30),
			new Point(17, 31), new Point(17, 52), new Point(17, 53), new Point(17, 54), new Point(17, 55),
			new Point(17, 56), new Point(17, 57), new Point(17, 58), new Point(17, 59), new Point(17, 60),
			new Point(17, 61), new Point(17, 62), new Point(17, 63), new Point(18, 0), new Point(18, 1),
			new Point(18, 2), new Point(18, 3), new Point(18, 4), new Point(18, 5), new Point(18, 6), new Point(18, 7),
			new Point(18, 8), new Point(18, 9), new Point(18, 10), new Point(18, 11), new Point(18, 12),
			new Point(18, 13), new Point(18, 14), new Point(18, 15), new Point(18, 20), new Point(18, 21),
			new Point(18, 22), new Point(18, 23), new Point(18, 24), new Point(18, 25), new Point(18, 26),
			new Point(18, 27), new Point(18, 28), new Point(18, 29), new Point(18, 30), new Point(18, 31),
			new Point(18, 52), new Point(18, 53), new Point(18, 54), new Point(18, 55), new Point(18, 56),
			new Point(18, 57), new Point(18, 58), new Point(18, 59), new Point(18, 60), new Point(18, 61),
			new Point(18, 62), new Point(18, 63), new Point(19, 0), new Point(19, 1), new Point(19, 2),
			new Point(19, 3), new Point(19, 4), new Point(19, 5), new Point(19, 6), new Point(19, 7), new Point(19, 8),
			new Point(19, 9), new Point(19, 10), new Point(19, 11), new Point(19, 12), new Point(19, 13),
			new Point(19, 14), new Point(19, 15), new Point(19, 20), new Point(19, 21), new Point(19, 22),
			new Point(19, 23), new Point(19, 24), new Point(19, 25), new Point(19, 26), new Point(19, 27),
			new Point(19, 28), new Point(19, 29), new Point(19, 30), new Point(19, 31), new Point(19, 52),
			new Point(19, 53), new Point(19, 54), new Point(19, 55), new Point(19, 56), new Point(19, 57),
			new Point(19, 58), new Point(19, 59), new Point(19, 60), new Point(19, 61), new Point(19, 62),
			new Point(19, 63), new Point(20, 0), new Point(20, 1), new Point(20, 2), new Point(20, 3), new Point(20, 4),
			new Point(20, 5), new Point(20, 6), new Point(20, 7), new Point(20, 8), new Point(20, 9), new Point(20, 10),
			new Point(20, 11), new Point(20, 12), new Point(20, 13), new Point(20, 14), new Point(20, 15),
			new Point(20, 16), new Point(20, 17), new Point(20, 18), new Point(20, 19), new Point(20, 20),
			new Point(20, 21), new Point(20, 22), new Point(20, 23), new Point(20, 24), new Point(20, 25),
			new Point(20, 26), new Point(20, 27), new Point(20, 28), new Point(20, 29), new Point(20, 30),
			new Point(20, 31), new Point(20, 48), new Point(20, 49), new Point(20, 50), new Point(20, 51),
			new Point(20, 52), new Point(20, 53), new Point(20, 54), new Point(20, 55), new Point(20, 56),
			new Point(20, 57), new Point(20, 58), new Point(20, 59), new Point(20, 60), new Point(20, 61),
			new Point(20, 62), new Point(20, 63), new Point(21, 0), new Point(21, 1), new Point(21, 2),
			new Point(21, 3), new Point(21, 4), new Point(21, 5), new Point(21, 6), new Point(21, 7), new Point(21, 8),
			new Point(21, 9), new Point(21, 10), new Point(21, 11), new Point(21, 12), new Point(21, 13),
			new Point(21, 14), new Point(21, 15), new Point(21, 16), new Point(21, 17), new Point(21, 18),
			new Point(21, 19), new Point(21, 20), new Point(21, 21), new Point(21, 22), new Point(21, 23),
			new Point(21, 24), new Point(21, 25), new Point(21, 26), new Point(21, 27), new Point(21, 28),
			new Point(21, 29), new Point(21, 30), new Point(21, 31), new Point(21, 48), new Point(21, 49),
			new Point(21, 50), new Point(21, 51), new Point(21, 52), new Point(21, 53), new Point(21, 54),
			new Point(21, 55), new Point(21, 56), new Point(21, 57), new Point(21, 58), new Point(21, 59),
			new Point(21, 60), new Point(21, 61), new Point(21, 62), new Point(21, 63), new Point(22, 0),
			new Point(22, 1), new Point(22, 2), new Point(22, 3), new Point(22, 4), new Point(22, 5), new Point(22, 6),
			new Point(22, 7), new Point(22, 8), new Point(22, 9), new Point(22, 10), new Point(22, 11),
			new Point(22, 12), new Point(22, 13), new Point(22, 14), new Point(22, 15), new Point(22, 16),
			new Point(22, 17), new Point(22, 18), new Point(22, 19), new Point(22, 20), new Point(22, 21),
			new Point(22, 22), new Point(22, 23), new Point(22, 24), new Point(22, 25), new Point(22, 26),
			new Point(22, 27), new Point(22, 28), new Point(22, 29), new Point(22, 30), new Point(22, 31),
			new Point(22, 48), new Point(22, 49), new Point(22, 50), new Point(22, 51), new Point(22, 52),
			new Point(22, 53), new Point(22, 54), new Point(22, 55), new Point(22, 56), new Point(22, 57),
			new Point(22, 58), new Point(22, 59), new Point(22, 60), new Point(22, 61), new Point(22, 62),
			new Point(22, 63), new Point(23, 0), new Point(23, 1), new Point(23, 2), new Point(23, 3), new Point(23, 4),
			new Point(23, 5), new Point(23, 6), new Point(23, 7), new Point(23, 8), new Point(23, 9), new Point(23, 10),
			new Point(23, 11), new Point(23, 12), new Point(23, 13), new Point(23, 14), new Point(23, 15),
			new Point(23, 16), new Point(23, 17), new Point(23, 18), new Point(23, 19), new Point(23, 20),
			new Point(23, 21), new Point(23, 22), new Point(23, 23), new Point(23, 24), new Point(23, 25),
			new Point(23, 26), new Point(23, 27), new Point(23, 28), new Point(23, 29), new Point(23, 30),
			new Point(23, 31), new Point(23, 48), new Point(23, 49), new Point(23, 50), new Point(23, 51),
			new Point(23, 52), new Point(23, 53), new Point(23, 54), new Point(23, 55), new Point(23, 56),
			new Point(23, 57), new Point(23, 58), new Point(23, 59), new Point(23, 60), new Point(23, 61),
			new Point(23, 62), new Point(23, 63), new Point(24, 8), new Point(24, 9), new Point(24, 10),
			new Point(24, 11), new Point(24, 12), new Point(24, 13), new Point(24, 14), new Point(24, 15),
			new Point(24, 16), new Point(24, 17), new Point(24, 18), new Point(24, 19), new Point(24, 20),
			new Point(24, 21), new Point(24, 22), new Point(24, 23), new Point(24, 24), new Point(24, 25),
			new Point(24, 26), new Point(24, 27), new Point(24, 28), new Point(24, 29), new Point(24, 30),
			new Point(24, 31), new Point(24, 48), new Point(24, 49), new Point(24, 50), new Point(24, 51),
			new Point(24, 52), new Point(24, 53), new Point(24, 54), new Point(24, 55), new Point(24, 56),
			new Point(24, 57), new Point(24, 58), new Point(24, 59), new Point(24, 60), new Point(24, 61),
			new Point(24, 62), new Point(24, 63), new Point(25, 8), new Point(25, 9), new Point(25, 10),
			new Point(25, 11), new Point(25, 12), new Point(25, 13), new Point(25, 14), new Point(25, 15),
			new Point(25, 16), new Point(25, 17), new Point(25, 18), new Point(25, 19), new Point(25, 20),
			new Point(25, 21), new Point(25, 22), new Point(25, 23), new Point(25, 24), new Point(25, 25),
			new Point(25, 26), new Point(25, 27), new Point(25, 28), new Point(25, 29), new Point(25, 30),
			new Point(25, 31), new Point(25, 48), new Point(25, 49), new Point(25, 50), new Point(25, 51),
			new Point(25, 52), new Point(25, 53), new Point(25, 54), new Point(25, 55), new Point(25, 56),
			new Point(25, 57), new Point(25, 58), new Point(25, 59), new Point(25, 60), new Point(25, 61),
			new Point(25, 62), new Point(25, 63), new Point(26, 8), new Point(26, 9), new Point(26, 10),
			new Point(26, 11), new Point(26, 12), new Point(26, 13), new Point(26, 14), new Point(26, 15),
			new Point(26, 16), new Point(26, 17), new Point(26, 18), new Point(26, 19), new Point(26, 20),
			new Point(26, 21), new Point(26, 22), new Point(26, 23), new Point(26, 24), new Point(26, 25),
			new Point(26, 26), new Point(26, 27), new Point(26, 28), new Point(26, 29), new Point(26, 30),
			new Point(26, 31), new Point(26, 48), new Point(26, 49), new Point(26, 50), new Point(26, 51),
			new Point(26, 52), new Point(26, 53), new Point(26, 54), new Point(26, 55), new Point(26, 56),
			new Point(26, 57), new Point(26, 58), new Point(26, 59), new Point(26, 60), new Point(26, 61),
			new Point(26, 62), new Point(26, 63), new Point(27, 8), new Point(27, 9), new Point(27, 10),
			new Point(27, 11), new Point(27, 12), new Point(27, 13), new Point(27, 14), new Point(27, 15),
			new Point(27, 16), new Point(27, 17), new Point(27, 18), new Point(27, 19), new Point(27, 20),
			new Point(27, 21), new Point(27, 22), new Point(27, 23), new Point(27, 24), new Point(27, 25),
			new Point(27, 26), new Point(27, 27), new Point(27, 28), new Point(27, 29), new Point(27, 30),
			new Point(27, 31), new Point(27, 48), new Point(27, 49), new Point(27, 50), new Point(27, 51),
			new Point(27, 52), new Point(27, 53), new Point(27, 54), new Point(27, 55), new Point(27, 56),
			new Point(27, 57), new Point(27, 58), new Point(27, 59), new Point(27, 60), new Point(27, 61),
			new Point(27, 62), new Point(27, 63), new Point(28, 8), new Point(28, 9), new Point(28, 10),
			new Point(28, 11), new Point(28, 12), new Point(28, 13), new Point(28, 14), new Point(28, 15),
			new Point(28, 16), new Point(28, 17), new Point(28, 18), new Point(28, 19), new Point(28, 20),
			new Point(28, 21), new Point(28, 22), new Point(28, 23), new Point(28, 24), new Point(28, 25),
			new Point(28, 26), new Point(28, 27), new Point(28, 28), new Point(28, 29), new Point(28, 30),
			new Point(28, 31), new Point(28, 52), new Point(28, 53), new Point(28, 54), new Point(28, 55),
			new Point(28, 56), new Point(28, 57), new Point(28, 58), new Point(28, 59), new Point(28, 60),
			new Point(28, 61), new Point(28, 62), new Point(28, 63), new Point(29, 8), new Point(29, 9),
			new Point(29, 10), new Point(29, 11), new Point(29, 12), new Point(29, 13), new Point(29, 14),
			new Point(29, 15), new Point(29, 16), new Point(29, 17), new Point(29, 18), new Point(29, 19),
			new Point(29, 20), new Point(29, 21), new Point(29, 22), new Point(29, 23), new Point(29, 24),
			new Point(29, 25), new Point(29, 26), new Point(29, 27), new Point(29, 28), new Point(29, 29),
			new Point(29, 30), new Point(29, 31), new Point(29, 52), new Point(29, 53), new Point(29, 54),
			new Point(29, 55), new Point(29, 56), new Point(29, 57), new Point(29, 58), new Point(29, 59),
			new Point(29, 60), new Point(29, 61), new Point(29, 62), new Point(29, 63), new Point(30, 8),
			new Point(30, 9), new Point(30, 10), new Point(30, 11), new Point(30, 12), new Point(30, 13),
			new Point(30, 14), new Point(30, 15), new Point(30, 16), new Point(30, 17), new Point(30, 18),
			new Point(30, 19), new Point(30, 20), new Point(30, 21), new Point(30, 22), new Point(30, 23),
			new Point(30, 24), new Point(30, 25), new Point(30, 26), new Point(30, 27), new Point(30, 28),
			new Point(30, 29), new Point(30, 30), new Point(30, 31), new Point(30, 52), new Point(30, 53),
			new Point(30, 54), new Point(30, 55), new Point(30, 56), new Point(30, 57), new Point(30, 58),
			new Point(30, 59), new Point(30, 60), new Point(30, 61), new Point(30, 62), new Point(30, 63),
			new Point(31, 8), new Point(31, 9), new Point(31, 10), new Point(31, 11), new Point(31, 12),
			new Point(31, 13), new Point(31, 14), new Point(31, 15), new Point(31, 16), new Point(31, 17),
			new Point(31, 18), new Point(31, 19), new Point(31, 20), new Point(31, 21), new Point(31, 22),
			new Point(31, 23), new Point(31, 24), new Point(31, 25), new Point(31, 26), new Point(31, 27),
			new Point(31, 28), new Point(31, 29), new Point(31, 30), new Point(31, 31), new Point(31, 52),
			new Point(31, 53), new Point(31, 54), new Point(31, 55), new Point(31, 56), new Point(31, 57),
			new Point(31, 58), new Point(31, 59), new Point(31, 60), new Point(31, 61), new Point(31, 62),
			new Point(31, 63), new Point(32, 16), new Point(32, 17), new Point(32, 18), new Point(32, 19),
			new Point(32, 20), new Point(32, 21), new Point(32, 22), new Point(32, 23), new Point(32, 24),
			new Point(32, 25), new Point(32, 26), new Point(32, 27), new Point(32, 28), new Point(32, 29),
			new Point(32, 30), new Point(32, 31), new Point(32, 52), new Point(32, 53), new Point(32, 54),
			new Point(32, 55), new Point(32, 56), new Point(32, 57), new Point(32, 58), new Point(32, 59),
			new Point(32, 60), new Point(32, 61), new Point(32, 62), new Point(32, 63), new Point(33, 16),
			new Point(33, 17), new Point(33, 18), new Point(33, 19), new Point(33, 20), new Point(33, 21),
			new Point(33, 22), new Point(33, 23), new Point(33, 24), new Point(33, 25), new Point(33, 26),
			new Point(33, 27), new Point(33, 28), new Point(33, 29), new Point(33, 30), new Point(33, 31),
			new Point(33, 52), new Point(33, 53), new Point(33, 54), new Point(33, 55), new Point(33, 56),
			new Point(33, 57), new Point(33, 58), new Point(33, 59), new Point(33, 60), new Point(33, 61),
			new Point(33, 62), new Point(33, 63), new Point(34, 16), new Point(34, 17), new Point(34, 18),
			new Point(34, 19), new Point(34, 20), new Point(34, 21), new Point(34, 22), new Point(34, 23),
			new Point(34, 24), new Point(34, 25), new Point(34, 26), new Point(34, 27), new Point(34, 28),
			new Point(34, 29), new Point(34, 30), new Point(34, 31), new Point(34, 52), new Point(34, 53),
			new Point(34, 54), new Point(34, 55), new Point(34, 56), new Point(34, 57), new Point(34, 58),
			new Point(34, 59), new Point(34, 60), new Point(34, 61), new Point(34, 62), new Point(34, 63),
			new Point(35, 16), new Point(35, 17), new Point(35, 18), new Point(35, 19), new Point(35, 20),
			new Point(35, 21), new Point(35, 22), new Point(35, 23), new Point(35, 24), new Point(35, 25),
			new Point(35, 26), new Point(35, 27), new Point(35, 28), new Point(35, 29), new Point(35, 30),
			new Point(35, 31), new Point(35, 52), new Point(35, 53), new Point(35, 54), new Point(35, 55),
			new Point(35, 56), new Point(35, 57), new Point(35, 58), new Point(35, 59), new Point(35, 60),
			new Point(35, 61), new Point(35, 62), new Point(35, 63), new Point(36, 20), new Point(36, 21),
			new Point(36, 22), new Point(36, 23), new Point(36, 24), new Point(36, 25), new Point(36, 26),
			new Point(36, 27), new Point(36, 28), new Point(36, 29), new Point(36, 30), new Point(36, 31),
			new Point(36, 48), new Point(36, 49), new Point(36, 50), new Point(36, 51), new Point(36, 52),
			new Point(36, 53), new Point(36, 54), new Point(36, 55), new Point(36, 56), new Point(36, 57),
			new Point(36, 58), new Point(36, 59), new Point(36, 60), new Point(36, 61), new Point(36, 62),
			new Point(36, 63), new Point(37, 20), new Point(37, 21), new Point(37, 22), new Point(37, 23),
			new Point(37, 24), new Point(37, 25), new Point(37, 26), new Point(37, 27), new Point(37, 28),
			new Point(37, 29), new Point(37, 30), new Point(37, 31), new Point(37, 48), new Point(37, 49),
			new Point(37, 50), new Point(37, 51), new Point(37, 52), new Point(37, 53), new Point(37, 54),
			new Point(37, 55), new Point(37, 56), new Point(37, 57), new Point(37, 58), new Point(37, 59),
			new Point(37, 60), new Point(37, 61), new Point(37, 62), new Point(37, 63), new Point(38, 20),
			new Point(38, 21), new Point(38, 22), new Point(38, 23), new Point(38, 24), new Point(38, 25),
			new Point(38, 26), new Point(38, 27), new Point(38, 28), new Point(38, 29), new Point(38, 30),
			new Point(38, 31), new Point(38, 48), new Point(38, 49), new Point(38, 50), new Point(38, 51),
			new Point(38, 52), new Point(38, 53), new Point(38, 54), new Point(38, 55), new Point(38, 56),
			new Point(38, 57), new Point(38, 58), new Point(38, 59), new Point(38, 60), new Point(38, 61),
			new Point(38, 62), new Point(38, 63), new Point(39, 20), new Point(39, 21), new Point(39, 22),
			new Point(39, 23), new Point(39, 24), new Point(39, 25), new Point(39, 26), new Point(39, 27),
			new Point(39, 28), new Point(39, 29), new Point(39, 30), new Point(39, 31), new Point(39, 48),
			new Point(39, 49), new Point(39, 50), new Point(39, 51), new Point(39, 52), new Point(39, 53),
			new Point(39, 54), new Point(39, 55), new Point(39, 56), new Point(39, 57), new Point(39, 58),
			new Point(39, 59), new Point(39, 60), new Point(39, 61), new Point(39, 62), new Point(39, 63),
			new Point(40, 20), new Point(40, 21), new Point(40, 22), new Point(40, 23), new Point(40, 24),
			new Point(40, 25), new Point(40, 26), new Point(40, 27), new Point(40, 28), new Point(40, 29),
			new Point(40, 30), new Point(40, 31), new Point(40, 48), new Point(40, 49), new Point(40, 50),
			new Point(40, 51), new Point(40, 52), new Point(40, 53), new Point(40, 54), new Point(40, 55),
			new Point(40, 56), new Point(40, 57), new Point(40, 58), new Point(40, 59), new Point(40, 60),
			new Point(40, 61), new Point(40, 62), new Point(40, 63), new Point(41, 20), new Point(41, 21),
			new Point(41, 22), new Point(41, 23), new Point(41, 24), new Point(41, 25), new Point(41, 26),
			new Point(41, 27), new Point(41, 28), new Point(41, 29), new Point(41, 30), new Point(41, 31),
			new Point(41, 48), new Point(41, 49), new Point(41, 50), new Point(41, 51), new Point(41, 52),
			new Point(41, 53), new Point(41, 54), new Point(41, 55), new Point(41, 56), new Point(41, 57),
			new Point(41, 58), new Point(41, 59), new Point(41, 60), new Point(41, 61), new Point(41, 62),
			new Point(41, 63), new Point(42, 20), new Point(42, 21), new Point(42, 22), new Point(42, 23),
			new Point(42, 24), new Point(42, 25), new Point(42, 26), new Point(42, 27), new Point(42, 28),
			new Point(42, 29), new Point(42, 30), new Point(42, 31), new Point(42, 52), new Point(42, 53),
			new Point(42, 54), new Point(42, 55), new Point(42, 56), new Point(42, 57), new Point(42, 58),
			new Point(42, 59), new Point(42, 60), new Point(42, 61), new Point(42, 62), new Point(42, 63),
			new Point(43, 20), new Point(43, 21), new Point(43, 22), new Point(43, 23), new Point(43, 24),
			new Point(43, 25), new Point(43, 26), new Point(43, 27), new Point(43, 28), new Point(43, 29),
			new Point(43, 30), new Point(43, 31), new Point(43, 52), new Point(43, 53), new Point(43, 54),
			new Point(43, 55), new Point(43, 56), new Point(43, 57), new Point(43, 58), new Point(43, 59),
			new Point(43, 60), new Point(43, 61), new Point(43, 62), new Point(43, 63), new Point(44, 16),
			new Point(44, 17), new Point(44, 18), new Point(44, 19), new Point(44, 20), new Point(44, 21),
			new Point(44, 22), new Point(44, 23), new Point(44, 24), new Point(44, 25), new Point(44, 26),
			new Point(44, 27), new Point(44, 28), new Point(44, 29), new Point(44, 30), new Point(44, 31),
			new Point(44, 52), new Point(44, 53), new Point(44, 54), new Point(44, 55), new Point(44, 56),
			new Point(44, 57), new Point(44, 58), new Point(44, 59), new Point(44, 60), new Point(44, 61),
			new Point(44, 62), new Point(44, 63), new Point(45, 16), new Point(45, 17), new Point(45, 18),
			new Point(45, 19), new Point(45, 20), new Point(45, 21), new Point(45, 22), new Point(45, 23),
			new Point(45, 24), new Point(45, 25), new Point(45, 26), new Point(45, 27), new Point(45, 28),
			new Point(45, 29), new Point(45, 30), new Point(45, 31), new Point(45, 52), new Point(45, 53),
			new Point(45, 54), new Point(45, 55), new Point(45, 56), new Point(45, 57), new Point(45, 58),
			new Point(45, 59), new Point(45, 60), new Point(45, 61), new Point(45, 62), new Point(45, 63),
			new Point(46, 16), new Point(46, 17), new Point(46, 18), new Point(46, 19), new Point(46, 20),
			new Point(46, 21), new Point(46, 22), new Point(46, 23), new Point(46, 24), new Point(46, 25),
			new Point(46, 26), new Point(46, 27), new Point(46, 28), new Point(46, 29), new Point(46, 30),
			new Point(46, 31), new Point(47, 16), new Point(47, 17), new Point(47, 18), new Point(47, 19),
			new Point(47, 20), new Point(47, 21), new Point(47, 22), new Point(47, 23), new Point(47, 24),
			new Point(47, 25), new Point(47, 26), new Point(47, 27), new Point(47, 28), new Point(47, 29),
			new Point(47, 30), new Point(47, 31), new Point(48, 16), new Point(48, 17), new Point(48, 18),
			new Point(48, 19), new Point(48, 20), new Point(48, 21), new Point(48, 22), new Point(48, 23),
			new Point(48, 24), new Point(48, 25), new Point(48, 26), new Point(48, 27), new Point(48, 28),
			new Point(48, 29), new Point(48, 30), new Point(48, 31), new Point(49, 16), new Point(49, 17),
			new Point(49, 18), new Point(49, 19), new Point(49, 20), new Point(49, 21), new Point(49, 22),
			new Point(49, 23), new Point(49, 24), new Point(49, 25), new Point(49, 26), new Point(49, 27),
			new Point(49, 28), new Point(49, 29), new Point(49, 30), new Point(49, 31), new Point(50, 20),
			new Point(50, 21), new Point(50, 22), new Point(50, 23), new Point(50, 24), new Point(50, 25),
			new Point(50, 26), new Point(50, 27), new Point(50, 28), new Point(50, 29), new Point(50, 30),
			new Point(50, 31), new Point(51, 20), new Point(51, 21), new Point(51, 22), new Point(51, 23),
			new Point(51, 24), new Point(51, 25), new Point(51, 26), new Point(51, 27), new Point(51, 28),
			new Point(51, 29), new Point(51, 30), new Point(51, 31), new Point(52, 20), new Point(52, 21),
			new Point(52, 22), new Point(52, 23), new Point(52, 24), new Point(52, 25), new Point(52, 26),
			new Point(52, 27), new Point(52, 28), new Point(52, 29), new Point(52, 30), new Point(52, 31),
			new Point(53, 20), new Point(53, 21), new Point(53, 22), new Point(53, 23), new Point(53, 24),
			new Point(53, 25), new Point(53, 26), new Point(53, 27), new Point(53, 28), new Point(53, 29),
			new Point(53, 30), new Point(53, 31) },
			SKIN_3PX_OVERLAY = { new Point(0, 36), new Point(0, 37), new Point(0, 38), new Point(0, 39),
					new Point(0, 40), new Point(0, 41), new Point(0, 42), new Point(0, 43), new Point(0, 44),
					new Point(0, 45), new Point(0, 46), new Point(0, 47), new Point(0, 52), new Point(0, 53),
					new Point(0, 54), new Point(0, 55), new Point(0, 56), new Point(0, 57), new Point(0, 58),
					new Point(0, 59), new Point(0, 60), new Point(0, 61), new Point(0, 62), new Point(0, 63),
					new Point(1, 36), new Point(1, 37), new Point(1, 38), new Point(1, 39), new Point(1, 40),
					new Point(1, 41), new Point(1, 42), new Point(1, 43), new Point(1, 44), new Point(1, 45),
					new Point(1, 46), new Point(1, 47), new Point(1, 52), new Point(1, 53), new Point(1, 54),
					new Point(1, 55), new Point(1, 56), new Point(1, 57), new Point(1, 58), new Point(1, 59),
					new Point(1, 60), new Point(1, 61), new Point(1, 62), new Point(1, 63), new Point(2, 36),
					new Point(2, 37), new Point(2, 38), new Point(2, 39), new Point(2, 40), new Point(2, 41),
					new Point(2, 42), new Point(2, 43), new Point(2, 44), new Point(2, 45), new Point(2, 46),
					new Point(2, 47), new Point(2, 52), new Point(2, 53), new Point(2, 54), new Point(2, 55),
					new Point(2, 56), new Point(2, 57), new Point(2, 58), new Point(2, 59), new Point(2, 60),
					new Point(2, 61), new Point(2, 62), new Point(2, 63), new Point(3, 36), new Point(3, 37),
					new Point(3, 38), new Point(3, 39), new Point(3, 40), new Point(3, 41), new Point(3, 42),
					new Point(3, 43), new Point(3, 44), new Point(3, 45), new Point(3, 46), new Point(3, 47),
					new Point(3, 52), new Point(3, 53), new Point(3, 54), new Point(3, 55), new Point(3, 56),
					new Point(3, 57), new Point(3, 58), new Point(3, 59), new Point(3, 60), new Point(3, 61),
					new Point(3, 62), new Point(3, 63), new Point(4, 32), new Point(4, 33), new Point(4, 34),
					new Point(4, 35), new Point(4, 36), new Point(4, 37), new Point(4, 38), new Point(4, 39),
					new Point(4, 40), new Point(4, 41), new Point(4, 42), new Point(4, 43), new Point(4, 44),
					new Point(4, 45), new Point(4, 46), new Point(4, 47), new Point(4, 48), new Point(4, 49),
					new Point(4, 50), new Point(4, 51), new Point(4, 52), new Point(4, 53), new Point(4, 54),
					new Point(4, 55), new Point(4, 56), new Point(4, 57), new Point(4, 58), new Point(4, 59),
					new Point(4, 60), new Point(4, 61), new Point(4, 62), new Point(4, 63), new Point(5, 32),
					new Point(5, 33), new Point(5, 34), new Point(5, 35), new Point(5, 36), new Point(5, 37),
					new Point(5, 38), new Point(5, 39), new Point(5, 40), new Point(5, 41), new Point(5, 42),
					new Point(5, 43), new Point(5, 44), new Point(5, 45), new Point(5, 46), new Point(5, 47),
					new Point(5, 48), new Point(5, 49), new Point(5, 50), new Point(5, 51), new Point(5, 52),
					new Point(5, 53), new Point(5, 54), new Point(5, 55), new Point(5, 56), new Point(5, 57),
					new Point(5, 58), new Point(5, 59), new Point(5, 60), new Point(5, 61), new Point(5, 62),
					new Point(5, 63), new Point(6, 32), new Point(6, 33), new Point(6, 34), new Point(6, 35),
					new Point(6, 36), new Point(6, 37), new Point(6, 38), new Point(6, 39), new Point(6, 40),
					new Point(6, 41), new Point(6, 42), new Point(6, 43), new Point(6, 44), new Point(6, 45),
					new Point(6, 46), new Point(6, 47), new Point(6, 48), new Point(6, 49), new Point(6, 50),
					new Point(6, 51), new Point(6, 52), new Point(6, 53), new Point(6, 54), new Point(6, 55),
					new Point(6, 56), new Point(6, 57), new Point(6, 58), new Point(6, 59), new Point(6, 60),
					new Point(6, 61), new Point(6, 62), new Point(6, 63), new Point(7, 32), new Point(7, 33),
					new Point(7, 34), new Point(7, 35), new Point(7, 36), new Point(7, 37), new Point(7, 38),
					new Point(7, 39), new Point(7, 40), new Point(7, 41), new Point(7, 42), new Point(7, 43),
					new Point(7, 44), new Point(7, 45), new Point(7, 46), new Point(7, 47), new Point(7, 48),
					new Point(7, 49), new Point(7, 50), new Point(7, 51), new Point(7, 52), new Point(7, 53),
					new Point(7, 54), new Point(7, 55), new Point(7, 56), new Point(7, 57), new Point(7, 58),
					new Point(7, 59), new Point(7, 60), new Point(7, 61), new Point(7, 62), new Point(7, 63),
					new Point(8, 32), new Point(8, 33), new Point(8, 34), new Point(8, 35), new Point(8, 36),
					new Point(8, 37), new Point(8, 38), new Point(8, 39), new Point(8, 40), new Point(8, 41),
					new Point(8, 42), new Point(8, 43), new Point(8, 44), new Point(8, 45), new Point(8, 46),
					new Point(8, 47), new Point(8, 48), new Point(8, 49), new Point(8, 50), new Point(8, 51),
					new Point(8, 52), new Point(8, 53), new Point(8, 54), new Point(8, 55), new Point(8, 56),
					new Point(8, 57), new Point(8, 58), new Point(8, 59), new Point(8, 60), new Point(8, 61),
					new Point(8, 62), new Point(8, 63), new Point(9, 32), new Point(9, 33), new Point(9, 34),
					new Point(9, 35), new Point(9, 36), new Point(9, 37), new Point(9, 38), new Point(9, 39),
					new Point(9, 40), new Point(9, 41), new Point(9, 42), new Point(9, 43), new Point(9, 44),
					new Point(9, 45), new Point(9, 46), new Point(9, 47), new Point(9, 48), new Point(9, 49),
					new Point(9, 50), new Point(9, 51), new Point(9, 52), new Point(9, 53), new Point(9, 54),
					new Point(9, 55), new Point(9, 56), new Point(9, 57), new Point(9, 58), new Point(9, 59),
					new Point(9, 60), new Point(9, 61), new Point(9, 62), new Point(9, 63), new Point(10, 32),
					new Point(10, 33), new Point(10, 34), new Point(10, 35), new Point(10, 36), new Point(10, 37),
					new Point(10, 38), new Point(10, 39), new Point(10, 40), new Point(10, 41), new Point(10, 42),
					new Point(10, 43), new Point(10, 44), new Point(10, 45), new Point(10, 46), new Point(10, 47),
					new Point(10, 48), new Point(10, 49), new Point(10, 50), new Point(10, 51), new Point(10, 52),
					new Point(10, 53), new Point(10, 54), new Point(10, 55), new Point(10, 56), new Point(10, 57),
					new Point(10, 58), new Point(10, 59), new Point(10, 60), new Point(10, 61), new Point(10, 62),
					new Point(10, 63), new Point(11, 32), new Point(11, 33), new Point(11, 34), new Point(11, 35),
					new Point(11, 36), new Point(11, 37), new Point(11, 38), new Point(11, 39), new Point(11, 40),
					new Point(11, 41), new Point(11, 42), new Point(11, 43), new Point(11, 44), new Point(11, 45),
					new Point(11, 46), new Point(11, 47), new Point(11, 48), new Point(11, 49), new Point(11, 50),
					new Point(11, 51), new Point(11, 52), new Point(11, 53), new Point(11, 54), new Point(11, 55),
					new Point(11, 56), new Point(11, 57), new Point(11, 58), new Point(11, 59), new Point(11, 60),
					new Point(11, 61), new Point(11, 62), new Point(11, 63), new Point(12, 36), new Point(12, 37),
					new Point(12, 38), new Point(12, 39), new Point(12, 40), new Point(12, 41), new Point(12, 42),
					new Point(12, 43), new Point(12, 44), new Point(12, 45), new Point(12, 46), new Point(12, 47),
					new Point(12, 52), new Point(12, 53), new Point(12, 54), new Point(12, 55), new Point(12, 56),
					new Point(12, 57), new Point(12, 58), new Point(12, 59), new Point(12, 60), new Point(12, 61),
					new Point(12, 62), new Point(12, 63), new Point(13, 36), new Point(13, 37), new Point(13, 38),
					new Point(13, 39), new Point(13, 40), new Point(13, 41), new Point(13, 42), new Point(13, 43),
					new Point(13, 44), new Point(13, 45), new Point(13, 46), new Point(13, 47), new Point(13, 52),
					new Point(13, 53), new Point(13, 54), new Point(13, 55), new Point(13, 56), new Point(13, 57),
					new Point(13, 58), new Point(13, 59), new Point(13, 60), new Point(13, 61), new Point(13, 62),
					new Point(13, 63), new Point(14, 36), new Point(14, 37), new Point(14, 38), new Point(14, 39),
					new Point(14, 40), new Point(14, 41), new Point(14, 42), new Point(14, 43), new Point(14, 44),
					new Point(14, 45), new Point(14, 46), new Point(14, 47), new Point(14, 52), new Point(14, 53),
					new Point(14, 54), new Point(14, 55), new Point(14, 56), new Point(14, 57), new Point(14, 58),
					new Point(14, 59), new Point(14, 60), new Point(14, 61), new Point(14, 62), new Point(14, 63),
					new Point(15, 36), new Point(15, 37), new Point(15, 38), new Point(15, 39), new Point(15, 40),
					new Point(15, 41), new Point(15, 42), new Point(15, 43), new Point(15, 44), new Point(15, 45),
					new Point(15, 46), new Point(15, 47), new Point(15, 52), new Point(15, 53), new Point(15, 54),
					new Point(15, 55), new Point(15, 56), new Point(15, 57), new Point(15, 58), new Point(15, 59),
					new Point(15, 60), new Point(15, 61), new Point(15, 62), new Point(15, 63), new Point(16, 36),
					new Point(16, 37), new Point(16, 38), new Point(16, 39), new Point(16, 40), new Point(16, 41),
					new Point(16, 42), new Point(16, 43), new Point(16, 44), new Point(16, 45), new Point(16, 46),
					new Point(16, 47), new Point(17, 36), new Point(17, 37), new Point(17, 38), new Point(17, 39),
					new Point(17, 40), new Point(17, 41), new Point(17, 42), new Point(17, 43), new Point(17, 44),
					new Point(17, 45), new Point(17, 46), new Point(17, 47), new Point(18, 36), new Point(18, 37),
					new Point(18, 38), new Point(18, 39), new Point(18, 40), new Point(18, 41), new Point(18, 42),
					new Point(18, 43), new Point(18, 44), new Point(18, 45), new Point(18, 46), new Point(18, 47),
					new Point(19, 36), new Point(19, 37), new Point(19, 38), new Point(19, 39), new Point(19, 40),
					new Point(19, 41), new Point(19, 42), new Point(19, 43), new Point(19, 44), new Point(19, 45),
					new Point(19, 46), new Point(19, 47), new Point(20, 32), new Point(20, 33), new Point(20, 34),
					new Point(20, 35), new Point(20, 36), new Point(20, 37), new Point(20, 38), new Point(20, 39),
					new Point(20, 40), new Point(20, 41), new Point(20, 42), new Point(20, 43), new Point(20, 44),
					new Point(20, 45), new Point(20, 46), new Point(20, 47), new Point(21, 32), new Point(21, 33),
					new Point(21, 34), new Point(21, 35), new Point(21, 36), new Point(21, 37), new Point(21, 38),
					new Point(21, 39), new Point(21, 40), new Point(21, 41), new Point(21, 42), new Point(21, 43),
					new Point(21, 44), new Point(21, 45), new Point(21, 46), new Point(21, 47), new Point(22, 32),
					new Point(22, 33), new Point(22, 34), new Point(22, 35), new Point(22, 36), new Point(22, 37),
					new Point(22, 38), new Point(22, 39), new Point(22, 40), new Point(22, 41), new Point(22, 42),
					new Point(22, 43), new Point(22, 44), new Point(22, 45), new Point(22, 46), new Point(22, 47),
					new Point(23, 32), new Point(23, 33), new Point(23, 34), new Point(23, 35), new Point(23, 36),
					new Point(23, 37), new Point(23, 38), new Point(23, 39), new Point(23, 40), new Point(23, 41),
					new Point(23, 42), new Point(23, 43), new Point(23, 44), new Point(23, 45), new Point(23, 46),
					new Point(23, 47), new Point(24, 32), new Point(24, 33), new Point(24, 34), new Point(24, 35),
					new Point(24, 36), new Point(24, 37), new Point(24, 38), new Point(24, 39), new Point(24, 40),
					new Point(24, 41), new Point(24, 42), new Point(24, 43), new Point(24, 44), new Point(24, 45),
					new Point(24, 46), new Point(24, 47), new Point(25, 32), new Point(25, 33), new Point(25, 34),
					new Point(25, 35), new Point(25, 36), new Point(25, 37), new Point(25, 38), new Point(25, 39),
					new Point(25, 40), new Point(25, 41), new Point(25, 42), new Point(25, 43), new Point(25, 44),
					new Point(25, 45), new Point(25, 46), new Point(25, 47), new Point(26, 32), new Point(26, 33),
					new Point(26, 34), new Point(26, 35), new Point(26, 36), new Point(26, 37), new Point(26, 38),
					new Point(26, 39), new Point(26, 40), new Point(26, 41), new Point(26, 42), new Point(26, 43),
					new Point(26, 44), new Point(26, 45), new Point(26, 46), new Point(26, 47), new Point(27, 32),
					new Point(27, 33), new Point(27, 34), new Point(27, 35), new Point(27, 36), new Point(27, 37),
					new Point(27, 38), new Point(27, 39), new Point(27, 40), new Point(27, 41), new Point(27, 42),
					new Point(27, 43), new Point(27, 44), new Point(27, 45), new Point(27, 46), new Point(27, 47),
					new Point(28, 32), new Point(28, 33), new Point(28, 34), new Point(28, 35), new Point(28, 36),
					new Point(28, 37), new Point(28, 38), new Point(28, 39), new Point(28, 40), new Point(28, 41),
					new Point(28, 42), new Point(28, 43), new Point(28, 44), new Point(28, 45), new Point(28, 46),
					new Point(28, 47), new Point(29, 32), new Point(29, 33), new Point(29, 34), new Point(29, 35),
					new Point(29, 36), new Point(29, 37), new Point(29, 38), new Point(29, 39), new Point(29, 40),
					new Point(29, 41), new Point(29, 42), new Point(29, 43), new Point(29, 44), new Point(29, 45),
					new Point(29, 46), new Point(29, 47), new Point(30, 32), new Point(30, 33), new Point(30, 34),
					new Point(30, 35), new Point(30, 36), new Point(30, 37), new Point(30, 38), new Point(30, 39),
					new Point(30, 40), new Point(30, 41), new Point(30, 42), new Point(30, 43), new Point(30, 44),
					new Point(30, 45), new Point(30, 46), new Point(30, 47), new Point(31, 32), new Point(31, 33),
					new Point(31, 34), new Point(31, 35), new Point(31, 36), new Point(31, 37), new Point(31, 38),
					new Point(31, 39), new Point(31, 40), new Point(31, 41), new Point(31, 42), new Point(31, 43),
					new Point(31, 44), new Point(31, 45), new Point(31, 46), new Point(31, 47), new Point(32, 8),
					new Point(32, 9), new Point(32, 10), new Point(32, 11), new Point(32, 12), new Point(32, 13),
					new Point(32, 14), new Point(32, 15), new Point(32, 32), new Point(32, 33), new Point(32, 34),
					new Point(32, 35), new Point(32, 36), new Point(32, 37), new Point(32, 38), new Point(32, 39),
					new Point(32, 40), new Point(32, 41), new Point(32, 42), new Point(32, 43), new Point(32, 44),
					new Point(32, 45), new Point(32, 46), new Point(32, 47), new Point(33, 8), new Point(33, 9),
					new Point(33, 10), new Point(33, 11), new Point(33, 12), new Point(33, 13), new Point(33, 14),
					new Point(33, 15), new Point(33, 32), new Point(33, 33), new Point(33, 34), new Point(33, 35),
					new Point(33, 36), new Point(33, 37), new Point(33, 38), new Point(33, 39), new Point(33, 40),
					new Point(33, 41), new Point(33, 42), new Point(33, 43), new Point(33, 44), new Point(33, 45),
					new Point(33, 46), new Point(33, 47), new Point(34, 8), new Point(34, 9), new Point(34, 10),
					new Point(34, 11), new Point(34, 12), new Point(34, 13), new Point(34, 14), new Point(34, 15),
					new Point(34, 32), new Point(34, 33), new Point(34, 34), new Point(34, 35), new Point(34, 36),
					new Point(34, 37), new Point(34, 38), new Point(34, 39), new Point(34, 40), new Point(34, 41),
					new Point(34, 42), new Point(34, 43), new Point(34, 44), new Point(34, 45), new Point(34, 46),
					new Point(34, 47), new Point(35, 8), new Point(35, 9), new Point(35, 10), new Point(35, 11),
					new Point(35, 12), new Point(35, 13), new Point(35, 14), new Point(35, 15), new Point(35, 32),
					new Point(35, 33), new Point(35, 34), new Point(35, 35), new Point(35, 36), new Point(35, 37),
					new Point(35, 38), new Point(35, 39), new Point(35, 40), new Point(35, 41), new Point(35, 42),
					new Point(35, 43), new Point(35, 44), new Point(35, 45), new Point(35, 46), new Point(35, 47),
					new Point(36, 8), new Point(36, 9), new Point(36, 10), new Point(36, 11), new Point(36, 12),
					new Point(36, 13), new Point(36, 14), new Point(36, 15), new Point(36, 36), new Point(36, 37),
					new Point(36, 38), new Point(36, 39), new Point(36, 40), new Point(36, 41), new Point(36, 42),
					new Point(36, 43), new Point(36, 44), new Point(36, 45), new Point(36, 46), new Point(36, 47),
					new Point(37, 8), new Point(37, 9), new Point(37, 10), new Point(37, 11), new Point(37, 12),
					new Point(37, 13), new Point(37, 14), new Point(37, 15), new Point(37, 36), new Point(37, 37),
					new Point(37, 38), new Point(37, 39), new Point(37, 40), new Point(37, 41), new Point(37, 42),
					new Point(37, 43), new Point(37, 44), new Point(37, 45), new Point(37, 46), new Point(37, 47),
					new Point(38, 8), new Point(38, 9), new Point(38, 10), new Point(38, 11), new Point(38, 12),
					new Point(38, 13), new Point(38, 14), new Point(38, 15), new Point(38, 36), new Point(38, 37),
					new Point(38, 38), new Point(38, 39), new Point(38, 40), new Point(38, 41), new Point(38, 42),
					new Point(38, 43), new Point(38, 44), new Point(38, 45), new Point(38, 46), new Point(38, 47),
					new Point(39, 8), new Point(39, 9), new Point(39, 10), new Point(39, 11), new Point(39, 12),
					new Point(39, 13), new Point(39, 14), new Point(39, 15), new Point(39, 36), new Point(39, 37),
					new Point(39, 38), new Point(39, 39), new Point(39, 40), new Point(39, 41), new Point(39, 42),
					new Point(39, 43), new Point(39, 44), new Point(39, 45), new Point(39, 46), new Point(39, 47),
					new Point(40, 0), new Point(40, 1), new Point(40, 2), new Point(40, 3), new Point(40, 4),
					new Point(40, 5), new Point(40, 6), new Point(40, 7), new Point(40, 8), new Point(40, 9),
					new Point(40, 10), new Point(40, 11), new Point(40, 12), new Point(40, 13), new Point(40, 14),
					new Point(40, 15), new Point(40, 36), new Point(40, 37), new Point(40, 38), new Point(40, 39),
					new Point(40, 40), new Point(40, 41), new Point(40, 42), new Point(40, 43), new Point(40, 44),
					new Point(40, 45), new Point(40, 46), new Point(40, 47), new Point(41, 0), new Point(41, 1),
					new Point(41, 2), new Point(41, 3), new Point(41, 4), new Point(41, 5), new Point(41, 6),
					new Point(41, 7), new Point(41, 8), new Point(41, 9), new Point(41, 10), new Point(41, 11),
					new Point(41, 12), new Point(41, 13), new Point(41, 14), new Point(41, 15), new Point(41, 36),
					new Point(41, 37), new Point(41, 38), new Point(41, 39), new Point(41, 40), new Point(41, 41),
					new Point(41, 42), new Point(41, 43), new Point(41, 44), new Point(41, 45), new Point(41, 46),
					new Point(41, 47), new Point(42, 0), new Point(42, 1), new Point(42, 2), new Point(42, 3),
					new Point(42, 4), new Point(42, 5), new Point(42, 6), new Point(42, 7), new Point(42, 8),
					new Point(42, 9), new Point(42, 10), new Point(42, 11), new Point(42, 12), new Point(42, 13),
					new Point(42, 14), new Point(42, 15), new Point(42, 36), new Point(42, 37), new Point(42, 38),
					new Point(42, 39), new Point(42, 40), new Point(42, 41), new Point(42, 42), new Point(42, 43),
					new Point(42, 44), new Point(42, 45), new Point(42, 46), new Point(42, 47), new Point(43, 0),
					new Point(43, 1), new Point(43, 2), new Point(43, 3), new Point(43, 4), new Point(43, 5),
					new Point(43, 6), new Point(43, 7), new Point(43, 8), new Point(43, 9), new Point(43, 10),
					new Point(43, 11), new Point(43, 12), new Point(43, 13), new Point(43, 14), new Point(43, 15),
					new Point(43, 36), new Point(43, 37), new Point(43, 38), new Point(43, 39), new Point(43, 40),
					new Point(43, 41), new Point(43, 42), new Point(43, 43), new Point(43, 44), new Point(43, 45),
					new Point(43, 46), new Point(43, 47), new Point(44, 0), new Point(44, 1), new Point(44, 2),
					new Point(44, 3), new Point(44, 4), new Point(44, 5), new Point(44, 6), new Point(44, 7),
					new Point(44, 8), new Point(44, 9), new Point(44, 10), new Point(44, 11), new Point(44, 12),
					new Point(44, 13), new Point(44, 14), new Point(44, 15), new Point(44, 32), new Point(44, 33),
					new Point(44, 34), new Point(44, 35), new Point(44, 36), new Point(44, 37), new Point(44, 38),
					new Point(44, 39), new Point(44, 40), new Point(44, 41), new Point(44, 42), new Point(44, 43),
					new Point(44, 44), new Point(44, 45), new Point(44, 46), new Point(44, 47), new Point(45, 0),
					new Point(45, 1), new Point(45, 2), new Point(45, 3), new Point(45, 4), new Point(45, 5),
					new Point(45, 6), new Point(45, 7), new Point(45, 8), new Point(45, 9), new Point(45, 10),
					new Point(45, 11), new Point(45, 12), new Point(45, 13), new Point(45, 14), new Point(45, 15),
					new Point(45, 32), new Point(45, 33), new Point(45, 34), new Point(45, 35), new Point(45, 36),
					new Point(45, 37), new Point(45, 38), new Point(45, 39), new Point(45, 40), new Point(45, 41),
					new Point(45, 42), new Point(45, 43), new Point(45, 44), new Point(45, 45), new Point(45, 46),
					new Point(45, 47), new Point(46, 0), new Point(46, 1), new Point(46, 2), new Point(46, 3),
					new Point(46, 4), new Point(46, 5), new Point(46, 6), new Point(46, 7), new Point(46, 8),
					new Point(46, 9), new Point(46, 10), new Point(46, 11), new Point(46, 12), new Point(46, 13),
					new Point(46, 14), new Point(46, 15), new Point(46, 32), new Point(46, 33), new Point(46, 34),
					new Point(46, 35), new Point(46, 36), new Point(46, 37), new Point(46, 38), new Point(46, 39),
					new Point(46, 40), new Point(46, 41), new Point(46, 42), new Point(46, 43), new Point(46, 44),
					new Point(46, 45), new Point(46, 46), new Point(46, 47), new Point(47, 0), new Point(47, 1),
					new Point(47, 2), new Point(47, 3), new Point(47, 4), new Point(47, 5), new Point(47, 6),
					new Point(47, 7), new Point(47, 8), new Point(47, 9), new Point(47, 10), new Point(47, 11),
					new Point(47, 12), new Point(47, 13), new Point(47, 14), new Point(47, 15), new Point(47, 32),
					new Point(47, 33), new Point(47, 34), new Point(47, 35), new Point(47, 36), new Point(47, 37),
					new Point(47, 38), new Point(47, 39), new Point(47, 40), new Point(47, 41), new Point(47, 42),
					new Point(47, 43), new Point(47, 44), new Point(47, 45), new Point(47, 46), new Point(47, 47),
					new Point(48, 0), new Point(48, 1), new Point(48, 2), new Point(48, 3), new Point(48, 4),
					new Point(48, 5), new Point(48, 6), new Point(48, 7), new Point(48, 8), new Point(48, 9),
					new Point(48, 10), new Point(48, 11), new Point(48, 12), new Point(48, 13), new Point(48, 14),
					new Point(48, 15), new Point(48, 32), new Point(48, 33), new Point(48, 34), new Point(48, 35),
					new Point(48, 36), new Point(48, 37), new Point(48, 38), new Point(48, 39), new Point(48, 40),
					new Point(48, 41), new Point(48, 42), new Point(48, 43), new Point(48, 44), new Point(48, 45),
					new Point(48, 46), new Point(48, 47), new Point(48, 52), new Point(48, 53), new Point(48, 54),
					new Point(48, 55), new Point(48, 56), new Point(48, 57), new Point(48, 58), new Point(48, 59),
					new Point(48, 60), new Point(48, 61), new Point(48, 62), new Point(48, 63), new Point(49, 0),
					new Point(49, 1), new Point(49, 2), new Point(49, 3), new Point(49, 4), new Point(49, 5),
					new Point(49, 6), new Point(49, 7), new Point(49, 8), new Point(49, 9), new Point(49, 10),
					new Point(49, 11), new Point(49, 12), new Point(49, 13), new Point(49, 14), new Point(49, 15),
					new Point(49, 32), new Point(49, 33), new Point(49, 34), new Point(49, 35), new Point(49, 36),
					new Point(49, 37), new Point(49, 38), new Point(49, 39), new Point(49, 40), new Point(49, 41),
					new Point(49, 42), new Point(49, 43), new Point(49, 44), new Point(49, 45), new Point(49, 46),
					new Point(49, 47), new Point(49, 52), new Point(49, 53), new Point(49, 54), new Point(49, 55),
					new Point(49, 56), new Point(49, 57), new Point(49, 58), new Point(49, 59), new Point(49, 60),
					new Point(49, 61), new Point(49, 62), new Point(49, 63), new Point(50, 0), new Point(50, 1),
					new Point(50, 2), new Point(50, 3), new Point(50, 4), new Point(50, 5), new Point(50, 6),
					new Point(50, 7), new Point(50, 8), new Point(50, 9), new Point(50, 10), new Point(50, 11),
					new Point(50, 12), new Point(50, 13), new Point(50, 14), new Point(50, 15), new Point(50, 36),
					new Point(50, 37), new Point(50, 38), new Point(50, 39), new Point(50, 40), new Point(50, 41),
					new Point(50, 42), new Point(50, 43), new Point(50, 44), new Point(50, 45), new Point(50, 46),
					new Point(50, 47), new Point(50, 52), new Point(50, 53), new Point(50, 54), new Point(50, 55),
					new Point(50, 56), new Point(50, 57), new Point(50, 58), new Point(50, 59), new Point(50, 60),
					new Point(50, 61), new Point(50, 62), new Point(50, 63), new Point(51, 0), new Point(51, 1),
					new Point(51, 2), new Point(51, 3), new Point(51, 4), new Point(51, 5), new Point(51, 6),
					new Point(51, 7), new Point(51, 8), new Point(51, 9), new Point(51, 10), new Point(51, 11),
					new Point(51, 12), new Point(51, 13), new Point(51, 14), new Point(51, 15), new Point(51, 36),
					new Point(51, 37), new Point(51, 38), new Point(51, 39), new Point(51, 40), new Point(51, 41),
					new Point(51, 42), new Point(51, 43), new Point(51, 44), new Point(51, 45), new Point(51, 46),
					new Point(51, 47), new Point(51, 52), new Point(51, 53), new Point(51, 54), new Point(51, 55),
					new Point(51, 56), new Point(51, 57), new Point(51, 58), new Point(51, 59), new Point(51, 60),
					new Point(51, 61), new Point(51, 62), new Point(51, 63), new Point(52, 0), new Point(52, 1),
					new Point(52, 2), new Point(52, 3), new Point(52, 4), new Point(52, 5), new Point(52, 6),
					new Point(52, 7), new Point(52, 8), new Point(52, 9), new Point(52, 10), new Point(52, 11),
					new Point(52, 12), new Point(52, 13), new Point(52, 14), new Point(52, 15), new Point(52, 36),
					new Point(52, 37), new Point(52, 38), new Point(52, 39), new Point(52, 40), new Point(52, 41),
					new Point(52, 42), new Point(52, 43), new Point(52, 44), new Point(52, 45), new Point(52, 46),
					new Point(52, 47), new Point(52, 48), new Point(52, 49), new Point(52, 50), new Point(52, 51),
					new Point(52, 52), new Point(52, 53), new Point(52, 54), new Point(52, 55), new Point(52, 56),
					new Point(52, 57), new Point(52, 58), new Point(52, 59), new Point(52, 60), new Point(52, 61),
					new Point(52, 62), new Point(52, 63), new Point(53, 0), new Point(53, 1), new Point(53, 2),
					new Point(53, 3), new Point(53, 4), new Point(53, 5), new Point(53, 6), new Point(53, 7),
					new Point(53, 8), new Point(53, 9), new Point(53, 10), new Point(53, 11), new Point(53, 12),
					new Point(53, 13), new Point(53, 14), new Point(53, 15), new Point(53, 36), new Point(53, 37),
					new Point(53, 38), new Point(53, 39), new Point(53, 40), new Point(53, 41), new Point(53, 42),
					new Point(53, 43), new Point(53, 44), new Point(53, 45), new Point(53, 46), new Point(53, 47),
					new Point(53, 48), new Point(53, 49), new Point(53, 50), new Point(53, 51), new Point(53, 52),
					new Point(53, 53), new Point(53, 54), new Point(53, 55), new Point(53, 56), new Point(53, 57),
					new Point(53, 58), new Point(53, 59), new Point(53, 60), new Point(53, 61), new Point(53, 62),
					new Point(53, 63), new Point(54, 0), new Point(54, 1), new Point(54, 2), new Point(54, 3),
					new Point(54, 4), new Point(54, 5), new Point(54, 6), new Point(54, 7), new Point(54, 8),
					new Point(54, 9), new Point(54, 10), new Point(54, 11), new Point(54, 12), new Point(54, 13),
					new Point(54, 14), new Point(54, 15), new Point(54, 48), new Point(54, 49), new Point(54, 50),
					new Point(54, 51), new Point(54, 52), new Point(54, 53), new Point(54, 54), new Point(54, 55),
					new Point(54, 56), new Point(54, 57), new Point(54, 58), new Point(54, 59), new Point(54, 60),
					new Point(54, 61), new Point(54, 62), new Point(54, 63), new Point(55, 0), new Point(55, 1),
					new Point(55, 2), new Point(55, 3), new Point(55, 4), new Point(55, 5), new Point(55, 6),
					new Point(55, 7), new Point(55, 8), new Point(55, 9), new Point(55, 10), new Point(55, 11),
					new Point(55, 12), new Point(55, 13), new Point(55, 14), new Point(55, 15), new Point(55, 48),
					new Point(55, 49), new Point(55, 50), new Point(55, 51), new Point(55, 52), new Point(55, 53),
					new Point(55, 54), new Point(55, 55), new Point(55, 56), new Point(55, 57), new Point(55, 58),
					new Point(55, 59), new Point(55, 60), new Point(55, 61), new Point(55, 62), new Point(55, 63),
					new Point(56, 8), new Point(56, 9), new Point(56, 10), new Point(56, 11), new Point(56, 12),
					new Point(56, 13), new Point(56, 14), new Point(56, 15), new Point(56, 48), new Point(56, 49),
					new Point(56, 50), new Point(56, 51), new Point(56, 52), new Point(56, 53), new Point(56, 54),
					new Point(56, 55), new Point(56, 56), new Point(56, 57), new Point(56, 58), new Point(56, 59),
					new Point(56, 60), new Point(56, 61), new Point(56, 62), new Point(56, 63), new Point(57, 8),
					new Point(57, 9), new Point(57, 10), new Point(57, 11), new Point(57, 12), new Point(57, 13),
					new Point(57, 14), new Point(57, 15), new Point(57, 48), new Point(57, 49), new Point(57, 50),
					new Point(57, 51), new Point(57, 52), new Point(57, 53), new Point(57, 54), new Point(57, 55),
					new Point(57, 56), new Point(57, 57), new Point(57, 58), new Point(57, 59), new Point(57, 60),
					new Point(57, 61), new Point(57, 62), new Point(57, 63), new Point(58, 8), new Point(58, 9),
					new Point(58, 10), new Point(58, 11), new Point(58, 12), new Point(58, 13), new Point(58, 14),
					new Point(58, 15), new Point(58, 52), new Point(58, 53), new Point(58, 54), new Point(58, 55),
					new Point(58, 56), new Point(58, 57), new Point(58, 58), new Point(58, 59), new Point(58, 60),
					new Point(58, 61), new Point(58, 62), new Point(58, 63), new Point(59, 8), new Point(59, 9),
					new Point(59, 10), new Point(59, 11), new Point(59, 12), new Point(59, 13), new Point(59, 14),
					new Point(59, 15), new Point(59, 52), new Point(59, 53), new Point(59, 54), new Point(59, 55),
					new Point(59, 56), new Point(59, 57), new Point(59, 58), new Point(59, 59), new Point(59, 60),
					new Point(59, 61), new Point(59, 62), new Point(59, 63), new Point(60, 8), new Point(60, 9),
					new Point(60, 10), new Point(60, 11), new Point(60, 12), new Point(60, 13), new Point(60, 14),
					new Point(60, 15), new Point(60, 52), new Point(60, 53), new Point(60, 54), new Point(60, 55),
					new Point(60, 56), new Point(60, 57), new Point(60, 58), new Point(60, 59), new Point(60, 60),
					new Point(60, 61), new Point(60, 62), new Point(60, 63), new Point(61, 8), new Point(61, 9),
					new Point(61, 10), new Point(61, 11), new Point(61, 12), new Point(61, 13), new Point(61, 14),
					new Point(61, 15), new Point(61, 52), new Point(61, 53), new Point(61, 54), new Point(61, 55),
					new Point(61, 56), new Point(61, 57), new Point(61, 58), new Point(61, 59), new Point(61, 60),
					new Point(61, 61), new Point(61, 62), new Point(61, 63), new Point(62, 8), new Point(62, 9),
					new Point(62, 10), new Point(62, 11), new Point(62, 12), new Point(62, 13), new Point(62, 14),
					new Point(62, 15), new Point(63, 8), new Point(63, 9), new Point(63, 10), new Point(63, 11),
					new Point(63, 12), new Point(63, 13), new Point(63, 14), new Point(63, 15) };

	/**
	 * X and Y coordinates that are different in comparison to the 3px-Skin.<br>
	 * {@link #SKIN_3PX} and {@link #SKIN_4PX_DIFF} would be all pixels used by a
	 * 4px-Skin
	 */
	private static final Point[] SKIN_4PX_DIFF = { new Point(42, 48), new Point(42, 49), new Point(42, 50),
			new Point(42, 51), new Point(43, 48), new Point(43, 49), new Point(43, 50), new Point(43, 51),
			new Point(46, 52), new Point(46, 53), new Point(46, 54), new Point(46, 55), new Point(46, 56),
			new Point(46, 57), new Point(46, 58), new Point(46, 59), new Point(46, 60), new Point(46, 61),
			new Point(46, 62), new Point(46, 63), new Point(47, 52), new Point(47, 53), new Point(47, 54),
			new Point(47, 55), new Point(47, 56), new Point(47, 57), new Point(47, 58), new Point(47, 59),
			new Point(47, 60), new Point(47, 61), new Point(47, 62), new Point(47, 63), new Point(50, 16),
			new Point(50, 17), new Point(50, 18), new Point(50, 19), new Point(51, 16), new Point(51, 17),
			new Point(51, 18), new Point(51, 19), new Point(54, 20), new Point(54, 21), new Point(54, 22),
			new Point(54, 23), new Point(54, 24), new Point(54, 25), new Point(54, 26), new Point(54, 27),
			new Point(54, 28), new Point(54, 29), new Point(54, 30), new Point(54, 31), new Point(55, 20),
			new Point(55, 21), new Point(55, 22), new Point(55, 23), new Point(55, 24), new Point(55, 25),
			new Point(55, 26), new Point(55, 27), new Point(55, 28), new Point(55, 29), new Point(55, 30),
			new Point(55, 31) },
			SKIN_4PX_OVERLAY_DIFF = { new Point(50, 32), new Point(50, 33), new Point(50, 34), new Point(50, 35),
					new Point(51, 32), new Point(51, 33), new Point(51, 34), new Point(51, 35), new Point(54, 36),
					new Point(54, 37), new Point(54, 38), new Point(54, 39), new Point(54, 40), new Point(54, 41),
					new Point(54, 42), new Point(54, 43), new Point(54, 44), new Point(54, 45), new Point(54, 46),
					new Point(54, 47), new Point(55, 36), new Point(55, 37), new Point(55, 38), new Point(55, 39),
					new Point(55, 40), new Point(55, 41), new Point(55, 42), new Point(55, 43), new Point(55, 44),
					new Point(55, 45), new Point(55, 46), new Point(55, 47), new Point(58, 48), new Point(58, 49),
					new Point(58, 50), new Point(58, 51), new Point(59, 48), new Point(59, 49), new Point(59, 50),
					new Point(59, 51), new Point(62, 52), new Point(62, 53), new Point(62, 54), new Point(62, 55),
					new Point(62, 56), new Point(62, 57), new Point(62, 58), new Point(62, 59), new Point(62, 60),
					new Point(62, 61), new Point(62, 62), new Point(62, 63), new Point(63, 52), new Point(63, 53),
					new Point(63, 54), new Point(63, 55), new Point(63, 56), new Point(63, 57), new Point(63, 58),
					new Point(63, 59), new Point(63, 60), new Point(63, 61), new Point(63, 62), new Point(63, 63) };

	// Duplicates from SKIN_3PX etc.
	private static final Point[] LEGACY_ARM = { new Point(40, 20), new Point(40, 21), new Point(40, 22),
			new Point(40, 23), new Point(40, 24), new Point(40, 25), new Point(40, 26), new Point(40, 27),
			new Point(40, 28), new Point(40, 29), new Point(40, 30), new Point(40, 31), new Point(41, 20),
			new Point(41, 21), new Point(41, 22), new Point(41, 23), new Point(41, 24), new Point(41, 25),
			new Point(41, 26), new Point(41, 27), new Point(41, 28), new Point(41, 29), new Point(41, 30),
			new Point(41, 31), new Point(42, 20), new Point(42, 21), new Point(42, 22), new Point(42, 23),
			new Point(42, 24), new Point(42, 25), new Point(42, 26), new Point(42, 27), new Point(42, 28),
			new Point(42, 29), new Point(42, 30), new Point(42, 31), new Point(43, 20), new Point(43, 21),
			new Point(43, 22), new Point(43, 23), new Point(43, 24), new Point(43, 25), new Point(43, 26),
			new Point(43, 27), new Point(43, 28), new Point(43, 29), new Point(43, 30), new Point(43, 31),
			new Point(44, 16), new Point(44, 17), new Point(44, 18), new Point(44, 19), new Point(44, 20),
			new Point(44, 21), new Point(44, 22), new Point(44, 23), new Point(44, 24), new Point(44, 25),
			new Point(44, 26), new Point(44, 27), new Point(44, 28), new Point(44, 29), new Point(44, 30),
			new Point(44, 31), new Point(45, 16), new Point(45, 17), new Point(45, 18), new Point(45, 19),
			new Point(45, 20), new Point(45, 21), new Point(45, 22), new Point(45, 23), new Point(45, 24),
			new Point(45, 25), new Point(45, 26), new Point(45, 27), new Point(45, 28), new Point(45, 29),
			new Point(45, 30), new Point(45, 31), new Point(46, 16), new Point(46, 17), new Point(46, 18),
			new Point(46, 19), new Point(46, 20), new Point(46, 21), new Point(46, 22), new Point(46, 23),
			new Point(46, 24), new Point(46, 25), new Point(46, 26), new Point(46, 27), new Point(46, 28),
			new Point(46, 29), new Point(46, 30), new Point(46, 31), new Point(47, 16), new Point(47, 17),
			new Point(47, 18), new Point(47, 19), new Point(47, 20), new Point(47, 21), new Point(47, 22),
			new Point(47, 23), new Point(47, 24), new Point(47, 25), new Point(47, 26), new Point(47, 27),
			new Point(47, 28), new Point(47, 29), new Point(47, 30), new Point(47, 31), new Point(48, 16),
			new Point(48, 17), new Point(48, 18), new Point(48, 19), new Point(48, 20), new Point(48, 21),
			new Point(48, 22), new Point(48, 23), new Point(48, 24), new Point(48, 25), new Point(48, 26),
			new Point(48, 27), new Point(48, 28), new Point(48, 29), new Point(48, 30), new Point(48, 31),
			new Point(49, 16), new Point(49, 17), new Point(49, 18), new Point(49, 19), new Point(49, 20),
			new Point(49, 21), new Point(49, 22), new Point(49, 23), new Point(49, 24), new Point(49, 25),
			new Point(49, 26), new Point(49, 27), new Point(49, 28), new Point(49, 29), new Point(49, 30),
			new Point(49, 31), new Point(50, 16), new Point(50, 17), new Point(50, 18), new Point(50, 19),
			new Point(50, 20), new Point(50, 21), new Point(50, 22), new Point(50, 23), new Point(50, 24),
			new Point(50, 25), new Point(50, 26), new Point(50, 27), new Point(50, 28), new Point(50, 29),
			new Point(50, 30), new Point(50, 31), new Point(51, 16), new Point(51, 17), new Point(51, 18),
			new Point(51, 19), new Point(51, 20), new Point(51, 21), new Point(51, 22), new Point(51, 23),
			new Point(51, 24), new Point(51, 25), new Point(51, 26), new Point(51, 27), new Point(51, 28),
			new Point(51, 29), new Point(51, 30), new Point(51, 31), new Point(52, 20), new Point(52, 21),
			new Point(52, 22), new Point(52, 23), new Point(52, 24), new Point(52, 25), new Point(52, 26),
			new Point(52, 27), new Point(52, 28), new Point(52, 29), new Point(52, 30), new Point(52, 31),
			new Point(53, 20), new Point(53, 21), new Point(53, 22), new Point(53, 23), new Point(53, 24),
			new Point(53, 25), new Point(53, 26), new Point(53, 27), new Point(53, 28), new Point(53, 29),
			new Point(53, 30), new Point(53, 31), new Point(54, 20), new Point(54, 21), new Point(54, 22),
			new Point(54, 23), new Point(54, 24), new Point(54, 25), new Point(54, 26), new Point(54, 27),
			new Point(54, 28), new Point(54, 29), new Point(54, 30), new Point(54, 31), new Point(55, 20),
			new Point(55, 21), new Point(55, 22), new Point(55, 23), new Point(55, 24), new Point(55, 25),
			new Point(55, 26), new Point(55, 27), new Point(55, 28), new Point(55, 29), new Point(55, 30),
			new Point(55, 31) },
			LEGACY_LEG = { new Point(0, 20), new Point(0, 21), new Point(0, 22), new Point(0, 23), new Point(0, 24),
					new Point(0, 25), new Point(0, 26), new Point(0, 27), new Point(0, 28), new Point(0, 29),
					new Point(0, 30), new Point(0, 31), new Point(1, 20), new Point(1, 21), new Point(1, 22),
					new Point(1, 23), new Point(1, 24), new Point(1, 25), new Point(1, 26), new Point(1, 27),
					new Point(1, 28), new Point(1, 29), new Point(1, 30), new Point(1, 31), new Point(2, 20),
					new Point(2, 21), new Point(2, 22), new Point(2, 23), new Point(2, 24), new Point(2, 25),
					new Point(2, 26), new Point(2, 27), new Point(2, 28), new Point(2, 29), new Point(2, 30),
					new Point(2, 31), new Point(3, 20), new Point(3, 21), new Point(3, 22), new Point(3, 23),
					new Point(3, 24), new Point(3, 25), new Point(3, 26), new Point(3, 27), new Point(3, 28),
					new Point(3, 29), new Point(3, 30), new Point(3, 31), new Point(4, 16), new Point(4, 17),
					new Point(4, 18), new Point(4, 19), new Point(4, 20), new Point(4, 21), new Point(4, 22),
					new Point(4, 23), new Point(4, 24), new Point(4, 25), new Point(4, 26), new Point(4, 27),
					new Point(4, 28), new Point(4, 29), new Point(4, 30), new Point(4, 31), new Point(5, 16),
					new Point(5, 17), new Point(5, 18), new Point(5, 19), new Point(5, 20), new Point(5, 21),
					new Point(5, 22), new Point(5, 23), new Point(5, 24), new Point(5, 25), new Point(5, 26),
					new Point(5, 27), new Point(5, 28), new Point(5, 29), new Point(5, 30), new Point(5, 31),
					new Point(6, 16), new Point(6, 17), new Point(6, 18), new Point(6, 19), new Point(6, 20),
					new Point(6, 21), new Point(6, 22), new Point(6, 23), new Point(6, 24), new Point(6, 25),
					new Point(6, 26), new Point(6, 27), new Point(6, 28), new Point(6, 29), new Point(6, 30),
					new Point(6, 31), new Point(7, 16), new Point(7, 17), new Point(7, 18), new Point(7, 19),
					new Point(7, 20), new Point(7, 21), new Point(7, 22), new Point(7, 23), new Point(7, 24),
					new Point(7, 25), new Point(7, 26), new Point(7, 27), new Point(7, 28), new Point(7, 29),
					new Point(7, 30), new Point(7, 31), new Point(8, 16), new Point(8, 17), new Point(8, 18),
					new Point(8, 19), new Point(8, 20), new Point(8, 21), new Point(8, 22), new Point(8, 23),
					new Point(8, 24), new Point(8, 25), new Point(8, 26), new Point(8, 27), new Point(8, 28),
					new Point(8, 29), new Point(8, 30), new Point(8, 31), new Point(9, 16), new Point(9, 17),
					new Point(9, 18), new Point(9, 19), new Point(9, 20), new Point(9, 21), new Point(9, 22),
					new Point(9, 23), new Point(9, 24), new Point(9, 25), new Point(9, 26), new Point(9, 27),
					new Point(9, 28), new Point(9, 29), new Point(9, 30), new Point(9, 31), new Point(10, 16),
					new Point(10, 17), new Point(10, 18), new Point(10, 19), new Point(10, 20), new Point(10, 21),
					new Point(10, 22), new Point(10, 23), new Point(10, 24), new Point(10, 25), new Point(10, 26),
					new Point(10, 27), new Point(10, 28), new Point(10, 29), new Point(10, 30), new Point(10, 31),
					new Point(11, 16), new Point(11, 17), new Point(11, 18), new Point(11, 19), new Point(11, 20),
					new Point(11, 21), new Point(11, 22), new Point(11, 23), new Point(11, 24), new Point(11, 25),
					new Point(11, 26), new Point(11, 27), new Point(11, 28), new Point(11, 29), new Point(11, 30),
					new Point(11, 31), new Point(12, 20), new Point(12, 21), new Point(12, 22), new Point(12, 23),
					new Point(12, 24), new Point(12, 25), new Point(12, 26), new Point(12, 27), new Point(12, 28),
					new Point(12, 29), new Point(12, 30), new Point(12, 31), new Point(13, 20), new Point(13, 21),
					new Point(13, 22), new Point(13, 23), new Point(13, 24), new Point(13, 25), new Point(13, 26),
					new Point(13, 27), new Point(13, 28), new Point(13, 29), new Point(13, 30), new Point(13, 31),
					new Point(14, 20), new Point(14, 21), new Point(14, 22), new Point(14, 23), new Point(14, 24),
					new Point(14, 25), new Point(14, 26), new Point(14, 27), new Point(14, 28), new Point(14, 29),
					new Point(14, 30), new Point(14, 31), new Point(15, 20), new Point(15, 21), new Point(15, 22),
					new Point(15, 23), new Point(15, 24), new Point(15, 25), new Point(15, 26), new Point(15, 27),
					new Point(15, 28), new Point(15, 29), new Point(15, 30), new Point(15, 31) },
			SECOND_ARM = { new Point(32, 52), new Point(32, 53), new Point(32, 54), new Point(32, 55),
					new Point(32, 56), new Point(32, 57), new Point(32, 58), new Point(32, 59), new Point(32, 60),
					new Point(32, 61), new Point(32, 62), new Point(32, 63), new Point(33, 52), new Point(33, 53),
					new Point(33, 54), new Point(33, 55), new Point(33, 56), new Point(33, 57), new Point(33, 58),
					new Point(33, 59), new Point(33, 60), new Point(33, 61), new Point(33, 62), new Point(33, 63),
					new Point(34, 52), new Point(34, 53), new Point(34, 54), new Point(34, 55), new Point(34, 56),
					new Point(34, 57), new Point(34, 58), new Point(34, 59), new Point(34, 60), new Point(34, 61),
					new Point(34, 62), new Point(34, 63), new Point(35, 52), new Point(35, 53), new Point(35, 54),
					new Point(35, 55), new Point(35, 56), new Point(35, 57), new Point(35, 58), new Point(35, 59),
					new Point(35, 60), new Point(35, 61), new Point(35, 62), new Point(35, 63), new Point(36, 48),
					new Point(36, 49), new Point(36, 50), new Point(36, 51), new Point(36, 52), new Point(36, 53),
					new Point(36, 54), new Point(36, 55), new Point(36, 56), new Point(36, 57), new Point(36, 58),
					new Point(36, 59), new Point(36, 60), new Point(36, 61), new Point(36, 62), new Point(36, 63),
					new Point(37, 48), new Point(37, 49), new Point(37, 50), new Point(37, 51), new Point(37, 52),
					new Point(37, 53), new Point(37, 54), new Point(37, 55), new Point(37, 56), new Point(37, 57),
					new Point(37, 58), new Point(37, 59), new Point(37, 60), new Point(37, 61), new Point(37, 62),
					new Point(37, 63), new Point(38, 48), new Point(38, 49), new Point(38, 50), new Point(38, 51),
					new Point(38, 52), new Point(38, 53), new Point(38, 54), new Point(38, 55), new Point(38, 56),
					new Point(38, 57), new Point(38, 58), new Point(38, 59), new Point(38, 60), new Point(38, 61),
					new Point(38, 62), new Point(38, 63), new Point(39, 48), new Point(39, 49), new Point(39, 50),
					new Point(39, 51), new Point(39, 52), new Point(39, 53), new Point(39, 54), new Point(39, 55),
					new Point(39, 56), new Point(39, 57), new Point(39, 58), new Point(39, 59), new Point(39, 60),
					new Point(39, 61), new Point(39, 62), new Point(39, 63), new Point(40, 48), new Point(40, 49),
					new Point(40, 50), new Point(40, 51), new Point(40, 52), new Point(40, 53), new Point(40, 54),
					new Point(40, 55), new Point(40, 56), new Point(40, 57), new Point(40, 58), new Point(40, 59),
					new Point(40, 60), new Point(40, 61), new Point(40, 62), new Point(40, 63), new Point(41, 48),
					new Point(41, 49), new Point(41, 50), new Point(41, 51), new Point(41, 52), new Point(41, 53),
					new Point(41, 54), new Point(41, 55), new Point(41, 56), new Point(41, 57), new Point(41, 58),
					new Point(41, 59), new Point(41, 60), new Point(41, 61), new Point(41, 62), new Point(41, 63),
					new Point(42, 48), new Point(42, 49), new Point(42, 50), new Point(42, 51), new Point(42, 52),
					new Point(42, 53), new Point(42, 54), new Point(42, 55), new Point(42, 56), new Point(42, 57),
					new Point(42, 58), new Point(42, 59), new Point(42, 60), new Point(42, 61), new Point(42, 62),
					new Point(42, 63), new Point(43, 48), new Point(43, 49), new Point(43, 50), new Point(43, 51),
					new Point(43, 52), new Point(43, 53), new Point(43, 54), new Point(43, 55), new Point(43, 56),
					new Point(43, 57), new Point(43, 58), new Point(43, 59), new Point(43, 60), new Point(43, 61),
					new Point(43, 62), new Point(43, 63), new Point(44, 52), new Point(44, 53), new Point(44, 54),
					new Point(44, 55), new Point(44, 56), new Point(44, 57), new Point(44, 58), new Point(44, 59),
					new Point(44, 60), new Point(44, 61), new Point(44, 62), new Point(44, 63), new Point(45, 52),
					new Point(45, 53), new Point(45, 54), new Point(45, 55), new Point(45, 56), new Point(45, 57),
					new Point(45, 58), new Point(45, 59), new Point(45, 60), new Point(45, 61), new Point(45, 62),
					new Point(45, 63), new Point(46, 52), new Point(46, 53), new Point(46, 54), new Point(46, 55),
					new Point(46, 56), new Point(46, 57), new Point(46, 58), new Point(46, 59), new Point(46, 60),
					new Point(46, 61), new Point(46, 62), new Point(46, 63), new Point(47, 52), new Point(47, 53),
					new Point(47, 54), new Point(47, 55), new Point(47, 56), new Point(47, 57), new Point(47, 58),
					new Point(47, 59), new Point(47, 60), new Point(47, 61), new Point(47, 62), new Point(47, 63) },
			SECOND_LEG = { new Point(16, 52), new Point(16, 53), new Point(16, 54), new Point(16, 55),
					new Point(16, 56), new Point(16, 57), new Point(16, 58), new Point(16, 59), new Point(16, 60),
					new Point(16, 61), new Point(16, 62), new Point(16, 63), new Point(17, 52), new Point(17, 53),
					new Point(17, 54), new Point(17, 55), new Point(17, 56), new Point(17, 57), new Point(17, 58),
					new Point(17, 59), new Point(17, 60), new Point(17, 61), new Point(17, 62), new Point(17, 63),
					new Point(18, 52), new Point(18, 53), new Point(18, 54), new Point(18, 55), new Point(18, 56),
					new Point(18, 57), new Point(18, 58), new Point(18, 59), new Point(18, 60), new Point(18, 61),
					new Point(18, 62), new Point(18, 63), new Point(19, 52), new Point(19, 53), new Point(19, 54),
					new Point(19, 55), new Point(19, 56), new Point(19, 57), new Point(19, 58), new Point(19, 59),
					new Point(19, 60), new Point(19, 61), new Point(19, 62), new Point(19, 63), new Point(20, 48),
					new Point(20, 49), new Point(20, 50), new Point(20, 51), new Point(20, 52), new Point(20, 53),
					new Point(20, 54), new Point(20, 55), new Point(20, 56), new Point(20, 57), new Point(20, 58),
					new Point(20, 59), new Point(20, 60), new Point(20, 61), new Point(20, 62), new Point(20, 63),
					new Point(21, 48), new Point(21, 49), new Point(21, 50), new Point(21, 51), new Point(21, 52),
					new Point(21, 53), new Point(21, 54), new Point(21, 55), new Point(21, 56), new Point(21, 57),
					new Point(21, 58), new Point(21, 59), new Point(21, 60), new Point(21, 61), new Point(21, 62),
					new Point(21, 63), new Point(22, 48), new Point(22, 49), new Point(22, 50), new Point(22, 51),
					new Point(22, 52), new Point(22, 53), new Point(22, 54), new Point(22, 55), new Point(22, 56),
					new Point(22, 57), new Point(22, 58), new Point(22, 59), new Point(22, 60), new Point(22, 61),
					new Point(22, 62), new Point(22, 63), new Point(23, 48), new Point(23, 49), new Point(23, 50),
					new Point(23, 51), new Point(23, 52), new Point(23, 53), new Point(23, 54), new Point(23, 55),
					new Point(23, 56), new Point(23, 57), new Point(23, 58), new Point(23, 59), new Point(23, 60),
					new Point(23, 61), new Point(23, 62), new Point(23, 63), new Point(24, 48), new Point(24, 49),
					new Point(24, 50), new Point(24, 51), new Point(24, 52), new Point(24, 53), new Point(24, 54),
					new Point(24, 55), new Point(24, 56), new Point(24, 57), new Point(24, 58), new Point(24, 59),
					new Point(24, 60), new Point(24, 61), new Point(24, 62), new Point(24, 63), new Point(25, 48),
					new Point(25, 49), new Point(25, 50), new Point(25, 51), new Point(25, 52), new Point(25, 53),
					new Point(25, 54), new Point(25, 55), new Point(25, 56), new Point(25, 57), new Point(25, 58),
					new Point(25, 59), new Point(25, 60), new Point(25, 61), new Point(25, 62), new Point(25, 63),
					new Point(26, 48), new Point(26, 49), new Point(26, 50), new Point(26, 51), new Point(26, 52),
					new Point(26, 53), new Point(26, 54), new Point(26, 55), new Point(26, 56), new Point(26, 57),
					new Point(26, 58), new Point(26, 59), new Point(26, 60), new Point(26, 61), new Point(26, 62),
					new Point(26, 63), new Point(27, 48), new Point(27, 49), new Point(27, 50), new Point(27, 51),
					new Point(27, 52), new Point(27, 53), new Point(27, 54), new Point(27, 55), new Point(27, 56),
					new Point(27, 57), new Point(27, 58), new Point(27, 59), new Point(27, 60), new Point(27, 61),
					new Point(27, 62), new Point(27, 63), new Point(28, 52), new Point(28, 53), new Point(28, 54),
					new Point(28, 55), new Point(28, 56), new Point(28, 57), new Point(28, 58), new Point(28, 59),
					new Point(28, 60), new Point(28, 61), new Point(28, 62), new Point(28, 63), new Point(29, 52),
					new Point(29, 53), new Point(29, 54), new Point(29, 55), new Point(29, 56), new Point(29, 57),
					new Point(29, 58), new Point(29, 59), new Point(29, 60), new Point(29, 61), new Point(29, 62),
					new Point(29, 63), new Point(30, 52), new Point(30, 53), new Point(30, 54), new Point(30, 55),
					new Point(30, 56), new Point(30, 57), new Point(30, 58), new Point(30, 59), new Point(30, 60),
					new Point(30, 61), new Point(30, 62), new Point(30, 63), new Point(31, 52), new Point(31, 53),
					new Point(31, 54), new Point(31, 55), new Point(31, 56), new Point(31, 57), new Point(31, 58),
					new Point(31, 59), new Point(31, 60), new Point(31, 61), new Point(31, 62), new Point(31, 63) };

	/** The Image-Type to use for {@link #toCleanSkin(BufferedImage)} */
	private static final int IMAGE_TYPE = BufferedImage.TYPE_4BYTE_ABGR;

	/**
	 * Returns a new {@link BufferedImage} that is completely clean.<br>
	 * Only containing pixels that are rendered by the client
	 *
	 * @param img The Skin-Image to clean
	 * 
	 * @return A new and clean BufferedImage
	 * 
	 * @see #hasSkinDimensions(BufferedImage)
	 */
	public static BufferedImage toCleanSkin(BufferedImage img) {
		BufferedImage newImg = new BufferedImage(64, 64, IMAGE_TYPE);

		// Transparent background
		Graphics2D g = newImg.createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, 64, 64);
		g.dispose();

		boolean hasOverlay = hasOverlay(img);

		for (Point p : SKIN_3PX) {
			if (p.x < img.getWidth() && p.y < img.getHeight()) {
				newImg.setRGB(p.x, p.y, new Color(img.getRGB(p.x, p.y), false).getRGB());
			}
		}

		if (hasOverlay) {
			for (Point p : SKIN_3PX_OVERLAY) {
				newImg.setRGB(p.x, p.y, new Color(img.getRGB(p.x, p.y), true).getRGB());
			}
		}

		if (hasSteveArms(img)) {
			for (Point p : SKIN_4PX_DIFF) {
				if (p.x < img.getWidth() && p.y < img.getHeight()) {
					newImg.setRGB(p.x, p.y, new Color(img.getRGB(p.x, p.y), false).getRGB());
				}
			}

			if (hasOverlay) {
				for (Point p : SKIN_4PX_OVERLAY_DIFF) {
					newImg.setRGB(p.x, p.y, new Color(img.getRGB(p.x, p.y), true).getRGB());
				}
			}
		}

		if (isLegacySkin(img)) {
			for (int i = 0; i < SECOND_ARM.length; i++) {
				Point p = SECOND_ARM[i], lP = LEGACY_ARM[i];
				newImg.setRGB(p.x, p.y, new Color(img.getRGB(lP.x, lP.y), false).getRGB());
			}

			for (int i = 0; i < SECOND_LEG.length; i++) {
				Point p = SECOND_LEG[i], lP = LEGACY_LEG[i];
				newImg.setRGB(p.x, p.y, new Color(img.getRGB(lP.x, lP.y), false).getRGB());
			}
		}

		return newImg;
	}

	/**
	 * Checks if a skin has an overlay.<br>
	 * If any of the overlay-pixels are not 100% transparent, the skin is considered
	 * as having an overlay.
	 *
	 * @param img The Skin-Image to check
	 * 
	 * @return true, if it has an overlay
	 * 
	 * @see #hasSkinDimensions(BufferedImage)
	 */
	public static boolean hasOverlay(BufferedImage img) {
		boolean has4pxArms = false;

		if (img.getHeight() >= 64) {
			for (Point p : SKIN_3PX_OVERLAY) {
				if (new Color(img.getRGB(p.x, p.y), true).getAlpha() != 0) {
					has4pxArms = true;
					break;
				}
			}

			if (!has4pxArms) {
				for (Point p : SKIN_4PX_OVERLAY_DIFF) {
					if (new Color(img.getRGB(p.x, p.y), true).getAlpha() != 0) {
						has4pxArms = true;
						break;
					}
				}
			}
		}

		return has4pxArms;
	}

	/**
	 * Checks if a skin has 4px arms.<br>
	 * If any of the '4px-arm'-pixels are not 100% transparent, the skin is
	 * considered as 4px.
	 *
	 * @param img The Skin-Image to check
	 * 
	 * @return true, if its arms are 4px
	 * 
	 * @see #hasSkinDimensions(BufferedImage)
	 */
	public static boolean hasSteveArms(BufferedImage img) {
		boolean has4pxArms = false;

		for (Point p : SKIN_4PX_DIFF) {
			if (p.x < img.getWidth() && p.y < img.getHeight()) {
				if (new Color(img.getRGB(p.x, p.y), true).getAlpha() != 0) {
					has4pxArms = true;
					break;
				}
			}
		}

		if (!has4pxArms) {
			for (Point p : SKIN_4PX_OVERLAY_DIFF) {
				if (p.x < img.getWidth() && p.y < img.getHeight()) {
					if (new Color(img.getRGB(p.x, p.y), true).getAlpha() != 0) {
						has4pxArms = true;
						break;
					}
				}
			}
		}

		return has4pxArms;
	}

	/**
	 * Checks if a skin looks old (legacy/pre Minecraft 1.8)<br>
	 *
	 * @param img The Skin-Image to check
	 * 
	 * @return true, if it has an height of 32px
	 * 
	 * @see #hasSkinDimensions(BufferedImage)
	 */
	public static boolean isLegacySkin(BufferedImage img) {
		return img.getHeight() == 32;
	}

	/**
	 * Checks if the skin dimensions are official and supported by {@link SkinUtils}
	 *
	 * @param img The Skin-Image
	 * 
	 * @return true, if the dimensions are official and supported
	 */
	public static boolean hasSkinDimensions(BufferedImage img) {
		return img.getWidth() == 64 && (img.getHeight() == 64 || img.getHeight() == 32);
	}

	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(new File(
				"C:\\Users\\Christian\\Desktop\\a76e9ed560865b515a6de28ec55abaed61ed4c8ed8ba9eb952551406abd4b97.png"));

		String arr = "const Sprax2013=[";

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y), true);

				arr += c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "," + c.getAlpha() + ",";
			}
		}

		arr += "]";

		System.out.println(arr);
	}
}