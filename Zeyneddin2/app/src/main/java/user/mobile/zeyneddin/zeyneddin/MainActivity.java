package user.mobile.zeyneddin.zeyneddin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import user.mobile.zeyneddin.zeyneddin.main.printer.PrinterActivity;
import user.mobile.zeyneddin.zeyneddin.main.qaime.QaimeActivity;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.SqliteActivity;

/*
    Приложение создано 23:16 2018 05 19
    Цель написать 4 активити
    Первое активити запускается при входе в приложение
        В нем содержатся две кнопки, первая ведёт ко
        второй активити, вторая к третьёй
    Второе активити
        содержит RecycleView, 3 TextView и кнопку создать
         запись, при заполнении всех TextView и нажатии
          на кнопку, будет созлана запись в базе
    Третье активити
        тоже одержит RecycleWiew, но служит для вывода и
         удаления данных из Sqlite базы
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnQaime(View view) {
        /*
            Обоаботчик кнопки btn_Qaime при нажатии
            должен переходить к третьему активити
         */
        Intent qaimeActivity = new Intent(MainActivity.this, QaimeActivity.class);
        startActivity(qaimeActivity);

    }

    public void btnBaza(View view) {
        /*
            Обработчик кнопки btn_Baza при нажатии
            долже переходить ко второму активити
         */

        Intent bazaActivity = new Intent(MainActivity.this, SqliteActivity.class);
        startActivity(bazaActivity);

    }

    public void btnPrinter(View view) {
        /*
            Обработчик кнопки btn_Printer
            должен переходить к активити PrinterActivity
         */
        Intent printerActivity = new Intent(MainActivity.this, PrinterActivity.class);
        startActivity(printerActivity);
    }
}
