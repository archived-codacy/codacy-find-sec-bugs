//#Patterns: WEAK_TRUST_MANAGER
package codacy

//#Issue: {"severity": "Error", "line": 18, "patternId": "WEAK_TRUST_MANAGER"}
//#Issue: {"severity": "Error", "line": 19, "patternId": "WEAK_TRUST_MANAGER"}
//#Issue: {"severity": "Error", "line": 20, "patternId": "WEAK_TRUST_MANAGER"}
//#Issue: {"severity": "Error", "line": 29, "patternId": "WEAK_TRUST_MANAGER"}
//#Issue: {"severity": "Error", "line": 38, "patternId": "WEAK_TRUST_MANAGER"}

import javax.net.ssl.{ X509TrustManager, SSLContext }

object SecurityBypasser {
  import java.security.cert.X509Certificate
  import javax.net.ssl._

  // The all-permisive trust manager.
  object AllTM extends X509TrustManager {
    def getAcceptedIssuers(): Array[X509Certificate] = null
    def checkClientTrusted(certs: Array[X509Certificate], authType: String) {}
    def checkServerTrusted(certs: Array[X509Certificate], authType: String) {}
  }

  // The all-permissive hostname verifier.
  object AllHosts extends HostnameVerifier {
    def verify(urlHostName: String, session: SSLSession) = true
  }

  def destroyAllSSLSecurityForTheEntireVMForever() {
    val trustAllCerts = Array[TrustManager](AllTM)
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory())
    HttpsURLConnection.setDefaultHostnameVerifier(AllHosts);
  }
}

class Test {
   val trustAllCerts = Array[X509TrustManager](SecurityBypasser.AllTM)
}
