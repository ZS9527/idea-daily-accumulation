package com.test.testidea.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	/**
	 * 从某一行开始读取或写入，默认为0
	 */
	private int beginRow = 0;
	/**
	 * 过滤掉最后几行
	 */
	private int filterEndRow = 0;
	/**
	 * 标题行的背景色，默认为绿色
	 */
	private Short titleRowBackground = IndexedColors.GREEN.getIndex();
	/**
	 * 标题行的文字色，默认为白色
	 */
	private Short titleRowTextColor = IndexedColors.WHITE.getIndex();
	
	private Workbook wb;
	private Sheet sheet;
	private Row row;
	
	private void init(File file) throws Exception {
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file)));
		} catch (Exception e) {
			FileInputStream fileInputStream = new FileInputStream(file);
			wb = new XSSFWorkbook(fileInputStream);
			fileInputStream.close();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(beginRow);
	}
	
	public ExcelUtil() { }

	/**
	 * 初始化工具类
	 * @param file 需要读取的文件
	 * @throws Exception
	 */
	public ExcelUtil(File file) throws Exception {
		this.init(file);
	}
	
	/**
	 * 初始化工具类
	 * @param file 需要读取的文件
	 * @param beginRow 从第beginRow行开始读取数据
	 * @throws Exception
	 */
	public ExcelUtil(File file, int beginRow) throws Exception {
		this.beginRow = beginRow;
		this.init(file);
	}
	
	/**
	 * 获取标题
	 * @return 标题数组
	 */
	public String[] readTitle() {
		int cols = row.getPhysicalNumberOfCells();
		String[] titles = new String[cols];
		
		for (int i = 0; i < cols; i++) {
			titles[i] = getString(row.getRowNum(), i);
		}
		
		return titles;
	}
	
	/**
	 * 将   Excel 内容解析构建 List&lt;Bean&gt;
	 * @param <T>
	 * @param type Bean.class
	 * @return 
	 * @return 结果集
	 * @throws Exception
	 */
	public <T> List<T> readToModels(Class<T> type) throws Exception {
		List<T> list = new ArrayList<T>();
		
		Map<String, Map<String, Object>> map = this.getFieldInfo(type);
		
		// 获取所有列名
		String[] titles = this.readTitle();
		int cols = row.getPhysicalNumberOfCells();
		int rows = sheet.getLastRowNum() - filterEndRow;
		Map<String, Method> methods = new HashMap<>();
		
		// 遍历所有行
		for (int i = (beginRow + 1); i <= rows; i ++) {
			row = sheet.getRow(i);
			
			if (row == null) {
				break;
			}
			
			// 构建一个实体对象
			T bean = type.newInstance();

			int j = 0;
			while (j < cols) {
				// 获取 fieldName, fieldType
				String fieldName = map.get(titles[j]).get("name").toString();
				Class<?> fieldType = (Class<?>) map.get(titles[j]).get("type");
				// 获取format信息
				JSONObject format = JSONObject.parseObject(map.get(titles[j]).get("format").toString());
				
				// 跳过主键 【参数标识写法有待改进】
				if ((Boolean) map.get(titles[j]).get("isPK")) {
					j++;
					continue;
				}
				
				// 获取当前行列对应的值
				String value = this.getString(i, j);

				// 逆向处理 format
				if (!format.isEmpty()) {
					Iterator<?> it = format.keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						Object val = format.get(key);
						if (val != null && val.equals(value)) {
							value = key;
							break;
						}
					}
				}
				
				// 非空字段检查
				if (Strings.isNullOrEmpty(value) && (Boolean) map.get(titles[j]).get("required")) {
					throw new Exception("Convert to bean error, excel " + (i + 1) + " line: [ " + titles[j] + " ] 是非空字段, 请填写完整或删除错误行.");
				}
				// 正则校验
				String regExp = map.get(titles[j]).get("regExp").toString();
				if (!Strings.isNullOrEmpty(regExp)) {
					Pattern p = Pattern.compile(regExp);
					Matcher m = p.matcher(value);
					if (!m.matches()) {
						String placeholder = map.get(titles[j]).get("placeholder").toString();
						placeholder = Strings.isNullOrEmpty(placeholder) ? "" : "（" + placeholder + "）";
						
						throw new Exception("Convert to bean error, excel " + (i + 1) + " line: [ " + titles[j] + " ] 不合法" + placeholder + ", 请重新填写或删除错误行.");
					}
				}
				
				// 反射执行 setter
				Method method = null;
				if (methods.containsKey(fieldName)) {
					method = methods.get(fieldName);
				} else {
					method = type.getDeclaredMethod("set" + this.initcap(fieldName), fieldType);
				}
				
				// 目前处理两种两种类型，有需要在加 ，默认作为String处理
				if (fieldType == Integer.class) {
					method.invoke(bean, this.toInteger(value));
				} else if (fieldType == Date.class) {
					// 日期格式化，默认为`YYYY-MM-DD`
					String dateFormat = format.get("rule") == null ? "yyyy-MM-dd" : format.get("rule").toString();
					method.invoke(bean, this.toDate(value, dateFormat));
				} else if (fieldType == Double.class) {
					method.invoke(bean, this.toDouble(value));
				} else {
					method.invoke(bean, value);
				}
				
				j++;
			}
			// 加入集合
			list.add(bean);
		}
		
		return list;
	}
	
	/**
	 * 导出 Excel 格式为 .xlsx
	 * @param list 数据集合
	 * @param suffix 文件后缀，支持 Excel.XLS, Excel.XLSX
	 * @return 导出后的临时文件
	 * @throws Exception
	 */
	public File export(List<?> list, String suffix) throws Exception {
		File file = File.createTempFile(randomUUID(), suffix);
		wb = Excel.XLS.equals(suffix) ? new HSSFWorkbook() : new XSSFWorkbook();
		sheet = wb.createSheet();

		if (list != null && list.size() > 0) {
			// 创建标题单元格样式
			CellStyle topStyle = wb.createCellStyle();
			// 文本居中显示
			topStyle.setAlignment(CellStyle.ALIGN_CENTER);
			topStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			// 上下左右边框
			topStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			topStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			topStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			topStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			// 背景颜色
			if (titleRowBackground != null) {
				topStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				topStyle.setFillForegroundColor(titleRowBackground);
			}
			// 字体
			Font font = wb.createFont();
			font.setBoldweight((short) 600);
			if (titleRowTextColor != null) {
				font.setColor(titleRowTextColor);
			}
			topStyle.setFont(font);
	        
			// 创建数据单元格样式
			CellStyle style = wb.createCellStyle();
			// 文本居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 上下左右边框
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			
			// 创建第一行[设置高度]，用于列头设置
			row = sheet.createRow((short) beginRow);
			row.setHeight((short) (25 * 20));
			
			// 列信息
			Cell cell;
			
			// 通过反射获取注解
			Class<?> type = list.get(0).getClass();
			List<Excel> annotations = new ArrayList<Excel>();
			for (Field field : type.getDeclaredFields()) {
				if (field.isAnnotationPresent(Excel.class)) {
					annotations.add(field.getAnnotation(Excel.class));
				}
			}
			
			// 列头排序处理，生成列头信息
			this.sortTitle(annotations);
			int i = 0;
			for (Excel annotation : annotations) {
		        // 创建列头信息
		        cell = row.createCell((short) i++);
			    cell.setCellValue(annotation.title());
		        cell.setCellStyle(topStyle);
		        // 列宽度动态化设置
		        sheet.setColumnWidth(i - 1, annotation.width() * 256);
			}
			
			// 获取到   type 所有相关属性信息
			Map<String, Map<String, Object>> map = this.getFieldInfo(type);
			Map<String, Method> methods = new HashMap<>();
			
			for (int k = beginRow; k < list.size(); k++) {
				Row crow = sheet.createRow((short) k + 1);
				
				// 获取 Bean
				Object bean = list.get(k);
				
				int j = 0;
				while (j < row.getLastCellNum()) {
					String title = row.getCell(j).getRichStringCellValue().toString();
					Class<?> fieldType = (Class<?>) map.get(title).get("type");
					// 获取 fieldName
					String fieldName = map.get(title).get("name").toString();
					// 获取format信息
					JSONObject format = JSONObject.parseObject(map.get(title).get("format").toString());
					
					// 反射执行 getter
					Method method = null;
					if (methods.containsKey(fieldName)) {
						method = methods.get(fieldName);
					} else {
						method = type.getDeclaredMethod("get" + this.initcap(fieldName));
					}
					Object value = method.invoke(bean);
					
					// 处理format
					if (!format.isEmpty() && format.get(value) != null) {
						value = format.get(value);
					}
					
					// 创建对应列
					cell = crow.createCell((short) j++);
					cell.setCellStyle(style);
					
					// 单元格文本格式处理
					if (fieldType != Date.class && format.get("rule") != null) {
						DataFormat dataFormat = wb.createDataFormat();
						CellStyle formatStyle = cell.getCellStyle();
						formatStyle.setDataFormat(dataFormat.getFormat(format.get("rule").toString()));
						cell.setCellStyle(formatStyle);
					}
					
					// 创建列并赋值，类型检测
					if (value == null) {
						continue;
					} else if (fieldType == Integer.class) {
						if (value instanceof Integer) {
							cell.setCellValue(this.toInteger(value));
						} else {
							cell.setCellValue(value.toString());
						}
					} else if (fieldType == Date.class) {
						// 日期格式化，默认为`YYYY-MM-DD`
						String dateFormat = format.get("rule") == null ? "yyyy-MM-dd" : format.get("rule").toString();
						cell.setCellValue(this.toDates(value, dateFormat));
					} else if (fieldType == Double.class) {
						cell.setCellValue(this.toDouble(value));
					} else {
						cell.setCellValue(value.toString());
					}
				}
			}
		}
		
		// 写入
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		wb.write(fileOutputStream);
		fileOutputStream.close();
		return file;
	}
	
	/**
	 * 通过反射获取   Class.Field 相关信息
	 * @param type Class
	 * @return Field -&gt; Map
	 */
	public Map<String, Map<String, Object>> getFieldInfo(Class<?> type) {
		// 通过反射取到所有字段的相关信息，Type.Name.Excel(Annotation).name
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(Excel.class)) {
				Excel annotation = field.getAnnotation(Excel.class);
				// ...
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("name", field.getName());
				params.put("type", field.getGenericType());
				params.put("required", annotation.required());
				params.put("isPK", annotation.isPK());
				params.put("format", annotation.format());
				params.put("regExp", annotation.regExp());
				params.put("placeholder", annotation.placeholder());
				map.put(annotation.title(), params);
			}
		}
		return map;
	}
	
	/**
	 * 获取指定单元格的文本信息
	 * @param rownum 行号
	 * @param cellnum 列号
	 * @return 单元格的文本信息
	 */
	public String getString(int rownum, int cellnum) {
		try {
			// 合并单元格
			if (isMergedRegion(rownum, cellnum)) {
				return getMergedRegionValue(rownum, cellnum);
			}
			
			return getCellFormatValue(sheet.getRow(rownum).getCell(cellnum));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取合并单元格的数据
	 * @param row 
	 * @param column
	 * @return
	 */
	private String getMergedRegionValue(int row, int column){    
	    int sheetMergeCount = sheet.getNumMergedRegions();
	        
	    for (int i = 0 ; i < sheetMergeCount ; i++) {
	        CellRangeAddress ca = sheet.getMergedRegion(i);
	        int firstColumn = ca.getFirstColumn();
	        int lastColumn = ca.getLastColumn();
	        int firstRow = ca.getFirstRow();
	        int lastRow = ca.getLastRow();
	        if (row >= firstRow && row <= lastRow) {
	            if (column >= firstColumn && column <= lastColumn) {
	                Row fRow = sheet.getRow(firstRow);
	                Cell fCell = fRow.getCell(firstColumn);
	                String value = getCellFormatValue(fCell);
	                if (value == null) {
						continue;
					}
	                return value;
	            }
	        }
	    }
	        
	    return null;    
	}
	
	/**
	 * 判断指定单元格是否为合并单元格
	 * @param row 行号
	 * @param column 列号
	 * @return 是否为合并单元格
	 */
	private boolean isMergedRegion(int row ,int column) {  
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {  
			CellRangeAddress range = sheet.getMergedRegion(i);  
			int firstColumn = range.getFirstColumn();  
			int lastColumn = range.getLastColumn();  
			int firstRow = range.getFirstRow();  
			int lastRow = range.getLastRow();  
			if (row >= firstRow && row <= lastRow) {  
				if (column >= firstColumn && column <= lastColumn) {  
					return true;
				}  
			}  
		}  
		return false;  
	} 
	
	/**
	 * 根据类类型获取列值
	 * @param cell 列
	 * @return 值
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		
		if (cell != null) {
			switch (cell.getCellType()) {  
		        case Cell.CELL_TYPE_BLANK:  
		            cellvalue = "";  
		            break;  
		        case Cell.CELL_TYPE_BOOLEAN:  
		            cellvalue = String.valueOf(cell.getBooleanCellValue());  
		            break;  
		        case Cell.CELL_TYPE_ERROR:
		            cellvalue = null;
		            break;  
		        case Cell.CELL_TYPE_FORMULA:  
		            Workbook wb = cell.getSheet().getWorkbook();  
		            CreationHelper crateHelper = wb.getCreationHelper();
		            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
		            cellvalue = getCellFormatValue(evaluator.evaluateInCell(cell));  
		            break;  
		        case Cell.CELL_TYPE_NUMERIC:
		            if (DateUtil.isCellDateFormatted(cell)) {
		                Date theDate = cell.getDateCellValue();  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		                cellvalue = sdf.format(theDate);
		            } else {   
		                cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());  
		            }  
		            break;  
		        case Cell.CELL_TYPE_STRING:
		            cellvalue = cell.getRichStringCellValue().getString();  
		            break;  
		        default:  
		            cellvalue = null;
	        }
		}
		
		return Strings.isNullOrEmpty(cellvalue) ? null : cellvalue.trim();
	}
	
	/**
	 * 根据排序号进行（列头）排序
	 * @param annotations
	 */
	private void sortTitle(List<Excel> annotations) {
		for (int i = annotations.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (annotations.get(j + 1).serial() < annotations.get(j).serial()) {
                    Excel temp = annotations.get(j);
                    annotations.set(j, annotations.get(j + 1));
                    annotations.set(j + 1, temp);
                }
            }
        }
	}
	
	/**
	 * 将字符串首字母转大写
	 * @param str 字符串
	 * @return 转换后的字符串
	 */
	private String initcap(String str) {
		if (!Strings.isNullOrEmpty(str)) {
			char[] ch = str.toCharArray();
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
			return new String(ch);
		}
		return str;
	}
	
	/**
	 * 字符串转整形，处理方法
	 * @param obj 字符串
	 * @return 整形结果
	 */
	private Integer toInteger(Object obj) {
		if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
			if (obj.toString().indexOf(".") > -1) {
				return (int) Double.parseDouble(obj.toString());
			}
			return Integer.parseInt(obj.toString());
		}
		return null;
	}
	
	/**
	 * 日期转换
	 * @param obj Object
	 * @param format
	 * @return Date
	 * @throws ParseException 
	 */
	private Date toDate(Object obj, String format) throws ParseException {
		if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
			return new SimpleDateFormat(format).parse(obj.toString());
		}
		return null;
	}
	
	/**
	 * 日期转换为特定格式的字符串
	 * @param obj
	 * @param format
	 * @return
	 */
	private String toDates(Object obj, String format) {
		if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
			return new SimpleDateFormat(format).format(obj);
		}
		return null;
	}
	
	/**
	 * 浮点型转换
	 * @param obj Object
	 * @return Double
	 */
	private Double toDouble(Object obj) {
		if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
			return Double.parseDouble(obj.toString());
		}
		return null;
	}
	
	/**
	 * 手机号码验证
	 * @param str 字符串
	 * @return true | false
	 */
	@SuppressWarnings("unused")
	private boolean isMobile(String str) {
		if (!Strings.isNullOrEmpty(str)) {
	        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
	        Matcher m = p.matcher(str);
	        return m.matches();
		}
		return false;
	}
	
	/**
	 * 验证是否为固定电话
	 * @param str 字符串
	 * @return true | false
	 */
	@SuppressWarnings("unused")
	private boolean isPhone(String str) {
		if (!Strings.isNullOrEmpty(str)) {
			// 不带区号
			Pattern p = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
			// 带区号
			Pattern pa = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
			
			Matcher m = null;
			if (str.length() > 9) {
				m = pa.matcher(str);
			} else {
				m = p.matcher(str);
			}
			
			return m.matches();
		}
		return false;
	}
	
	public static String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public ExcelUtil setTitleRowBackground(Short titleRowBackground) {
		this.titleRowBackground = titleRowBackground;
		return this;
	}

	public ExcelUtil setTitleRowTextColor(Short titleRowTextColor) {
		this.titleRowTextColor = titleRowTextColor;
		return this;
	}

	/**
	 * 读取数据时过滤掉最后`rows`行
	 * @param rows 行数
	 */
	public ExcelUtil filterRows(int rows) {
		this.filterEndRow = rows;
		return this;
	}

	/**
	 * 清除标题行的颜色样式，该方法需要在 export 方法前调用
	 */
	public ExcelUtil clearTitleRowColorStyle() {
		this.titleRowTextColor = null;
		this.titleRowBackground = null;
		return this;
	}
	
	/**
	 * Copy行,复制格式和对应单元格的值
	 * @param sheet
	 * @param sourceIndex 复制行行号
	 * @param targetIndex 目标行行号
	 * @param copyValue 是否复制值
	 * @throws Exception
	 */
	public static void copyRow(Sheet sheet, int sourceIndex, int targetIndex, boolean copyValue) throws Exception {
		Row sourceRow = sheet.getRow(sourceIndex);
		int columnCount = sourceRow.getLastCellNum();
		if (sourceRow != null) {
			Row newRow = sheet.getRow(targetIndex);
			newRow.setHeight(sourceRow.getHeight());
			for (int j = 0; j < columnCount; j++) {  
                Cell templateCell = sourceRow.getCell(j);  
                if (templateCell != null) {  
                    Cell newCell = newRow.createCell(j);  
                    copyCell(templateCell, newCell, copyValue);
                }  
            }
		}
	}
	
	/**
	 * 复制单元格，包括样式和值
	 * @param sourceCell 复制单元格
	 * @param targetCell 目标单元格
	 * @param copyValue 是否复制值
	 * @throws Exception
	 */
	public static void copyCell(Cell sourceCell, Cell targetCell, boolean copyValue) throws Exception {
		targetCell.setCellStyle(sourceCell.getCellStyle());
		if (sourceCell.getCellComment() != null) {
			targetCell.setCellComment(sourceCell.getCellComment());
		}
		
		int sourceCellType = sourceCell.getCellType();
		targetCell.setCellType(sourceCellType);
		
		if (copyValue)
		switch (sourceCellType) {  
	        case Cell.CELL_TYPE_BLANK:
	            break;  
	        case Cell.CELL_TYPE_BOOLEAN:  
	        	targetCell.setCellValue(sourceCell.getBooleanCellValue());  
	            break;  
	        case Cell.CELL_TYPE_ERROR:
	            break;  
	        case Cell.CELL_TYPE_FORMULA:  
	            targetCell.setCellFormula(sourceCell.getCellFormula());
	            break;  
	        case Cell.CELL_TYPE_NUMERIC:
	        	if (DateUtil.isCellDateFormatted(sourceCell)) {  
					targetCell.setCellValue(sourceCell.getDateCellValue());  
	            } else {
	            	targetCell.setCellValue(sourceCell.getNumericCellValue());  
	            }
	            break;  
	        case Cell.CELL_TYPE_STRING:
	        	targetCell.setCellValue(sourceCell.getRichStringCellValue());
	            break;
	    }
	}
	
	/**
	 * 创建数据行的样式
	 * @param wb
	 * @return
	 */
	public static CellStyle defaultRowStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		return style;
	}

	/**
	 * 创建默认的头部样式（高亮行样式）
	 * @param wb
	 * @return
	 */
	public static CellStyle defaultHeaderStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBoldweight((short) 600);
		style.setFont(font);
		return style;
	}
	
	/**
	 * 重置默认头部样式的前景色（RGB）
	 * @param red RGB.red
	 * @param green RGB.green
	 * @param blue RGB.blue
	 */
	public static void resetDefaultHeaderColor(HSSFWorkbook wb, int red, int green, int blue) {
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(IndexedColors.GREY_25_PERCENT.getIndex(), (byte) red, (byte) green, (byte) blue);
	}
}
