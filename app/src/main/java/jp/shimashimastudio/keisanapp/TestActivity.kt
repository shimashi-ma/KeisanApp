package jp.shimashimastudio.keisanapp

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test.imageView
import java.util.*


class TestActivity : AppCompatActivity(), View.OnClickListener {

    //問題数
    var mNumberOfQuestion: Int = 0
    //残り問題数
    var mNumberOfRemaining: Int = 0
    //正解問題数
    var mNumberOfCorrect: Int = 0
    //効果音
    lateinit var mSoundPool: SoundPool
    //サウンドID
    var mIntSound_Correct: Int = 0 //正解音
    var mIntSound_InCorrect: Int = 0 //不正解音


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //todo　テスト画面が開いたら
        //渡された問題数を受け取って、画面に表示させる
        val numbers = intent.extras
        mNumberOfQuestion  = numbers.getInt("ほにゃらら")
        textViewRmaining.text = mNumberOfQuestion.toString()
        mNumberOfRemaining = mNumberOfQuestion
        mNumberOfCorrect = 0 //なくてもいい

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

    override fun onResume() {
        super.onResume()
        //soundPoolの準備
        mSoundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build())
                .setMaxStreams(1)
                .build()

        } else {
            SoundPool(1,AudioManager.STREAM_MUSIC,0)
        }

        //効果音ファイルをメモリにダウンロード
        mIntSound_Correct = mSoundPool.load(this,R.raw.sound_correct,1)
        mIntSound_InCorrect = mSoundPool.load(this,R.raw.sound_incorrect,1)
    }

    override fun onPause() {
        super.onPause()
        //効果音の後片付け
        mSoundPool.release()
    }

    //todo　電卓ボタンが押されたら
    override fun onClick(v: View?) {

        val button: Button = v as Button

        when(v?.id){
            //クリアボタン　＝＞消す
            R.id.buttonClear
                -> textViewAnswer.text = ""

            //マイナスボタン（ーは先頭だけ）
            R.id.buttonMinus
                    -> if(textViewAnswer.text.toString() == "")
                       textViewAnswer.text = "-"

            //0 一文字目が0でかつマイナスの場合は0は押せない
            R.id.button0
                    -> if(textViewAnswer.text.toString() != "0" && textViewAnswer.text.toString() != "-")
                       textViewAnswer.append(button.text)

            //1~9の数字
            else -> if(textViewAnswer.text.toString() == "0")
                    textViewAnswer.text = button.text
                    else textViewAnswer.append(button.text)

        }

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

        //問題の二つの数字を1〜100からランダムに設定して表示
        val random = Random()
        val intQuestionLeft = random.nextInt(100) + 1  //random.nextInt(100)は0〜99になるので+1してる
        val intQuestionRight = random.nextInt(100) + 1
        textViewLeft.text = intQuestionLeft.toString()
        textViewRight.text = intQuestionRight.toString()

        //計算方法を　＋　ー　からランダムに表示
        when(random.nextInt(2) + 1){
            1 -> textViewOperator.text = "+"
            2 -> textViewOperator.text = "-"
        }

        //前の問題で入力した答えを消す
        textViewAnswer.text = ""

        //○×画像を見えないようにする
        imageView.visibility = View.INVISIBLE
    }

    //todo　答え合わせ処理（anwerCheckメソッド）
    private fun answerCheck() {

        //「もどる」「こたえあわせ」「電卓」ボタンを使えなくする
        buttonAnswerCheck.isEnabled = false
        buttonBack.isEnabled = false
        button0.isEnabled = false
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
        buttonClear.isEnabled = false
        buttonMinus.isEnabled = false

        //のこりの問題数をひとつ減らして表示させる
        mNumberOfRemaining -= 1  //デクリメント
        textViewRmaining.text = mNumberOfRemaining.toString()

        //○×画像が見えるようにする
        imageView.visibility = View.VISIBLE

        //自分が入力したこたえと本当のこたえを比較する
        //自分の答え
        val intMyAnswer: Int = textViewAnswer.text.toString().toInt()
        //本当の答え
        val intRealAnswer: Int =
            if(textViewOperator.text == "+"){
                textViewLeft.text.toString().toInt() + textViewRight.text.toString().toInt()
            } else {
                textViewLeft.text.toString().toInt() - textViewRight.text.toString().toInt()
            }
        //比較
        if (intMyAnswer == intRealAnswer){
            //合っている場合　＝＞　正解数をひとつ増やして表示・○画像・ピンポン音
            mNumberOfCorrect += 1
            textViewCorrect.text = mNumberOfCorrect.toString()
            imageView.setImageResource(R.drawable.pic_correct)
            mSoundPool.play(mIntSound_Correct, 1.0f,1.0f,0,0,1.0f)

        } else {
            //間違っている場合　＝＞　×画像・ブー音
            imageView.setImageResource(R.drawable.pic_incorrect)
            mSoundPool.play(mIntSound_InCorrect, 1.0f,1.0f,0,0,1.0f)
        }

        //正答率を計算して表示（正解数÷出題済問題数）
        val intPoint: Int = ((mNumberOfCorrect.toDouble()/(mNumberOfQuestion - mNumberOfRemaining).toDouble()) * 100).toInt()
        textViewPoint.text = intPoint.toString()

        //残り問題数がなくなった場合（テストが終わった場合）
        if(mNumberOfRemaining == 0){
            //もどるボタンを表示、こたえあわせボタンを非表示、「テスト終了」を表示
            buttonBack.isEnabled = true
            buttonAnswerCheck.isEnabled = false
            textViewMessage2.text = "テスト終了"
        }

        //残り問題数がまだある場合　＝＞　1秒後に次の問題を出す


    }

}
