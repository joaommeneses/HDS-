import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;

public class RSAEncrypt {

    // Directory where the keys will be stored
    private static final String KEY_DIRECTORY = "resources/";

    public static void main(String[] args) {

        // Check args
        if (args.length != 2) {
            System.err.println("Usage: RSAKeyGenerator <priv-key-file> <pub-key-file>");
            return;
        }

        final String privKeyPath = KEY_DIRECTORY + args[0];
        final String pubKeyPath = KEY_DIRECTORY + args[1];

        try {
            System.out.println("Generate and save keys");
            writeKeys(privKeyPath, pubKeyPath);
            
            System.out.println("Load keys to verify");
            PrivateKey privKey = readPrivateKey(privKeyPath);
            PublicKey pubKey = readPublicKey(pubKeyPath);
            System.out.println("Private Key: " + privKey.toString());
            System.out.println("Public Key: " + pubKey.toString());

            System.out.println("Done.");
        } catch (Exception e) {
            System.err.println("Error during key handling: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeKeys(String privKeyPath, String pubKeyPath) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(4096);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Ensure the directory exists before attempting to write the files
        Files.createDirectories(Paths.get(privKeyPath).getParent());

        try (FileOutputStream fos = new FileOutputStream(privKeyPath)) {
            fos.write(keyPair.getPrivate().getEncoded());
        }

        try (FileOutputStream fos = new FileOutputStream(pubKeyPath)) {
            fos.write(keyPair.getPublic().getEncoded());
        }

        System.out.println("RSA keys generated and saved.");
    }

    public static PrivateKey readPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey readPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
