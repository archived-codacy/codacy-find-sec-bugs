 Passwords should not be kept in the source code. The source code can be widely shared in an enterprise environment, and is certainly shared in open source. To be managed safely, passwords and secret keys should be stored in separate configuration files or keystores. (Hard coded keys are reported separately by _Hard Coded Key_ pattern)

**Vulnerable Code:**

```
private String SECRET_PASSWORD = "letMeIn!";

Properties props = new Properties();
props.put(Context.SECURITY_CREDENTIALS, "p@ssw0rd");
```
  

**References**  
[CWE-259: Use of Hard-coded Password](http://cwe.mitre.org/data/definitions/259.html)

