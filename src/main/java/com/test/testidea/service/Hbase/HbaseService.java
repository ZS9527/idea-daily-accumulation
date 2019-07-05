package com.test.testidea.service.Hbase;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

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

    /**
     * 构造函数 加载配置
     */
    public HbaseService() {
        conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.99.100:2181");
        conf.set("hbase.rootdir", "hdfs://192.168.57.133:9000/hbase");
        try {
            admin = ConnectionFactory.createConnection(conf).getAdmin();
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
     * @param column
     * @throws Exception
     */
    public void createTable(String tableName, String column) throws Exception {
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + "表已经存在！");
        } else {
            TableDescriptor tableDesc = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName)).build();
            admin.createTable(tableDesc);
            System.out.println(tableName + "表创建成功！");
        }
    }

    public static void main(String[] args) throws Exception {
        HbaseService hbase = new HbaseService();
        hbase.createTable("t1","cf");
    }
}
