 The use of JavaScript Interface could expose the WebView to risky API. If an XSS is triggered in the WebView, the class could be called by the malicious JavaScript code.

**Code at risk:**

```
WebView myWebView = (WebView) findViewById(R.id.webView);

myWebView.addJavascriptInterface(new FileWriteUtil(this), "fileWriteUtil");

WebSettings webSettings = myWebView.getSettings();
webSettings.setJavaScriptEnabled(true);

[...]
class FileWriteUtil {
    Context mContext;

    FileOpenUtil(Context c) {
        mContext = c;
    }

    public void writeToFile(String data, String filename, String tag) {
        [...]
    }
}
```
  

**References**  
[Android Official Doc: WebView.addJavascriptInterface()](http://developer.android.com/reference/android/webkit/WebView.html#addJavascriptInterface%28java.lang.Object,%20java.lang.String%29)  
[CWE-749: Exposed Dangerous Method or Function](https://cwe.mitre.org/data/definitions/749.html)

