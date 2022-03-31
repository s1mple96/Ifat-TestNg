package com.miyuan.ifat.support.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

class TokenUtil {

    public static String generateToken(Long userId) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> header = new HashMap<>(2);
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        return JWT.create().withHeader(header)
                .withClaim("iss", "Service")
                .withClaim("aud", "APP")
                .withClaim("userId", null == userId ? null : userId.toString())
                .withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256("0b980413-4eda-4c22-95a9-0d01d0ca2ba8"));
    }
}
