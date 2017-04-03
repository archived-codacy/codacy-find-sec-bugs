 When data from untusted source are put into a logger and not neutralized correctly, an attacker could forge log entries or include malicious content. Inserted false entries could be used to skew statistics, distract the administrator or even to implicate another party in the commission of a malicious act. If the log file is processed automatically, the attacker can render the file unusable by corrupting the format of the file or injecting unexpected characters. An attacker may also inject code or other commands into the log file and take advantage of a vulnerability in the log processing utility (e.g. command injection or XSS).  

**Code at risk:**

```
String val = request.getParameter("val");
try {
    number = Integer.parseInt(val);
} catch (NumberFormatException) {
    log.info("Failed to parse val = " + val);
}
```
  

**References**  
[CWE-117: Improper Output Neutralization for Logs](http://cwe.mitre.org/data/definitions/117.html)  
[CWE-93: Improper Neutralization of CRLF Sequences ('CRLF Injection')](http://cwe.mitre.org/data/definitions/93.html)

