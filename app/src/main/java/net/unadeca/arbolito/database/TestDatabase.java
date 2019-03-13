package net.unadeca.arbolito.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TestDatabase.NAME, version = TestDatabase.VERSION)
public class TestDatabase {
    public static final String NAME = "TestDatabase";
    public static final int VERSION = 1;
}