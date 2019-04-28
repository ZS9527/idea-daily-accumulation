package com.test.testidea.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Excel {
	
	// 支持的文件格式
	public static final String XLS = ".xls";
	public static final String XLSX = ".xlsx";
	
	/**
	 * Excel 列头标题文本
	 */
	String title();
	
	/**
	 * 字段是否必须，读取时如果该字段为空则抛出异常
	 */
	boolean required() default false;
	
	/**
	 * 是否为主键，如果为true，则跳过读取
	 */
	boolean isPK() default false;
	
	/**
	 * 列宽度，与所需容纳的字数成正比，需要根据实际情况调整
	 * <br>默认设置字符长度为10
	 */
	int width() default 10;
	
	/**
	 * 动态进行值转换，JSON格式字符串
	 * <pre>
	 * // 支持处理Date类型的格式化
	 * key -> rule，如：{ rule: "yyyy-MM-dd" }
	 * //  支持处理数值格式化
	 * key -> rule， 如：{ rule: "#,##0.00" }
	 * </pre>
	 */
	String format() default "{}";
	
	/**
	 * 序号，作为导出时对应的表头展示顺序，值越小月靠前
	 */
	int serial() default 1;
	
	/*************************************/
	/**
	 * 开启正则校验，当对应的值进行正则校验失败时抛出异常（异常中包含具体的校验信息）
	 */
	String regExp() default "";
	
	/**
	 * 正则校验失败后的提示信息（该信息会随异常一起抛出，需要自行处理后响应到前端）
	 */
	String placeholder() default "";
}
