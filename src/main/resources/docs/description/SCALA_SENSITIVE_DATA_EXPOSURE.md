 Applications can unintentionally leak information about their configuration, internal workings, or violate privacy through a variety of application problems. [1] Pages that provide different responses based on the validity of the data can lead to Information Leakage; specifically when data deemed confidential is being revealed as a result of the web application's design. [2]

Examples of sensitive data includes (but is not limited to): API keys, passwords, product versions or environment configurations.

**Code at risk:**

```
def doGet(value:String) = Action {
  val configElement = configuration.underlying.getString(value)

  Ok("Hello "+ configElement +" !")
}
```

Application configuration elements should not be sent in the response content and users should not be allowed to control which configuration elements will be used by the code.

**References**  
[OWASP: Top 10 2013-A6-Sensitive Data Exposure](https://www.owasp.org/index.php/Top_10_2013-A6-Sensitive_Data_Exposure)  
[1] [OWASP: Top 10 2007-Information Leakage and Improper Error Handling](https://www.owasp.org/index.php/Top_10_2007-Information_Leakage_and_Improper_Error_Handling)  
[2] [WASC-13: Information Leakage](http://projects.webappsec.org/w/page/13246936/Information%20Leakage)  
[CWE-200: Information Exposure](https://cwe.mitre.org/data/definitions/200.html)  
 