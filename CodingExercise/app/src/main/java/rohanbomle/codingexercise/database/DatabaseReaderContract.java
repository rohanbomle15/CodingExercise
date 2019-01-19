package rohanbomle.codingexercise.database;

import android.provider.BaseColumns;

/***
 * Database contract
 * USE TO : Declare database parameters, columns etc
 *
 */
public final class DatabaseReaderContract {

    private DatabaseReaderContract(){}

    public static class AlbumEntry implements BaseColumns {
        public static final String TABLE_NAME = "ALBUMS";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USEAR_ID = "userid";
    }
}
