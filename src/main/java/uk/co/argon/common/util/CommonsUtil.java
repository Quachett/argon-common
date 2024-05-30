package uk.co.argon.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import uk.co.argon.common.fileio.FileManager;

public class CommonsUtil {
    public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String PIPE = "||";
    public static final String NEW = "NEW";
    public static final String YES = "Yes";
    public static final String NO = "No";
    private static final String DIR = "C:/Users/lloyd/OneDrive/Documents/Import_files/";
    private static final String DICTIONARY = "dictionary.txt";
    private static List<String> dictionary;
	
    public static String createAttributes(List<String> attributes) {
        //return attributes.stream().collect(Collectors.joining(SynapseConstants.COMMA));
    	return StringUtils.join(attributes,ArgonConstants.COMMA + " ");
    }
    
    public static String getLineOfQs(int num) {
        return Joiner.on(", ").join(Iterables.limit(Iterables.cycle("?"), num));
    }
    
    public static String arrayRangeToString(Object[] obj, int start, int end) {
    	int i;
    	String result = "";
    	synchronized (CommonsUtil.class) {
    		for(i=0;i<end;i++) {
        		if(i<end-1)
        			result += obj[i] + ", ";
        		else
        			result += obj[i];
        	}
		}
    	
        return "[" + result +"]";
    }
   
    public static <T> T[] swap(T[] t, int i, int j) {
		T temp = t[j];
		t[j] = t[i];
		t[i] = temp;
		
		return t;
	}
   
    public static <T> T[][] swap(T[][] t, int x, int y, int i, int j) {
		T temp = t[x][y];
		t[x][y] = t[i][j];
		t[i][j] = temp;
		
		return t;
	}
   
    public static <T> List<T> swap(List<T> t, int i, int j) {
		T temp = t.get(j);
		t.set(j, t.get(i));
		t.set(i, temp);
		
		return t;
	}
   
    public static <T> List<List<T>> swap(List<List<T>> t, int x, int y, int i, int j) {
		T temp = t.get(y).get(x);
		t.get(y).set(x, t.get(j).get(i));
		t.get(j).set(i, temp);
		
		return t;
	}
    
    public static Integer[] getIntegerArray(int size, int bound) {
		Integer[] array = new Integer[size];
		synchronized(CommonsUtil.class) {
			for(int i=0; i<size; i++)
				array[i] = new Random().nextInt(bound);
		}
		return array;
	}
    
    public static List<Integer> getIntegerList(int size, int bound, int negative) {
		List<Integer> list = new ArrayList<>();
		synchronized(CommonsUtil.class) {
			for(int i=0; i<size; i++)
				list.add(new Random().nextInt(bound) - negative);
		}
		return list;
	}
    
    public static String[] getStringArray(int size) throws IOException {
    	String[] array = new String[size];
    	if(dictionary != null && dictionary.isEmpty())
    		dictionary = FileManager.getInstance().fileToList(DIR, DICTIONARY);
    	for(int i=0; i<size; i++)
    		array[i] = dictionary.get(new Random().nextInt(dictionary.size()-1));
    	return array;
    }
	
	public static String randomStringFromRegex(String regex, int length) {
		StringBuilder sb = new StringBuilder();
		int bound = 94, i = 0;
		
		while(i<length) {
			String c = String.valueOf(((char) (32 + new Random().nextInt(bound))));
			if(Pattern.matches(regex, c)) {
				sb.append(c);
				i++;
			}
		}
		
		return sb.toString();
	}
}
