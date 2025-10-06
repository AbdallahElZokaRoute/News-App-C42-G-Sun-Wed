package com.route.newsappc42gsunwed.solid

import android.view.View
import android.widget.Button
import java.io.File
import java.util.Date


/**         UI X
 *       OOP
 *  SOLID Design principles ( )
 *
 *  'S'ingle Responsibility Principle
 *  'O'pen for Extension / Closed For Modification Principle
 *  'L'iskov's Substitution Principle
 *  'I'nterface Segregation Principle
 *  'D'epedency Inversion Principle
 *
 *
 *
 *   Maintainable
 *
 *   Codebase -> SOLID ->
 *   // Dependency Injection ( Dagger Hilt  )
 */    // High           Low/ High      //  DAG -> Direct Acyclic Graph
//  News View Model -> Repository  -> Local DataSource  -> Room
//                                 -> Remote DataSource -> Retrofit

//    Kotlin / Java   Single inheritance Language

fun test2() {
    val newsRepository: NewsRepository = NewsRepositoryImplV2(KtorDataSource(), SQLiteDataSource())
    //                                                        Ktor                   SQLite
    newsRepository
}

class NewsRepositoryImplV2(
    private val ktorDataSource: KtorDataSource,
    private val sqLiteDataSource: SQLiteDataSource
) : NewsRepository

class KtorDataSource
class SQLiteDataSource
class NewsRepositoryImpl(
    private val retrofitDataSource: RetrofitDataSource,
    private val roomDataSource: RoomDataSource
) : NewsRepository {

}

class RoomDataSource
class RetrofitDataSource

interface NewsRepository {

}


fun test() {
    val button: ButtonV2
    button.onClickListener = object : OnClickListener {
        override fun onClick(view: View?) {
            TODO("Not yet implemented")
        }
    }
    button.onLongClickListener = object : OnLongClickListener {
        override fun onLongClick(view: View?) {
            TODO("Not yet implemented")
        }
    }
    button.onDoubleClickListener = object : OnDoubleClickListener {
        override fun onDoubleClick(view: View?) {
            TODO("Not yet implemented")
        }
    }
}

class ButtonV2() {
    var onClickListener: OnClickListener? = null
    var onDoubleClickListener: OnDoubleClickListener? = null
    var onLongClickListener: OnLongClickListener? = null


}

interface OnClickListener {
    fun onClick(view: View?)
}

interface OnDoubleClickListener {
    fun onDoubleClick(view: View?)
}

interface OnLongClickListener {
    fun onLongClick(view: View?)
}

class AuthViewModel(
    private val loginService: LoginService,
    private val logger: FileLogger
) {
    val LOGIN_TAG = "Login"
    fun login(email: String, password: String) {
        try {  //   Call endpoint
            val response = loginService.loginWithEmailAndPassword(email, password)

        } catch (e: Exception) {
            logException(e.message ?: "")
        }
    }

    fun logException(message: String) {
        logger.log(LOGIN_TAG, message)
    }
}

open class FileLogger() {
    //  Here  80 Usages
    open fun log(tag: String, message: String) {
        //   Handle File and Exception  //   50 Crashes
        val file = File("errors.txt") //  1_000_000   ->    10
        //  Firebase Crashlytics
        file.writeText("Tag = $tag \nMessage = $message\n")
    }
}

class CustomFileLogger() : FileLogger() {
    override fun log(tag: String, message: String) {
        //   Handle File and Exception
        val file = File("custom_errors_file.txt")
        file.writeText("Tag = $tag \nMessage = $message\nDate =${Date()}\n")
    }
}


interface LoginService {
    fun loginWithEmailAndPassword(email: String, password: String)
}
//  1- Adapter (on Click Listener / callbacks )
//  2- MVVM (Fragment/Activity/Composable)
//              Handle Interaction
//          Call API / Query from Database
