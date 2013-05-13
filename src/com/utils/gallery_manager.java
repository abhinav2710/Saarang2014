package com.utils;

/*
 *	This class is the result of the unorganized Saarang and Shaastra
 * 	It maps event to there categories and gives each event name
 * 	size of event name hash is equal to number of events
 */

import java.util.HashMap;

public class gallery_manager {


	public static final String[] EventCategoryText = { "Fun", "Quiz", "Dance",
			"Lit", "lit2", "Arts", "Workshop", "Music" };
	
	public static final int noEvents = 66;


	/*
	 * THE MAP
	 */
	public static final Integer[][] eventids = {
			{ 1, 4, 7, 11, 20, 21, 25, 26, 27, 52, 65 },
			{ 2, 8, 12, 15, 19, 28, 29 }, { 3, 30, 46 },
			{ 5, 6, 16, 17, 18, 24, 31, 47, 48 },
			{ 9, 42, 43, 44, 49, 50, 57, 65 }, { 10, 32, 51, 61, 62, 63, 64 },
			{ 23, 13, 55 },
			{ 14, 22, 38, 39, 40, 41, 45, 53, 54, 56, 58, 59, 60 } };

	public static final HashMap<Integer, String> eventNameHash = new HashMap<Integer, String>();
	static {
		eventNameHash.put(1, " Soduku ");
		eventNameHash.put(2, " Pot Purri ");
		eventNameHash.put(3, " Treasure Hunt ");
		eventNameHash.put(4, " SpEnt Quiz ");
		eventNameHash.put(5, " India Quiz ");
		eventNameHash.put(6, " Travel & Living Quiz  ");
		eventNameHash.put(7, " Online Quiz ");
		eventNameHash.put(8, " Main Quiz ");
		eventNameHash.put(9, " Buzzer Quiz ");
		eventNameHash.put(10, " Design Fest ");
		eventNameHash.put(11, " Strokes ");
		eventNameHash.put(12, " Grey Matter ");
		eventNameHash.put(13, " Face Me ");
		eventNameHash.put(14, " Kaleidoscope ");
		eventNameHash.put(15, " Arty Styx ");
		eventNameHash.put(16, " Soap Carving ");
		eventNameHash.put(17, " Klay Kraft ");
		eventNameHash.put(18, " Expressions ");
		eventNameHash.put(19, " Dreams On Canvas ");
		eventNameHash.put(20, " Tactile Writing ");
		eventNameHash.put(21, " LecDems ");
		eventNameHash.put(22, " Saarang Workshop ");
		eventNameHash.put(23, " Creative Writing ");
		eventNameHash.put(24, " Cluedo ");
		eventNameHash.put(25, " SpellB ");
		eventNameHash.put(26, " Crossie ");
		eventNameHash.put(27, " Scrabble ");
		eventNameHash.put(28, " WTGW ");
		eventNameHash.put(29, " VOX ");
		eventNameHash.put(30, " Sonata ");
		eventNameHash.put(31, " Acoustyx ");
		eventNameHash.put(32, " ACapella ");
		eventNameHash.put(33, " PowerChords ");
		eventNameHash.put(34, " Decibels ");
		eventNameHash.put(35, " Tarang ");
		eventNameHash.put(36, " Alankar ");
		eventNameHash.put(37, " Un-Plucked ");
		eventNameHash.put(38, " Djukebox ");
		eventNameHash.put(39, " Dramatics ");
		eventNameHash.put(40, " StreetPlay ");
		eventNameHash.put(41, " Mad-Ads ");
		eventNameHash.put(42, " Radio Play ");
		eventNameHash.put(43, " Adventure Zone ");
		eventNameHash.put(44, " Laser Tag ");
		eventNameHash.put(45, " Classical Music ");
		eventNameHash.put(46, " Classical Music Insturments ");
		eventNameHash.put(47, " Classical Dance Solo ");
		eventNameHash.put(49, " Saarang Film Festival ");
		eventNameHash.put(50, " Photography Contests ");
		eventNameHash.put(51, " Ad Film Making ");
		eventNameHash.put(52, " Choreo ");
		eventNameHash.put(53, " Free Style ");
		eventNameHash.put(54, " Dance Workshop ");
		eventNameHash.put(55, " Just Duet ");
		eventNameHash.put(56, " $treet$ ");
		eventNameHash.put(57, " Debate ");
		eventNameHash.put(58, " JAM ");
		eventNameHash.put(59, " Elocution ");
		eventNameHash.put(60, " Carnival ");
		eventNameHash.put(61, " RoadShows ");
		eventNameHash.put(62, " Panache ");
	}

