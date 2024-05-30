package uk.co.argon.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
 
/**
 *
 * @author Johan Prins
 *
 */
public class DateUtil {
	private DateFormat dateFormat;
	private DateFormat miliDateFormat;
	private DateFormat simpleDateFormat;
	private DateFormat displayDateFormat;
	private DateFormat nonStandardDisplayDateFormat;
	
	public static void main(String[] args) {
		String d="2021-12-09T01:46:49+0200";
		try {
			System.out.println(new DateUtil().parseDate(d));
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public DateUtil() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		miliDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		displayDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		nonStandardDisplayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	}

	public Date parseDate(String value) throws ParseException {
		if (value != null) {
			// Bug fix: TIA sends dates in the following format: 2014-09-03T09:02:00Z
			// This fix will replace the Z with +0200
			if (value.length() == 20 && value.toUpperCase().endsWith("Z")) {
				value = value.substring(0, 19);
				value += "+0200";
				return dateFormat.parse(value);
			}
			// End of fix

			if (value.length() == 10) {
				return simpleDateFormat.parse(value);
			}
			else if (value.length() == 24 && value.endsWith("Z")) {
				return miliDateFormat.parse(value);
			}
			else {
				return dateFormat.parse(value);
			}
		}
		return null;
	}

	/**
	 * Formats a Date object to <b>yyyy-MM-dd</b> string format.
	 *
	 * @param date
	 *            The date to be formatted
	 * @return the date as a formatted string or an empty ("") string.
	 */
	public String formatDate(Date date) {
		if (date != null) {
			return simpleDateFormat.format(date);
		}
		return "";
	}

	/**
	 * Formats a Date object to <b>yyyy-MM-dd'T'HH:mm:ssZ</b> string format.
	 *
	 * @param date
	 *            The date to be formatted
	 * @return the date as a formatted string or an empty ("") string.
	 */
	public String formatDateTime(Date date) {
		if (date != null) {
			return dateFormat.format(date);
		}
		return "";
	}
	
	public String formatDateTimeMilli(Timestamp ts) {
    	if(ts!=null){
    		   return ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    	}
    	return null;
    }

	/**
	 * Parses a date string from <b>yyyy-MM-dd'T'HH:mm:ssZ</b> to <b>yyyy-MM-dd HH:mm</b> string format.
	 *
	 * @param value
	 *            A date in <b>yyyy-MM-dd'T'HH:mm:ssZ</b> string format.
	 * @return The date in <b>yyyy-MM-dd HH:mm</b> string format.
	 * @throws ParseException
	 *             Thrown if the date cannot be parsed.
	 */
	public String convertToDisplayDateFormat(String value) throws ParseException {
		if (value != null) {
			Date date = dateFormat.parse(value);
			return displayDateFormat.format(date);
		}
		return value;
	}

	/**
	 * Takes a Date object and converts it to a String object whose format is <b>yyyy-MM-dd HH:mm</b>
	 *
	 * @param date
	 *            The date object to be converted
	 * @return The date in <b>yyyy-MM-dd HH:mm</b> string format.
	 */
	public String formatDisplayDateFormat(Date date) {
		if (date != null) {
			return displayDateFormat.format(date);
		}
		return "";
	}
	
	/**
	 * Takes a Date object and converts it to a String object whose format is <b>dd-MMM-yyy</b>
	 *
	 * @param date
	 *            The date object to be converted
	 * @return The date in <b>dd-MM-yyy</b> string format.
	 */
	public String formatNonStandardDisplayDateFormat(Date date) {
		if (date != null) {
			return nonStandardDisplayDateFormat.format(date);
		}
		return "";
	}
    
	
	/**
	 * Takes a String, or null, and return a SQL timestamp
	 * @param date
	 * @return SQL Timestamp
	 * @throws ParseException
	 */
    public Timestamp getTimestamp(String date) throws ParseException {
        Date formatedDate = null;
        
        if(StringUtils.isNotBlank(date))
            formatedDate = dateFormat.parse(date);
        else
            formatedDate = new Date();
        
        return new Timestamp(formatedDate.getTime());
    }
}
