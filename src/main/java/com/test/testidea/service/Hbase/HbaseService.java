package com.test.testidea.service.Hbase;

import com.test.testidea.config.HBaseConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/6/24 15:15
 */

@Slf4j
public class HbaseService {

    Admin admin;
    Configuration conf;
    Connection conn;

    @Autowired
    HBaseConfig hBaseConfig;

    /**
     * 构造函数 加载配置
     */
    public HbaseService() {
        conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "myhbase");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一张表
     *
     * @param tableName
     * @param familys
     * @throws Exception
     */
    public void createTable(String tableName, String... familys) throws Exception {
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + "表已经存在！");
        } else {
            List<ColumnFamilyDescriptor> cfs = new ArrayList<>();
            for (int i = 0 ; i < familys.length; i++) {
                ColumnFamilyDescriptor cf = ColumnFamilyDescriptorBuilder.of(familys[i]);
                cfs.add(cf);
            }
            TableDescriptor tableDesc = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
                .setColumnFamilies(cfs).build();

            admin.createTable(tableDesc);
            System.out.println(tableName + "表创建成功！");
        }
    }

    /*
     * 删除表
     *
     * @tableName 表名
     */
    public void deleteTable(String tableName) throws IOException {
        admin.disableTable(TableName.valueOf(tableName));
        admin.deleteTable(TableName.valueOf(tableName));
        System.out.println(tableName + "is deleted!");
    }

    /**
     * 添加数据
     * @param tableName 表名
     * @param family 列族
     * @param value 内容
     * @throws IOException
     */
    public void addData(String tableName, String family, String row, String column, String value) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
        table.close();
    }

    /*
     * 根据rwokey查询
     *
     * @rowKey rowKey
     *
     * @tableName 表名
     */
    public  Result getResult(String tableName, String rowKey)
        throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        Table table = conn.getTable(TableName.valueOf(tableName));
        Result result = table.get(get);
        for (Cell kv : result.listCells()) {
            System.out.println("family:" + Bytes.toString(kv.getFamilyArray()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifierArray()));
            System.out.println("value:" + Bytes.toString(kv.getValueArray()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        return result;
    }

    /*
     * 删除指定的列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     */
    public void deleteColumn(String tableName, String rowKey,
        String falilyName, String columnName) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.addColumn(falilyName.getBytes(), columnName.getBytes());
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + "is deleted!");
    }



    public static void main(String[] args) throws Exception {
        HbaseService hbase = new HbaseService();
//        hbase.createTable("t1","onehour", "threehour");
        hbase.deleteTable("t1");
//        hbase.addData("t1","t1", "two");
//        hbase.getResult("t1", "row0");
//        hbase.deleteColumn("t1", "row0", "t1", "column0");
    }
}
