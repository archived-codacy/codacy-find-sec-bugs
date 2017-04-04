 **Vulnerable Code:**  

```
SSLContext.getInstance("SSL");
```

**Solution:**  
Upgrade your implementation to the following, and configure https.protocols JVM option to include TLSv1.2:

```
SSLContext.getInstance("TLS");
```

  

**References**  
[Diagnosing TLS, SSL, and HTTPS](https://blogs.oracle.com/java-platform-group/entry/diagnosing_tls_ssl_and_https)

