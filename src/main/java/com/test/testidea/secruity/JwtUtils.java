package com.test.testidea.secruity;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtUtils
 *
 * @author
 * @date 2017年12月10日 下午9:10:35
 */

@Slf4j
public class JwtUtils {

	/**
	 * Token Header
	 */
	public static final String TOKEN_HEADER = "Authorization";

	/**
	 * Token value prefix
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 * Token密钥
	 */
	private static final String SECRET = "DEFAULT_MY_SECRET";

	/**
	 * 要素Keys
	 */
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";

	/**
	 * 根据Token 获取用户名
	 *
	 * @param token Token
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}

	/**
	 * 根据Token获取创建时间
	 *
	 * @param token Token
	 * @return 创建时间
	 */
	public static Date getCreatedDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return new Date((Long) claims.get(CLAIM_KEY_CREATED));
	}

	/**
	 * 根据Token获取过期时间
	 *
	 * @param token Token
	 * @return 过期时间
	 */
	public static Date getExpirationDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 获取Token中的要素信息
	 *
	 * @param token Token
	 * @return 要素信息
	 */
	private static Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	/**
	 * 根据当前时间生成Token过期时间（默认过期时间为1年）
	 *
	 * @return 过期时间
	 */
	private static Date generateExpirationDate() {
		return Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 校验Token是否过期
	 *
	 * @param token Token
	 * @return 是否过期
	 */
	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 判断重要数据更新时间是否比Token创建时间早
	 *
	 * @param created Token创建时间
	 * @param lastPasswordReset 重要数据更新时间
	 * @return 比较结果
	 */
	private static Boolean isCreatedBeforeLastUpdate(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	/**
	 * 生成Token
	 *
	 * @param claims 要素
	 * @return Token
	 */
	private static String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	/**
	 * 生成Token
	 *
	 * @param user 用户信息
	 * @return Token
	 */
	public static String generateToken(JwtUser user) {
		Map<String, Object> claims = new HashMap<>(3);
		claims.put(CLAIM_KEY_CREATED, new Date());
		claims.put(CLAIM_KEY_USERNAME, user.getUsername());
		return generateToken(claims);
	}

	/**
	 * 刷新Token
	 *
	 * @param token 原始token
	 * @return 新Token
	 */
	public static String refreshToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 校验Token是否有效
	 *
	 * @param token 令牌
	 * @param user  用户信息
	 * @return 是否有效
	 */
	public static Boolean validateToken(String token, JwtUser user) {
		if (Strings.isNullOrEmpty(token) || user == null) {
			return false;
		}

		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);

		return (user.getEnabled() && username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastUpdate(created, Date.from(user.getAuthorityUpdateTime().atZone(ZoneId.systemDefault()).toInstant()))
				&& !isCreatedBeforeLastUpdate(created, Date.from(user.getLastPasswordResetTime().atZone(ZoneId.systemDefault()).toInstant())));
	}

	/**
	 * 获取Token
	 *
	 * @param request HttpServletRequest
	 * @return Token
	 */
	public static String getToken(HttpServletRequest request) {
		String header = request.getHeader(TOKEN_HEADER);
		if (Strings.isNullOrEmpty(header)) {
			header = request.getParameter(TOKEN_HEADER);
		}

		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			return header.substring(TOKEN_PREFIX.length());
		}

		return null;
	}

	/**
	 * 根据Token拼接出Header需要的Value
	 *
	 * @param token 令牌
	 * @return HeaderValue
	 */
	public static String getTokenHeaderValue(String token) {
		return TOKEN_PREFIX + token;
	}

}
