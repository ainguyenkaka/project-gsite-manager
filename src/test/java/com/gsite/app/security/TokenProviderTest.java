package com.gsite.app.security;

import com.gsite.app.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenProviderTest {

    private final Logger log = LoggerFactory.getLogger(TokenProviderTest.class);

    private String secretKey;

    @Before
    public void setUp() {
        secretKey = "8a1d3b6fa9e302c0415b6d650e9911883f953fd3";
    }

    @Test
    public void getTestingToken() {
        String token = TokenProvider.createTestToken(secretKey);
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n========================Token========================\n");
        sb.append(token);
        sb.append("\n=====================================================\n");
        log.info(sb.toString());
        assertThat(token).isNotEmpty();

    }

}
