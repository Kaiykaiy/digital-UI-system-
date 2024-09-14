import java.security.KeyPairGenerator
import java.util.Base64

fun generateKeys(): Pair<String, String> {
    val keyGen = KeyPairGenerator.getInstance("EC")
    keyGen.initialize(256)
    val keyPair = keyGen.generateKeyPair()

    val privateKey = Base64.getEncoder().encodeToString(keyPair.private.encoded)
    val publicKey = Base64.getEncoder().encodeToString(keyPair.public.encoded)

    return Pair(privateKey, publicKey)
}

fun main() {
    val (privateKey, publicKey) = generateKeys()
    println("Private Key: $privateKey")
    println("Public Key: $publicKey")
}import java.security.MessageDigest

fun sha256(input: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(input)
}

fun ripemd160(input: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("RIPEMD160")
    return digest.digest(input)
}

fun generateAddress(publicKey: String): String {
    val sha256Hash = sha256(Base64.getDecoder().decode(publicKey))
    val ripemd160Hash = ripemd160(sha256Hash)
    return Base64.getEncoder().encodeToString(ripemd160Hash)
}

fun main() {
    val (_, publicKey) = generateKeys()
    val address = generateAddress(publicKey)
    println("Wallet Address: $address")
}import java.security.Signature
import java.util.Base64

fun signTransaction(privateKey: String, transactionData: String): String {
    val keyFactory = java.security.KeyFactory.getInstance("EC")
    val keySpec = java.security.spec.PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey))
    val privateKey = keyFactory.generatePrivate(keySpec)

    val signature = Signature.getInstance("SHA256withECDSA")
    signature.initSign(privateKey)
    signature.update(transactionData.toByteArray())

    return Base64.getEncoder().encodeToString(signature.sign())
}

fun main() {
    val (privateKey, _) = generateKeys()
    val transactionData = "Sample transaction"
    val signature = signTransaction(privateKey, transactionData)
    println("Transaction Signature: $signature")
}fun verifySignature(publicKey: String, signatureData: String, transactionData: String): Boolean {
    val keyFactory = java.security.KeyFactory.getInstance("EC")
    val keySpec = java.security.spec.X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))
    val publicKey = keyFactory.generatePublic(keySpec)

    val signature = Signature.getInstance("SHA256withECDSA")
    signature.initVerify(publicKey)
    signature.update(transactionData.toByteArray())

    return signature.verify(Base64.getDecoder().decode(signatureData))
}

fun main() {
    val (privateKey, publicKey) = generateKeys()
    val transactionData = "Sample transaction"
    val signature = signTransaction(privateKey, transactionData)

    val isValid = verifySignature(publicKey, signature, transactionData)
    println("Is transaction valid? $isValid")
}
