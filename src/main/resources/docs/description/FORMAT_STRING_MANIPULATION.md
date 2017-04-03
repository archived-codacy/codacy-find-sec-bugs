 Allowing user input to control format parameters could enable an attacker to cause exceptions to be thrown or leak information.  
Attackers may be able to modify the format string argument, such that an exception is thrown. If this exception is left uncaught, it may crash the application. Alternatively, if sensitive information is used within the unused arguments, attackers may change the format string to reveal this information.  
The example code below lets the user specify the decimal points to which it shows the balance. The user can in fact specify anything causing an exception to be thrown which could lead to application failure. Even more critical within this example, if an attacker can specify the user input "2f %3$s %4$.2", the format string would be "The customer: %s %s has the balance %4$.2f %3$s %4$.2". This would then lead to the sensitive accountNo to be included within the resulting string.

**Vulnerable Code:**

```
Formatter formatter = new Formatter(Locale.US);
String format = "The customer: %s %s has the balance %4$." + userInput + "f";
formatter.format(format, firstName, lastName, accountNo, balance);
```

**Solution:**  
Avoid using user controlled values in the format string argument.

  

**References**  
[CWE-134: Use of Externally-Controlled Format String](http://cwe.mitre.org/data/definitions/134.html)

 