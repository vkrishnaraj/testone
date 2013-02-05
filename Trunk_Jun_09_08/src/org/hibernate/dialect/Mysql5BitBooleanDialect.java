package org.hibernate.dialect;

import java.sql.Types;

public class Mysql5BitBooleanDialect extends MySQL5Dialect{     
    public Mysql5BitBooleanDialect() {
        super();
        registerColumnType( Types.BOOLEAN, "bit" );     
    }       
}
