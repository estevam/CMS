package ca.est.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* @author Estevam.Meneses
*/
public class EncryptionUtil {

	private static final Logger log = LoggerFactory.getLogger(EncryptionUtil.class);

	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
	private static final String ALGORITHM = "AES";
    private static final String ENCRYPTION_PASS ="ENCRYPTION_PASS";
	private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
	private static final int IV_LENGTH_BYTE = 64;  //64 
	private static final int SALT_LENGTH_BYTE = 16; //16

	/**
	 * Encrypt string 		
	 * @param msg
	 * @return
	 */
	public static synchronized String encrypt(String msg) {
		// Salt is random data very often used in cryptography as additional input to a hash function.
		byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);
		// Initialization Vector (IV)
		byte[] initVector = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE); 

		// secret key from password

		byte[] cipherTextWithIvSalt = null;
		try {
			
			//final String ENCRYPT_PASS = System.getenv(ENCRYPTION_PASS); // AES/GCM/NoPadding;


			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
			
			SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(ENCRYPTION_PASS.toCharArray(), salt);
			AlgorithmParameterSpec algorithmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, initVector);
			cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, algorithmParameterSpec);
			byte[] cipherText = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
			cipherTextWithIvSalt = ByteBuffer.allocate(initVector.length + salt.length + cipherText.length).put(initVector).put(salt)
					.put(cipherText).array();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
			log.error("Error on encrypt : ", e);
		}
		return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

	}

	/**
	 * Decrypt String
	 * @param msg
	 * @return 
	 */
	public static synchronized String decrypt(String msg) {
		
		byte[] decode = Base64.getDecoder().decode(msg.getBytes(StandardCharsets.UTF_8));
		
		// get back the iv and salt from the cipher text
		ByteBuffer byteBuffer = ByteBuffer.wrap(decode);
				
		// Initialization Vector (IV)
		byte[] initVector = new byte[IV_LENGTH_BYTE]; 
		byteBuffer.get(initVector);
				
		// Salt is random data very often used in cryptography as additional input to a hash function.
		byte[] salt = new byte[SALT_LENGTH_BYTE];
		byteBuffer.get(salt);
				
		byte[] cipherText = new byte[byteBuffer.remaining()];
		byteBuffer.get(cipherText);
					
		SecretKey aesKeyFromPassword;
		try {

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
			
			//final String ENCRYPT_PASS = System.getenv(ENCRYPTION_PASS); // AES/GCM/NoPadding;
			
			aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(ENCRYPTION_PASS.toCharArray(), salt);
			AlgorithmParameterSpec algorithmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, initVector);
			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, algorithmParameterSpec);
			
			byte[] plainText = cipher.doFinal(cipherText);
			return new String(plainText, StandardCharsets.UTF_8);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException  | NoSuchPaddingException | IllegalStateException e) {
			log.error("Error on decrypt ", e);
		}
		return null;
	}
	
	/**
	 * 
	 * @author Estevam.Meneses
	 *
	 */
     static class CryptoUtils {

		private static final int ITERATION_COUNT = 2;
		private static final int KEY_LENGTH = 128;
		private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
		
		public static byte[] getRandomNonce(int numBytes) {
			byte[] nonce = new byte[numBytes];
			new SecureRandom().nextBytes(nonce);
			return nonce;
		}

		/**
		 * Password derived AES 256 bits secret key
		 * @param password
		 * @param salt
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 */
		public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
				throws NoSuchAlgorithmException, InvalidKeySpecException {

			SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
			return  new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
		}
	}
}
