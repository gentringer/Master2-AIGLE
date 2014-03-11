package com.gestion.model.hbase.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class CreateTable {

	private static Configuration conf = null;

	static {
		conf = HBaseConfiguration.create();
	}

	public static void creatTable(String tableName, String[] familys)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table already exists!");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(familys[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table " + tableName + " ok.");
		}
	}


	public static void deleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("delete table " + tableName + " ok.");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}


	public static void addRecord(String tableName, String rowKey,
			String family, String qualifier, String value) throws Exception {
		try {
			HTable table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
					.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table "
					+ tableName + " ok.");
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void delRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		List<Delete> list = new ArrayList<Delete>();
		Delete del = new Delete(rowKey.getBytes());
		list.add(del);
		table.delete(list);
		System.out.println("del record " + rowKey + " ok.");
	}

	public static void getOneRecord (String tableName, String rowKey) throws IOException{
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		for(KeyValue kv : rs.raw()){
			System.out.print(new String(kv.getRow()) + " " );
			System.out.print(new String(kv.getFamily()) + ":" );
			System.out.print(new String(kv.getQualifier()) + " " );
			System.out.print(kv.getTimestamp() + " " );
			System.out.println(new String(kv.getValue()));
		}
	}

	public static void getAllRecord (String tableName) {
		try{
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for(Result r:ss){

				for(KeyValue kv : r.raw()){
					System.out.print(new String(kv.getRow()) + " ");
					System.out.print(new String(kv.getFamily()) + ":");
					System.out.print(new String(kv.getQualifier()) + " ");
					System.out.print(kv.getTimestamp() + " ");
					System.out.println(new String(kv.getValue()));
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static List<String> getRecordsByDepartement (String tableName, String codedepartement) {
		List<String> listcodes=  new ArrayList<String>();
		try{
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for(Result r:ss){
				for(KeyValue kv : r.raw()){
					String qualifier = new String(kv.getQualifier());
					String value = new String(kv.getValue());
					if(qualifier.equals("DEP")&& value.equals(codedepartement)){
						//System.out.print(new String(kv.getRow()) + " ");
						//System.out.print(kv.getTimestamp() + " ");
						//System.out.println(new String(kv.getValue()));
						listcodes.add(new String(kv.getRow()));
					}
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}


		return listcodes;
	}

	@SuppressWarnings("deprecation")
	public static List<Commune> getAllRecordList (String tableName) {
		List<Commune> listcommune = new ArrayList<Commune>();
		try{
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for(Result r:ss){
				Commune comm = new Commune();
				for(KeyValue kv : r.raw()){
					String qualifier = new String(kv.getQualifier());

					if(qualifier.equals("NCC")){
						comm.nom=new String(kv.getValue());
					}
					if(qualifier.equals("DEP")){
						comm.departement=new String(kv.getValue());
					}
					if(qualifier.equals("REG")){
						comm.region=new String(kv.getValue());
					}
					if(qualifier.equals("CT")){
						comm.CT=new String(kv.getValue());
					}
					if(qualifier.equals("COM")){
						comm.COM=new String(kv.getValue());
					}
					if(qualifier.equals("CHEFLIEU")){
						comm.CHEFLIEU=new String(kv.getValue());
					}
					if(qualifier.equals("CDC")){
						comm.CDC=new String(kv.getValue());
					}
					if(qualifier.equals("AR")){
						comm.AR=new String(kv.getValue());
					}

				}
				listcommune.add(comm);
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return listcommune;

	}
}
