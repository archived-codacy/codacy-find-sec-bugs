 Enabling JavaScript for the WebView means that it is now susceptible to XSS. The page render should be inspected for potential reflected XSS, stored XSS and DOM XSS.  

```
WebView myWebView = (WebView) findViewById(R.id.webView);
WebSettings webSettings = myWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
```

**Code at risk:**  
Enabling JavaScript is not a bad practice. It just means that the backend code need to be audited for potential XSS. The XSS can also be introduce client-side with DOM XSS.

```
function updateDescription(newDescription) {
    $("#userDescription").html("<p>"+newDescription+"</p>");
}
```
  

**References**  
[Issue: Using setJavaScriptEnabled can introduce XSS vulnerabilities](http://www.technotalkative.com/issue-using-setjavascriptenabled-can-introduce-xss-vulnerabilities-application-review-carefully/)  
[Android Official Doc: WebView](http://developer.android.com/guide/webapps/webview.html#UsingJavaScript)  
[WASC-8: Cross Site Scripting](http://projects.webappsec.org/w/page/13246920/Cross%20Site%20Scripting)  
[OWASP: XSS Prevention Cheat Sheet](https://www.owasp.org/index.php/XSS_%28Cross_Site_Scripting%29_Prevention_Cheat_Sheet)  
[OWASP: Top 10 2013-A3: Cross-Site Scripting (XSS)](https://www.owasp.org/index.php/Top_10_2013-A3-Cross-Site_Scripting_%28XSS%29)  
[CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')](http://cwe.mitre.org/data/definitions/79.html)

