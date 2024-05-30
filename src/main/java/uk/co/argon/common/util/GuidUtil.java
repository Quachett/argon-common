package uk.co.argon.common.util;

/**
 * 
 * @author Johan Prins
 * 
 */
public class GuidUtil {
	public static String convertGuidToDbFormat(String guid) {
		StringBuilder sb = new StringBuilder();
		sb.append(guid.substring(7, 9));
		sb.append(guid.substring(5, 7));
		sb.append(guid.substring(3, 5));
		sb.append(guid.substring(1, 3));
		sb.append(guid.substring(12, 14));
		sb.append(guid.substring(10, 12));
		sb.append(guid.substring(17, 19));
		sb.append(guid.substring(15, 17));
		sb.append(guid.substring(20, 24));
		sb.append(guid.substring(25, 37));
		return sb.toString();
	}

	public static String convertGuidToWorkplaceFormat(String guid) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append(guid.substring(6, 8));
		sb.append(guid.substring(4, 6));
		sb.append(guid.substring(2, 4));
		sb.append(guid.substring(0, 2));
		sb.append('-');
		sb.append(guid.substring(10, 12));
		sb.append(guid.substring(8, 10));
		sb.append('-');
		sb.append(guid.substring(14, 16));
		sb.append(guid.substring(12, 14));
		sb.append('-');
		sb.append(guid.substring(16, 20));
		sb.append('-');
		sb.append(guid.substring(20));
		sb.append('}');
		return sb.toString();
	}
	
	public static String generateGUID() {
		StringBuilder b = new StringBuilder();
		b.append('{').append(java.util.UUID.randomUUID().toString().toUpperCase()).append('}');
		return b.toString();
	}

	public static boolean validateWorkplaceGuid(String guid) {
		if (guid.length() != 38 && guid.length() != 32)
			return false;
		return true;
	}
}
