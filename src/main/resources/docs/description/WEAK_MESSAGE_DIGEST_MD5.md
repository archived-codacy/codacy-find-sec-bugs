 The algorithms MD2, MD4 and MD5 are not a recommended MessageDigest. **PBKDF2** should be use to hash password for example.> "The security of the MD5 hash function is severely compromised. A collision attack exists that can find collisions within seconds on a computer with a 2.6 GHz Pentium 4 processor (complexity of 224.1).[1] Further, there is also a chosen-prefix collision attack that can produce a collision for two inputs with specified prefixes within hours, using off-the-shelf computing hardware (complexity 239).[2]"  
> - [Wikipedia: MD5 - Security](https://en.wikipedia.org/wiki/MD5#Security)> " **SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, and SHA-512/256** :  
> The use of these hash functions is acceptable for all hash function applications."  
> - [NIST: Transitions: Recommendation for Transitioning the Use of Cryptographic Algorithms and Key Lengths p.15](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar1.pdf)> "The main idea of a PBKDF is to slow dictionary or brute force attacks on the passwords by increasing the time needed to test each password. An attacker with a list of likely passwords can evaluate the PBKDF using the known iteration counter and the salt. Since an attacker has to spend a significant amount of computing time for each try, it becomes harder to apply the dictionary or brute force attacks."  
> - [NIST: Recommendation for Password-Based Key Derivation p.12](http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf)  

**Vulnerable Code:**

```
MessageDigest md5Digest = MessageDigest.getInstance("MD5");
    md5Digest.update(password.getBytes());
    byte[] hashValue = md5Digest.digest();
```
  

```
byte[] hashValue = DigestUtils.getMd5Digest().digest(password.getBytes());
```
  

**Solution (Using bouncy castle):**

```
public static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
    gen.init(password.getBytes("UTF-8"), salt.getBytes(), 4096);
    return ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
}
```
  
**Solution (Java 8 and later):**  

```
public static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 4096, 256 * 8);
    SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    return f.generateSecret(spec).getEncoded();
}
```
  

**References**  
[1] [On Collisions for MD5](http://www.win.tue.nl/hashclash/On%20Collisions%20for%20MD5%20-%20M.M.J.%20Stevens.pdf): Master Thesis by M.M.J. Stevens  
[2] [Chosen-prefix collisions for MD5 and applications](http://homepages.cwi.nl/~stevens/papers/stJOC%20-%20Chosen-Prefix%20Collisions%20for%20MD5%20and%20Applications.pdf): Paper written by Marc Stevens  
[Wikipedia: MD5](https://en.wikipedia.org/wiki/MD5)  
[NIST: Transitions: Recommendation for Transitioning the Use of Cryptographic Algorithms and Key Lengths](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar1.pdf)  
[NIST: Recommendation for Password-Based Key Derivation](http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf)  
[Stackoverflow: Reliable implementation of PBKDF2-HMAC-SHA256 for Java](http://stackoverflow.com/questions/22580853/reliable-implementation-of-pbkdf2-hmac-sha256-for-java)  
[CWE-327: Use of a Broken or Risky Cryptographic Algorithm](http://cwe.mitre.org/data/definitions/327.html)

