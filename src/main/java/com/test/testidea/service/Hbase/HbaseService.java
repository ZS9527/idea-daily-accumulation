package com.test.testidea.service.Hbase;

import com.test.testidea.config.HBaseConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/6/24 15:15
 */

@Slf4j
public class HbaseService {
//
//    Admin admin;
//    Configuration conf;
//    Connection conn;
//
//    @Autowired
//    HBaseConfig hBaseConfig;
//
//    /**
//     * 构造函数 加载配置
//     */
//    public HbaseService() {
//        conf = new Configuration();
//        conf.set("hbase.zookeeper.quorum", "myhbase");
//        conf.set("hbase.zookeeper.property.clientPort","2181");
//        try {
//            conn = ConnectionFactory.createConnection(conf);
//            admin = conn.getAdmin();
//        } catch (MasterNotRunningException e) {
//            e.printStackTrace();
//        } catch (ZooKeeperConnectionException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 创建一张表
//     *
//     * @param tableName
//     * @param familys
//     * @throws Exception
//     */
//    public void createTable(String tableName, String... familys) throws Exception {
//        if (admin.tableExists(TableName.valueOf(tableName))) {
//            System.out.println(tableName + "表已经存在！");
//        } else {
//            List<ColumnFamilyDescriptor> cfs = new ArrayList<>();
//            for (int i = 0 ; i < familys.length; i++) {
//                ColumnFamilyDescriptor cf = ColumnFamilyDescriptorBuilder.of(familys[i]);
//                cfs.add(cf);
//            }
//            TableDescriptor tableDesc = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
//                .setColumnFamilies(cfs).build();
//
//            admin.createTable(tableDesc);
//            System.out.println(tableName + "表创建成功！");
//        }
//    }
//
//    /*
//     * 删除表
//     *
//     * @tableName 表名
//     */
//    public void deleteTable(String tableName) throws IOException {
//        admin.disableTable(TableName.valueOf(tableName));
//        admin.deleteTable(TableName.valueOf(tableName));
//        System.out.println(tableName + "is deleted!");
//    }
//
//    /**
//     * 添加数据
//     * @param tableName 表名
//     * @param family 列族
//     * @param value 内容
//     * @throws IOException
//     */
//    public void addData(String tableName, String family, String row, String column, String value) throws IOException {
//        Table table = conn.getTable(TableName.valueOf(tableName));
//        Put put = new Put(Bytes.toBytes(row));
//        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
//        table.put(put);
//        table.close();
//    }
//
//    /**
//     * 查询
//     * @param tableName 表名
//     * @param rowKey 行key
//     * @param family 列族
//     * @param column 行
//     * @return
//     * @throws IOException
//     */
//    public  Result getResult(String tableName, String rowKey, String family, String column)
//        throws IOException {
//        Get get = new Get(Bytes.toBytes(rowKey));
//        Table table = conn.getTable(TableName.valueOf(Bytes.toBytes(tableName)));
//        Result result = table.get(get);
//        String resultStr = null;
//        if( !get.isCheckExistenceOnly()){
//            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
//            Result res = table.get(get);
//            byte[] resultB = res.getValue(Bytes.toBytes(family),
//                Bytes.toBytes(column));
//            resultStr = Bytes.toString(resultB);
//        }
//        System.out.println(resultStr);
//        return result;
//    }
//
//    /**
//     * 查询
//     * @param tableName 表名
//     * @return
//     * @throws IOException
//     */
//    public void getResultList(String tableName)
//        throws IOException {
//        Table table = conn.getTable(TableName.valueOf(Bytes.toBytes(tableName)));
//        ResultScanner rs = table.getScanner(new Scan());
//        for (Result r : rs) {
//            System.out.println("获得到rowkey:" + new String(r.getRow()));
//            for (Cell cell : r.listCells()) {
//                System.out.println("列：" + new String(cell.getQualifierArray(), cell
//                    .getQualifierOffset(), cell
//                    .getQualifierLength())
//                    + ":" + new String(cell.getValueArray(), cell
//                    .getValueOffset(), cell
//                    .getValueLength()));
//
//            }
//        }
//
//        return ;
//    }
//
//    /*
//     * 删除指定的列
//     *
//     * @tableName 表名
//     *
//     * @rowKey rowKey
//     *
//     * @familyName 列族名
//     *
//     * @columnName 列名
//     */
//    public void deleteColumn(String tableName, String rowKey,
//        String falilyName, String columnName) throws IOException {
//        Table table = conn.getTable(TableName.valueOf(tableName));
//        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
//        deleteColumn.addColumn(falilyName.getBytes(), columnName.getBytes());
//        table.delete(deleteColumn);
//        System.out.println(falilyName + ":" + columnName + "is deleted!");
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        HbaseService hbase = new HbaseService();
////        hbase.createTable("t1","onehour", "threehour");
////        hbase.deleteTable("t1");
//        hbase.addData("t1","threehour", "one", "name", "Jerry");
////        hbase.getResult("t1", "two", "onehour", "name");
////        hbase.getResultList("t1");
////        hbase.deleteColumn("t1", "one", "threehour", "name");
//    }
}
