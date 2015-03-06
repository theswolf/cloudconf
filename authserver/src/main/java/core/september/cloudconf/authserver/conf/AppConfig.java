package core.september.cloudconf.authserver.conf;

import java.util.concurrent.TimeUnit;

/**
 * Created by christian on 06/03/15.
 */
public class AppConfig {
    public static final String AUTH_HEADER="Authorization";
    public static final String AUTH_KEY="1234";
    public static final String CLAIM="CLAIM";
    public static final Integer SHORT_EXPIRY = 20;
    public static final Integer LONG_EXPIRY = Long.valueOf(TimeUnit.DAYS.toSeconds(3650)).intValue();

}
