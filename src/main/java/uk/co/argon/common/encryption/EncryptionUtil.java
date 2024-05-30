package uk.co.argon.common.encryption;

import java.security.spec.KeySpec;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import uk.co.argon.common.util.ArgonConstants;


public class EncryptionUtil {
	protected static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("za.co.ominsure.synapse.common.encryption.EncryptionUtil");
	private SecretKeySpec secretKeySpec;
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public EncryptionUtil() {
		try {
			secretKeySpec = getSecretKeySpec();
			if (secretKeySpec != null) {
				encryptCipher = Cipher.getInstance(ArgonConstants.CRYPTO_TYPE);
				decryptCipher = Cipher.getInstance(ArgonConstants.CRYPTO_TYPE);
				decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
				encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, (e.getMessage() == null ? e.toString() : e.getMessage()), e);
		}
	}

	private SecretKeySpec getSecretKeySpec() {
		SecretKeySpec keySpec = null;
		try {
			String secretKey = "UmFtZXNoV2FzSGVyZSEh";
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, ArgonConstants.CRYPTO_KEY_SIZE);
			SecretKey generatedSecret = factory.generateSecret(spec);

			keySpec = new SecretKeySpec(generatedSecret.getEncoded(), ArgonConstants.CRYPTO_TYPE);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, (e.getMessage() == null ? e.toString() : e.getMessage()), e);
		}
		return keySpec;
	}

	public synchronized String encrypt(String unencryptedString) {
		try {
			return (encryptCipher == null ? null : Base64.encodeBase64String(encryptCipher.doFinal(unencryptedString.getBytes())));
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, (e.getMessage() == null ? e.toString() : e.getMessage()), e);
		}
		return null;
	}

	public synchronized String decrypt(String encryptedString) {
		try {
			if (decryptCipher == null) {
				return null;
			}

			byte[] encodedBase64Bytes = Base64.decodeBase64(encryptedString);
			return new String(decryptCipher.doFinal(encodedBase64Bytes));
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, (e.getMessage() == null ? e.toString() : e.getMessage()), e);
		}
		return null;
	}
}