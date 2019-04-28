package com.test.testidea.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * 标准的响应对象（所有的接口响应内容格式都应该是一致的）
 * @author fangzhimin
 * @date 2018/9/4 11:05
 */

@SuppressWarnings("unchecked")
@ApiModel("请求统一响应标准")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -4802543396830024571L;

	/**
	 * 响应内容
	 */
	@ApiModelProperty("响应内容")
	T body;

	/**
	 * 响应消息
	 */
	@ApiModelProperty(value = "响应消息", example = "OK")
	String message;

	/**
	 * 响应状态码
	 */
	@ApiModelProperty(value = "响应状态码", example = "200")
	Integer status;

	/**
	 * 返回成功响应内容（默认）
	 * @see HttpStatus#OK
	 * @return Result
	 */
	public static <T> Result<T> ok() {
		return (Result<T>) Result.builder()
				.status(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase())
				.build();
	}

	/**
	 * 返回自定义body的成功响应内容
	 * @param body {@link ResultBody} or {@link Object}
	 * @return Result
	 */
	public static <T> Result<T> ok(T body) {
		return (Result<T>) Result.builder()
				.status(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase())
				.body(body)
				.build();
	}

	/**
	 * 返回自定义body和message的成功响应内容
	 * @param message the leave
	 * @param body {@link ResultBody} or {@link Object}
	 * @return Result
	 */
	public static <T> Result<T> ok(String message, T body) {
		return (Result<T>) Result.builder()
				.status(HttpStatus.OK.value())
				.message(message)
				.body(body)
				.build();
	}

	/**
	 * 返回失败响应内容（默认）
	 * @see HttpStatus#BAD_REQUEST
	 * @return Result
	 */
	public static <T> Result<T> no() {
		return (Result<T>) Result.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.build();
	}

	/**
	 * 返回自定义message的失败响应内容
	 * @param message the leave
	 * @return Result
	 */
	public static <T> Result<T> no(String message) {
		return (Result<T>) Result.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(message)
				.build();
	}

	/**
	 * 返回自定义status和message的失败响应内容
	 * @param status {@link HttpStatus#value()}
	 * @param message the leave
	 * @return Result
	 */
	public static <T> Result<T> no(int status, String message) {
		return (Result<T>) Result.builder()
				.status(status)
				.message(message)
				.build();
	}

	/**
	 * 返回自定义message和body的失败响应内容
	 * @param message the leave
	 * @param body {@link ResultBody} or {@link Object}
	 * @return Result
	 */
	public static <T> Result<T> no(String message, T body) {
		return (Result<T>) Result.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(message)
				.body(body)
				.build();
	}

	/**
	 * toJSONString
	 * @see JsonInclude.Include#NON_NULL
	 * @return JSONString
	 */
	public String toJSONString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsString(this);
	}

	/**
	 * 标准的数据响应
	 * @author fangzhimin
	 * @date 2018/9/4 11:05
	 */
	@ApiModel
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Builder
	@Data
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class ResultBody<R> implements Serializable {
		private static final long serialVersionUID = 1523505566629563884L;

		/**
		 * 数据总数
		 */
		@ApiModelProperty("数据总数")
		Long total;

		/**
		 * 数据
		 */
		@ApiModelProperty("数据")
		R data;

		/**
		 * 附加属性
		 */
		@ApiModelProperty("附加属性")
		Map<String, Object> props;

		/**
		 * 构建 {@link Result#body} 内容, 通常用来构建数据查询结果
		 * @param data data
		 * @return ResultBody
		 */
		public static <R> ResultBody<R> to(R data) {
			return (ResultBody<R>) ResultBody.builder()
					.data(data)
					.build();
		}

		/**
		 * 构建 {@link Result#body} 内容, 通常用来构建数据查询结果
		 * @param total data total
		 * @param data data
		 * @return ResultBody
		 */
		public static <R> ResultBody<R> to(long total, R data) {
			return (ResultBody<R>) ResultBody.builder()
					.total(total)
					.data(data)
					.build();
		}

		/**
		 * 构建 {@link Result#body} 内容, 通常用来构建数据查询结果，并附加任意参数
		 * @param total 总数
		 * @param data 数据
		 * @param props 附加参数
		 * @return ResultBody
		 */
		public static <R> ResultBody<R> to(long total, R data, Map<String, Object> props) {
			return (ResultBody<R>) ResultBody.builder()
				.total(total)
				.data(data)
				.props(props)
				.build();
		}
	}
}
