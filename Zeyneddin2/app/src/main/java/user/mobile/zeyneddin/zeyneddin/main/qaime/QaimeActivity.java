package user.mobile.zeyneddin.zeyneddin.main.qaime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import user.mobile.zeyneddin.zeyneddin.R;

/**
 *      Создан 2018,05,19 FARRUXJN
 */

/*

    Активити для выбора этементов из базы данных

    1) Создать RecycleView в нём будет три TextView и один CheckBox

    2) При нажатии на CheckBox выводить DialogFragment, в нём выбирать
       количество лементов.

    3) Посе выбора количества умножать количество на цену и выводить
       под CheckBox

    4) Добавить кнобку при нажатии которой выводить выбранне лементы
       в новый Activity c RecycleView содержащим выбранные элементы
       их количество и общую цену

        4.1) Рядом с каждым элементом добавить кнобку удаления элемента

    5) В новом Activity добавить Кнобку подтверждения пи нажатии которой
       элементы из базы удаляются, также открывается принтер и печатает чек

 */
public class QaimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qaime);
    }
}
