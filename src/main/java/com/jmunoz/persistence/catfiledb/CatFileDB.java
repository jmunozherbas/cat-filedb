package com.jmunoz.persistence.catfiledb;

public class CatFileDB {

    public static final long MAX_LENGTH_FILE_CLASS = 5242880L;

    public static class StatusTransactionSession {
        public static final byte PENDING = 1;
        public static final byte PROGRESS = 2;
        public static final byte CANCELLED = 3;
    }

    public static class TypeTransaction {
        public static final byte SELECT = 0;
        public static final byte INSERT = 1;
        public static final byte UPDATE = 2;
        public static final byte DELETE = 3;
    }

    public static class Exception {
        public static final String DB_FILE_MANAGING = "FILE_MANAGING";
        public static final String DB_PARSING = "PARSING";
        public static final String DB_TRANSACTION = "TRANSACTION";
        public static final String DB_PROCESSING = "PROCESSING";
        public static final String DB_UNIQUE_ID_VIOLATED = "UNIQUE_ID_VIOLATED";
    }

}
