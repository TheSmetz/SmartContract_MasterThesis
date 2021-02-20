package encrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;

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

	private static <T> byte[] toByte(T object) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		byte[] res = {};
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			res = bos.toByteArray();
		} catch (Exception e) {

		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return res;
	}

	public static <T> byte[] hash(T object) {
		byte[] digest = {};
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(toByte(object));
			digest = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest;
	}

	public static PublicKey getPublicKeyFromBytes(byte[] pubKey) {
		X509EncodedKeySpec ks = new X509EncodedKeySpec(pubKey);
        KeyFactory kf;
        try {
            kf = java.security.KeyFactory.getInstance("ECDSA","BC");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithm error" + e);
            return null;
        } catch (NoSuchProviderException e) {
			System.out.println("No provider" + e);
			return null;
		}

        ECPublicKey remotePublicKey;

        try {
            remotePublicKey = (ECPublicKey)kf.generatePublic(ks);
        } catch (InvalidKeySpecException e) {
            System.out.println("Received invalid key specification from client "+e);
            return null;
        } catch (ClassCastException e) {
            System.out.println("Received valid X.509 key from client but it was not EC Public Key material "+e);
            return null;
        }
		return remotePublicKey;
	}
}
