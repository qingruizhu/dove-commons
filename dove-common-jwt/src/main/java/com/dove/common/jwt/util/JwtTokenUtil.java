package com.dove.common.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dove.common.jwt.enm.JwtErrorEnum;
import com.dove.common.jwt.exception.DoveJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * token工具
 * http://jwt.calebb.net
 */
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String TOKEN_ISSUE_MILLI = "issueAtMilli";

    /**
     * 签名私钥(base64加密)
     */
    private String secret;
    private long expiration;

    /**
     * @param secret -> Base64Utils.decodeFromString(secret) base64解密后再传入
     */
    public JwtTokenUtil(String secret) {
        this.secret = secret;
    }

    public JwtTokenUtil(String secret, Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(String subject) throws DoveJwtException {
        return this.sign(subject, System.currentTimeMillis());
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(String subject, Long issueTimeMillis) throws DoveJwtException {
        return this.sign(subject, null, issueTimeMillis);
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(String subject, Map<String, Object> claims, Long issueTimeMillis) throws DoveJwtException {
        if (null == subject || "".equals(subject)) throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_SIGN_HVT_SUBJECT);
        if (null == issueTimeMillis) issueTimeMillis = System.currentTimeMillis();
        try {
            JWTCreator.Builder builder = JWT.create();
            if (null != claims && !claims.isEmpty()) {
                claims.entrySet().stream().forEach((entry) -> {
                    String name = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        builder.withClaim(name, (String) value);
                    } else if (value instanceof Integer) {
                        builder.withClaim(name, (Integer) value);
                    } else if (value instanceof Long) {
                        builder.withClaim(name, (Long) value);
                    } else if (value instanceof Double) {
                        builder.withClaim(name, (Double) value);
                    } else if (value instanceof Boolean) {
                        builder.withClaim(name, (Boolean) value);
                    } else if (value instanceof Date) {
                        builder.withClaim(name, (Date) value);
                    }
                });
            }
            builder.withClaim(TOKEN_ISSUE_MILLI, issueTimeMillis);
            return builder.withSubject(subject)
                    .withIssuedAt(new Date(issueTimeMillis))//发布时间
                    .withExpiresAt(generateExpirationDate(issueTimeMillis))//过期日
                    .sign(getAlgorithm(subject));
        } catch (IllegalArgumentException | JWTCreationException e) {
            LOGGER.error("生成token失败:{}", e.getMessage());
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_SIGN);
        }
    }

    //生成签名的到期日
    private Date generateExpirationDate(Long issueTimeMillis) {
        return new Date(issueTimeMillis + expiration * 1000);//毫秒值
    }

    //获取签名算法
    private Algorithm getAlgorithm(String subject) throws DoveJwtException {
        StringBuffer realSecret = new StringBuffer(subject);
        realSecret.append(secret);
        try {
            return Algorithm.HMAC256(realSecret.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(JwtErrorEnum.JWT_ERROR_GET_ALGORITHM.getMessage(), e);
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_GET_ALGORITHM);
        }
    }

    /**
     * 验证token是否还有效
     *
     * @param token
     * @param subject
     */
    public boolean verify(String token, String subject) {
        try {
            JWTVerifier build = JWT.require(getAlgorithm(subject))
                    .withSubject(subject).build();
            build.verify(token);
        } catch (JWTVerificationException e) {
            if (e instanceof TokenExpiredException) {
                LOGGER.error("{}:token过期:{}", subject, e.getMessage());
            } else {
                LOGGER.error("{}:token非法:{}", subject, e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     * 获得token中 subject
     */
    public static String getSubject(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签名的subject失败:{}", e.getMessage());
            return null;
        }
    }


    /**
     * 获取签发时间->手动存入的claim,精确到毫秒
     */
    public static long getIssuedAtMilli(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(TOKEN_ISSUE_MILLI).asLong();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签名信息失败:{}", e.getMessage());
            return 0;
        }
    }

    /**
     * 获取签发时间(只精确到秒)
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签发时间失败:{}", e.getMessage());
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }


    public long getExpiration() {
        return expiration;
    }
}
