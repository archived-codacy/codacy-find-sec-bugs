 Server identity verification is disabled when making SSL connections. Some email libraries that enable SSL connections do not verify the server certificate by default. This is equivalent to trusting all certificates. When trying to connect to the server, this application would readily accept a certificate issued to "hackedserver.com". The application would now potentially leak sensitive user information on a broken SSL connection to the hacked server.

**Vulnerable Code:**

```
...
Email email = new SimpleEmail();
email.setHostName("smtp.servermail.com");
email.setSmtpPort(465);
email.setAuthenticator(new DefaultAuthenticator(username, password));
email.setSSLOnConnect(true);
email.setFrom("user@gmail.com");
email.setSubject("TestMail");
email.setMsg("This is a test mail ... :-)");
email.addTo("foo@bar.com");
email.send();
...
```

**Solution:**  
Please add the following check to verify the server cerfiticate:

```
email.setSSLCheckServerIdentity(true);
```
  

**References**  
[CWE-297: Improper Validation of Certificate with Host Mismatch](https://cwe.mitre.org/data/definitions/297.html)

 