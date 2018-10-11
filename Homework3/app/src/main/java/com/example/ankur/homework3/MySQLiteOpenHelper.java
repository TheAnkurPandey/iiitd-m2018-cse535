package com.example.ankur.homework3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Quiz";
    private static final String TABLE_NAME = "Question_Bank";
    private static final String COLUMN_1 = "Question";
    private static final String COLUMN_2 = "Answer";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME  + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_1 + " TEXT, " + COLUMN_2 + " TEXT )";

        sqLiteDatabase.execSQL(query);



        insertData(sqLiteDatabase, "1. The Language that the computer can understand is called Machine Language.");
        insertData(sqLiteDatabase, "2. Magnetic Tape used random access method.");
        insertData(sqLiteDatabase, "3. Twitter is an online social networking and blogging service.");
        insertData(sqLiteDatabase, "4. Worms and trojan horses are easily detected and eliminated by antivirus software.");
        insertData(sqLiteDatabase, "5. Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.");
        insertData(sqLiteDatabase, "6. GNU / Linux is a open source operating system.");
        insertData(sqLiteDatabase, "7. Freeware is software that is available for use at no monetary cost.");
        insertData(sqLiteDatabase, "8. The hexadecimal number system contains digits from 1 - 15.");
        insertData(sqLiteDatabase, "9. IPv6 Internet Protocol address is represented as eight groups of four Octal digits.");
        insertData(sqLiteDatabase, "10. Octal number system contains digits from 0 - 7.");
        insertData(sqLiteDatabase, "11. MS Word is a hardware.");
        insertData(sqLiteDatabase, "12. CPU controls only input data of computer.");
        insertData(sqLiteDatabase, "13. CPU stands for Central Performance Unit. ");
        insertData(sqLiteDatabase, "14. You cannot format text in an e-mail message.");
        insertData(sqLiteDatabase, "15. You cannot preview a message before you print it");
        insertData(sqLiteDatabase, "16. You cannot edit Contact forms.");
        /*insertData(sqLiteDatabase, "17. ");
        insertData(sqLiteDatabase, "18. ");
        insertData(sqLiteDatabase, "19. ");
        insertData(sqLiteDatabase, "20. ");
        insertData(sqLiteDatabase, "21. ");
        insertData(sqLiteDatabase, "22. ");
        insertData(sqLiteDatabase, "23. ");
        insertData(sqLiteDatabase, "24. ");
        insertData(sqLiteDatabase, "25. ");
        insertData(sqLiteDatabase, "26. ");
        insertData(sqLiteDatabase, "27. ");
        insertData(sqLiteDatabase, "28. ");
        insertData(sqLiteDatabase, "29. ");
        insertData(sqLiteDatabase, "30. ");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void insertData(SQLiteDatabase sqLiteDatabase, String question) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, question);
        contentValues.put(COLUMN_2, "unanswered");

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

}
