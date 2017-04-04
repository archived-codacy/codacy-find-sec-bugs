 The NIST recommends the use of 2048 bits and higher keys for the RSA algorithm.> "Digital Signature Verification | RSA: 1024 ≤ len(n) \< 2048 | Legacy-use"  
> "Digital Signature Verification | RSA: len(n) ≥ 2048 | Acceptable"  
> - [NIST: Recommendation for Transitioning the Use of Cryptographic Algorithms and Key Lengths p.7](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar1.pdf)

**Vulnerable Code:**

```
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(512);
```

**Solution:**  
The KeyPairGenerator creation should be as follows with at least 2048 bit key size.

```
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(2048);
```
  

**References**  
[NIST: Latest publication on key management](http://csrc.nist.gov/groups/ST/toolkit/key_management.html)  
[NIST: Recommendation for Transitioning the Use of Cryptographic Algorithms and Key Lengths p.7](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar1.pdf)  
[RSA Laboratories: 3.1.5 How large a key should be used in the RSA cryptosystem?](http://www.emc.com/emc-plus/rsa-labs/standards-initiatives/how-large-a-key-should-be-used.htm)  
[Wikipedia: Asymmetric algorithm key lengths](http://en.wikipedia.org/wiki/Key_size#Asymmetric%5Falgorithm%5Fkey%5Flengths)  
[CWE-326: Inadequate Encryption Strength](http://cwe.mitre.org/data/definitions/326.html)  
[Keylength.com (BlueKrypt): Aggregate key length recommendations.](http://www.keylength.com/en/compare/)

