package net.unadeca.arbolito.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.unadeca.arbolito.database.TestDatabase;

@Table(database = TestDatabase.class)
public class Arbol extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;
}
