 Implementing a custom MessageDigest is error-prone.

[NIST](http://csrc.nist.gov/groups/ST/toolkit/secure_hashing.html) recommends the use of SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, or SHA-512/256.

> " **SHA-1 for digital signature generation:**  
> SHA-1 may only be used for digital signature generation where specifically allowed by NIST protocol-specific guidance. For all other applications, SHA-1 shall not be used for digital signature generation.  
> **SHA-1 for digital signature verification:**  
> For digital signature verification, SHA-1 is allowed for legacy-use.  
> [...]  
> **SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, and SHA-512/256** :  
> The use of these hash functions is acceptable for all hash function applications."  
> - [NIST: Transitions: Recommendation for Transitioning the Use of Cryptographic Algorithms and Key Lengths p.15](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar1.pdf)

**Vulnerable Code:**

```
MyProprietaryMessageDigest extends MessageDigest {
    @Override
    protected byte[] engineDigest() {
        [...]
        //Creativity is a bad idea
        return [...];
    }
}
```

Upgrade your implementation to use one of the approved algorithms. Use an algorithm that is sufficiently strong for your specific security needs.

**Example Solution:**

```
MessageDigest sha256Digest = MessageDigest.getInstance("SHA256");
sha256Digest.update(password.getBytes());
```
  

**References**  
[NIST Approved Hashing Algorithms](http://csrc.nist.gov/groups/ST/toolkit/secure_hashing.html)  
[CWE-327: Use of a Broken or Risky Cryptographic Algorithm](http://cwe.mitre.org/data/definitions/327.html)

