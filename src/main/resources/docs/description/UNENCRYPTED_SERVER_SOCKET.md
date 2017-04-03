 The communication channel used is not encrypted. The traffic could be read by an attacker intercepting the network traffic.

**Vulnerable Code:**  
Plain server socket (Cleartext communication):

```
ServerSocket soc = new ServerSocket(1234);
```

**Solution:**  
SSL Server Socket (Secure communication):

```
ServerSocket soc = SSLServerSocketFactory.getDefault().createServerSocket(1234);
```

Beyond using an SSL server socket, you need to make sure your use of SSLServerSocketFactory does all the appropriate certificate validation checks to make sure you are not subject to man-in-the-middle attacks. Please read the OWASP Transport Layer Protection Cheat Sheet for details on how to do this correctly.

  

**References**  
[OWASP: Top 10 2010-A9-Insufficient Transport Layer Protection](https://www.owasp.org/index.php/Top_10_2010-A9)  
[OWASP: Top 10 2013-A6-Sensitive Data Exposure](https://www.owasp.org/index.php/Top_10_2013-A6-Sensitive_Data_Exposure)  
[OWASP: Transport Layer Protection Cheat Sheet](https://www.owasp.org/index.php/Transport_Layer_Protection_Cheat_Sheet)  
[WASC-04: Insufficient Transport Layer Protection](http://projects.webappsec.org/w/page/13246945/Insufficient%20Transport%20Layer%20Protection)  
[CWE-319: Cleartext Transmission of Sensitive Information](http://cwe.mitre.org/data/definitions/319.html)

