 The ESAPI has a small history of vulnerabilities within the cryptography component. Here is a quick validation list to make sure the Authenticated Encryption is working as expected.

**1. Library Version**

This issue is corrected in ESAPI version 2.1.0. Versions \<= 2.0.1 are vulnerable to a MAC bypass (CVE-2013-5679).

For Maven users, the plugin [versions](http://mojo.codehaus.org/versions-maven-plugin/) can be called using the following command. The effective version of ESAPI will be available in the output.

```
$ mvn versions:display-dependency-updates
```
  
Output:  

```
[...]
[INFO] The following dependencies in Dependencies have newer versions:
[INFO] org.slf4j:slf4j-api ................................... 1.6.4 -> 1.7.7
[INFO] org.owasp.esapi:esapi ................................. 2.0.1 -> 2.1.0
[...]
```

or by looking at the configuration directly.

```
<dependency>
    <groupId>org.owasp.esapi</groupId>
    <artifactId>esapi</artifactId>
    <version>2.1.0</version>
</dependency>
```

For Ant users, the jar used should be [esapi-2.1.0.jar](http://repo1.maven.org/maven2/org/owasp/esapi/esapi/2.1.0/esapi-2.1.0.jar).

**2. Configuration:**

The library version 2.1.0 is still vulnerable to key size being changed in the ciphertext definition (CVE-2013-5960). Some precautions need to be taken.

**The cryptographic configuration of ESAPI can also be vulnerable if any of these elements are present:**  
**Insecure configuration:**  

```
Encryptor.CipherText.useMAC=false

Encryptor.EncryptionAlgorithm=AES
Encryptor.CipherTransformation=AES/CBC/PKCS5Padding

Encryptor.cipher_modes.additional_allowed=CBC
```

**Secure configuration:**  

```
#Needed
Encryptor.CipherText.useMAC=true

#Needed to have a solid auth. encryption
Encryptor.EncryptionAlgorithm=AES
Encryptor.CipherTransformation=AES/GCM/NoPadding

#CBC mode should be removed to avoid padding oracle
Encryptor.cipher_modes.additional_allowed=
```

  

**References**  
[ESAPI Security bulletin 1 (CVE-2013-5679)](http://owasp-esapi-java.googlecode.com/svn/trunk/documentation/ESAPI-security-bulletin1.pdf)  
[Vulnerability Summary for CVE-2013-5679](http://nvd.nist.gov/view/vuln/detail?vulnId=CVE-2013-5679)  
[Synactiv: Bypassing HMAC validation in OWASP ESAPI symmetric encryption](http://www.synacktiv.com/ressources/synacktiv_owasp_esapi_hmac_bypass.pdf)  
[CWE-310: Cryptographic Issues](http://cwe.mitre.org/data/definitions/310.html)  
[ESAPI-dev mailing list: Status of CVE-2013-5960](http://lists.owasp.org/pipermail/esapi-dev/2015-March/002533.html)

