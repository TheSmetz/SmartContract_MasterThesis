package encrypt;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;

public class ECC {

	private static PrivateKey privateKey;
	private static PublicKey publicKey;

	public ECC() {
	}

	public static void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); // 256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static PublicKey getPublicKey() {
		return publicKey;
	}

	public static byte[] encrypt(String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	public static boolean verify(PublicKey pubKey, byte[] signature, String data) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(pubKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String nonceGenerator() {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 15; i++) {
			stringBuilder.append(secureRandom.nextInt(10));
		}
		String randomNumber = stringBuilder.toString();
		return randomNumber;
	}

	public static byte[] hashString(String s) {
		MessageDigest digest;
		byte[] hash = {};
		try {
			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

}
