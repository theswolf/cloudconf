package core.september.cloudconf.authserver.service;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import core.september.cloudconf.authserver.conf.AppConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

@Service
public class JWTService  {
    private JWTVerifier verifier;
    private JWTSigner signer;
    private JWTSigner.Options shortOption;
    private JWTSigner.Options longOption;


    public JWTVerifier getVerifer() {
        if(verifier == null) {
            verifier = new JWTVerifier(AppConfig.AUTH_KEY);
        }
        return verifier;
    }

    public JWTSigner getSigner() {
        if(signer == null) {
            signer = new JWTSigner(AppConfig.AUTH_KEY);
        }
        return signer;
    }

    public JWTSigner.Options getShortOption() {
        if(shortOption == null) {
            shortOption = new JWTSigner.Options();
            shortOption.setExpirySeconds(AppConfig.SHORT_EXPIRY);
        }
        return shortOption;
    }

    public JWTSigner.Options getLongOption() {
        if(longOption == null) {
            longOption = new JWTSigner.Options();
            longOption.setExpirySeconds(AppConfig.LONG_EXPIRY);
        }
        return longOption;
    }

    public String shortSign(Map<String,Object> claims) {
       return getSigner().sign(claims,getShortOption());
    }

    public String longSign(Map<String,Object> claims) {
        return getSigner().sign(claims,getLongOption());
    }

    public Map<String,Object> claim(String token) throws SignatureException, NoSuchAlgorithmException, JWTVerifyException, InvalidKeyException, IOException {
        return getVerifer().verify(token);
    }
}