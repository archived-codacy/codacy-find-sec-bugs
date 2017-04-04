 The highlighted API is used to execute a system command. If unfiltered input is passed to this API, it can lead to arbitrary command execution.  

**Vulnerable Code:**

```
def executeCommand(value:String) = Action {
    val result = value.!
    Ok("Result:\n"+result)
}
```

**References**  
[OWASP: Command Injection](https://www.owasp.org/index.php/Command_Injection)  
[OWASP: Top 10 2013-A1-Injection](https://www.owasp.org/index.php/Top_10_2013-A1-Injection)  
[CWE-78: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')](http://cwe.mitre.org/data/definitions/78.html)

