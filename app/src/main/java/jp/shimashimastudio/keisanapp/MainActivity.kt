package jp.shimashimastudio.keisanapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // todo スタートボタンを押したら
    // todo テスト画面を開く（spinnerから選んだ問題数を渡す）

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //選択肢を入れるためのArrayAdapterをセット　設定方法2パターン
//        val arrayAdapter = ArrayAdapter<Int>(this,android.R.layout.simple_spinner_item)
//        arrayAdapter.add(10)
//        arrayAdapter.add(20)
//        arrayAdapter.add(30)

        val arrayAdapter = ArrayAdapter.createFromResource(
            this,R.array.number_of_question,R.layout.support_simple_spinner_dropdown_item)

        //spinnerと選択肢をアダプターでつなぐ
        spinner.adapter = arrayAdapter

        //スタートボタンを押したとき
        button.setOnClickListener{

            //選択したアイテムをゲット
            val numberOfQuestion: Int = spinner.selectedItem.toString().toInt() //spinner.selectedItemの型はAnyなので型変換してる


            //インテントの渡し先を決める（インスタンス化）
            val intent = Intent(this@MainActivity,TestActivity::class.java)
            //渡す情報をインテントにセットする
            intent.putExtra("ほにゃらら",numberOfQuestion)
            //新しい画面を開いてインテントを渡す
            startActivity(intent)

        }
    }
}
