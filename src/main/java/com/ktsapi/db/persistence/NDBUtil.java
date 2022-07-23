package com.ktsapi.db.persistence;
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ktsapi.db.persistence;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.dizitart.no2.Nitrite;
//import org.dizitart.no2.NitriteCollection;
//import org.dizitart.no2.objects.ObjectRepository;
//
//import com.ktsapi.core.Const;
//import com.ktsapi.db.persistence.pojo.Testplan;
//
//
///**
// * https://github.com/dizitart/nitrite-database
// * https://www.dizitart.org/nitrite-database/#filter
// * @author spathirana
// * @Description database will be created in %userprofile%/.kandy/kandy.db
// */
//public class NDBUtil {
//
//    private static Nitrite db = null;
//	private static final Path DBFolder = Paths.get(System.getProperty(Const.USER_HOME), Const.CONFIG_FOLDER_NAME);
//	private static final Path DBFile =DBFolder.resolve(Const.DB_FILE_NAME);
//	
//	private NDBUtil(){		
//	}
//
//    static {
//    	System.out.print(DBFolder.toString());
//		try {
//			if(!Files.exists(DBFolder)) {
//				System.out.println(">>> DB is not there <<<");
//				Files.createDirectories(DBFolder);
//	    	}
//		} catch(IOException ex) {
//			
//		}
//    }		
//		
//    	
//    
//    public static Nitrite createConnection() {
//    	if (null == db) {
//            db = Nitrite.builder().compressed()
//                    	.filePath(DBFile.toString())
//                    	.openOrCreate();
//        }
//        return db;
//    }
//
//    public static NitriteCollection createCollection() {
//        NitriteCollection collection = createConnection().getCollection("test");
//        return collection;
//    }
//
//    public static ObjectRepository<?> save(Class<?> persistenClass) {
//           ObjectRepository<?> repository = NDBUtil.createConnection().getRepository(persistenClass);
//           return repository;
//    }
//
//    public static ObjectRepository<?> fetch(Class<?> persistenClass) {
//            ObjectRepository<?> repository = NDBUtil.createConnection().getRepository(persistenClass);
//          	return repository;
//    }
//}
