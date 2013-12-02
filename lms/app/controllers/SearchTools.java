package controllers;

public class SearchTools {
	private static String firstOrLastRegex = "[a-zA-Z]*";
	private static String firstThenLastRegex = "[a-zA-Z]* +[a-zA-Z]*";
	private static String lastThenFirstRegex = "[a-zA-Z]* *, *[a-zA-Z]*";
	private static String firstAndLastRegex = "[a-zA-Z]+[+][a-zA-Z]+";
	private static String numberRegex = "[0-9]+";
	
	public static boolean isNumber(String name){
		return name.matches(numberRegex);
	}
	
	public static boolean isFirstOrLast(String name){
		return name.matches(firstOrLastRegex);
	}
	
	public static boolean isFirstThenLast(String name){
		return name.matches(firstThenLastRegex);
	}
	
	public static boolean isLastThenFirst(String name){
		return name.matches(lastThenFirstRegex);
	}
	
	public static boolean isFormattedFirstAndLast(String name){
		
		return name.matches(firstAndLastRegex);
	}

	public static String[] getFormattedFirstAndLast(String name){
		return name.split("[+]");
	}
	
	public static String[] getFirstAndLast(String name){
		String innerTrimmed = name.replaceAll(" +", " ");
		return innerTrimmed.split(" ");
	}
	
	public static String[] getLastAndFirst(String name){
		String trimmed = name.replaceAll(" ", "");
		return trimmed.split(",");
	}
	
}
