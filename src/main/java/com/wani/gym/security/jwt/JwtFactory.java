package com.wani.gym.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wani.gym.security.MemberContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private static String signingKey = "jwttest";

    public String generateToken(MemberContext memberContext) {
        String token = null;

        try {
            token = JWT.create()
                    .withIssuer("wani")
                    .withClaim("USERNAME", memberContext.getMember().getUserId())
                    .withClaim("USER_ROLE", memberContext.getMember().getMemberRoles().getRoleName())
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }
}
