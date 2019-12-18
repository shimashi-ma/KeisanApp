package jp.shimashimastudio.keisanapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //todo　テスト画面が開いたら
        //渡された問題数を受け取って、画面に表示させる
        val numbers = intent.extras
        val numberOfQesion  = numbers.getInt("ほにゃらら")
        textViewRmaining.text = numberOfQesion.toString()

        //1問目を出す
        question()

        //todo　「答え合わせ」ボタンが押されたら
        buttonAnswerCheck.setOnClickListener {
            //答え合わせメソッドを呼ぶ
            answerCheck()
        }

        //todo　「もどる」ボタンが押されたら
        buttonBack.setOnClickListener {

        }

        //電卓ボタン
        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        buttonMinus.setOnClickListener(this)
        buttonClear.setOnClickListener(this)

    }

    //todo　電卓ボタンが押されたら
    override fun onClick(v: View?) {

    }

    //todo　問題が出されたら（questionメソッド）
    private fun question() {
        //戻るボタン使用不可
        buttonBack.isEnabled = false

        //電卓ボタン使用可能
        buttonAnswerCheck.isEnabled = true
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
        buttonMinus.isEnabled = true
        buttonClear.isEnabled = true
    }

    //todo　答え合わせ処理（anwerCheckメソッド）
    private fun answerCheck() {

    }

}