	public static final HashMap<Integer, Integer> EventCoordMaphash = new HashMap<Integer, Integer>();
	static {
		EventCoordMaphash.put(1,1);
		EventCoordMaphash.put(2,2);
		EventCoordMaphash.put(3,3);
		EventCoordMaphash.put(4,4);
		EventCoordMaphash.put(5,5);
		EventCoordMaphash.put(6,6);
		EventCoordMaphash.put(7,7);
		EventCoordMaphash.put(8,8);
		EventCoordMaphash.put(9,9);
		EventCoordMaphash.put(10,10);
		EventCoordMaphash.put(11,13);
		EventCoordMaphash.put(12,13);
		EventCoordMaphash.put(13,13);
		EventCoordMaphash.put(14,13);
		EventCoordMaphash.put(15,13);
		EventCoordMaphash.put(16,13);
		EventCoordMaphash.put(17,13);
		EventCoordMaphash.put(18,13);
		EventCoordMaphash.put(19,13);
		EventCoordMaphash.put(20,13);
		EventCoordMaphash.put(21,21);
		EventCoordMaphash.put(22,22);
		EventCoordMaphash.put(23,23);
		EventCoordMaphash.put(24,24);
		EventCoordMaphash.put(25,25);
		EventCoordMaphash.put(26,26);
		EventCoordMaphash.put(27,27);
		EventCoordMaphash.put(28,28);
		EventCoordMaphash.put(29,45);
		EventCoordMaphash.put(30,45);
		EventCoordMaphash.put(31,45);
		EventCoordMaphash.put(32,45);
		EventCoordMaphash.put(33,45);
		EventCoordMaphash.put(34,45);
		EventCoordMaphash.put(35,45);
		EventCoordMaphash.put(36,45);
		EventCoordMaphash.put(37,45);
		EventCoordMaphash.put(38,45);
		EventCoordMaphash.put(39,39);
		EventCoordMaphash.put(40,39);
		EventCoordMaphash.put(41,39);
		EventCoordMaphash.put(42,39);
		EventCoordMaphash.put(43,43);
		EventCoordMaphash.put(44,43);
		EventCoordMaphash.put(45,45);
		EventCoordMaphash.put(46,45);
		EventCoordMaphash.put(47,47);
		EventCoordMaphash.put(48,47);
		EventCoordMaphash.put(49,51);
		EventCoordMaphash.put(50,51);
		EventCoordMaphash.put(51,51);
		EventCoordMaphash.put(52,52);
		EventCoordMaphash.put(53,52);
		EventCoordMaphash.put(54,52);
		EventCoordMaphash.put(55,52);
		EventCoordMaphash.put(56,52);
		EventCoordMaphash.put(57,57);
		EventCoordMaphash.put(58,57);
		EventCoordMaphash.put(59,57);
		EventCoordMaphash.put(60,60);
		EventCoordMaphash.put(61,60);
		EventCoordMaphash.put(62,60);
	}
	public static final Integer[] CoordMap = {

	1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 8, 16, 17, 18, 19, 20, 21,
			22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 0, 0, 0, 0, 0, 22, 14,
			14, // 40
			31, 9, 9, 9, 3, 31, 47, 47, 26, 26, 10, 21, 0, 0, 22, 22, 3, 14, // 58
			26, 32, 32, 32, 10, 10, 10, 10, 0, 27 // 68

	};
}
