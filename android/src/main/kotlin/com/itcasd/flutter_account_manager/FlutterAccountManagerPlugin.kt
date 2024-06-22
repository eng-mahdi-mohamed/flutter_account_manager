package com.itcasd.flutter_account_manager

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.*
import androidx.annotation.NonNull
import android.content.Context

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterAccountManagerPlugin */
class FlutterAccountManagerPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context : Context	

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext() 
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_account_manager")
    channel.setMethodCallHandler(this)
  }

  private fun addAccount(call: MethodCall, result: Result) {
      val accountName = call.argument<String>(ACCOUNT_NAME)
      val accountType = call.argument<String>(ACCOUNT_TYPE)
      val accountManager = AccountManager.get(context)
      val account = Account(accountName, accountType)
      val wasAdded = accountManager.addAccountExplicitly(account, null, null)
      result.success(wasAdded)
  }

  private fun setAccessToken(call: MethodCall, result: Result) {
      val accountName = call.argument<String>(ACCOUNT_NAME)
      val accountType = call.argument<String>(ACCOUNT_TYPE)
      val authTokenType = call.argument<String>(AUTH_TOKEN_TYPE)
      val accessToken = call.argument<String>(ACCESS_TOKEN)
      val account = Account(accountName, accountType)
      val accountManager = AccountManager.get(context)
      accountManager.setAuthToken(account, authTokenType, accessToken)
      result.success(true)
  }

  private fun getAccounts(result: Result) {
      val accounts = AccountManager.get(context).accounts
      val list = mutableListOf<HashMap<String, String>>()
      for (account in accounts) {
        list.add(hashMapOf(
                ACCOUNT_NAME to account.name,
                ACCOUNT_TYPE to account.type
        ))
      }
      result.success(list)
  }

  private fun getAccessToken(call: MethodCall, result: Result) {
      val accountName = call.argument<String>(ACCOUNT_NAME)
      val accountType = call.argument<String>(ACCOUNT_TYPE)
      val authTokenType = call.argument<String>(AUTH_TOKEN_TYPE)
      val account = Account(accountName, accountType)
      AccountManager.get(context).getAuthToken(account, authTokenType, null, false,
              { data ->
                val bundle: Bundle = data.result
                bundle.getString(AccountManager.KEY_AUTHTOKEN)?.let { token ->
                  result.success(hashMapOf(
                          AUTH_TOKEN_TYPE to authTokenType,
                          ACCESS_TOKEN to token
                  ))
                }
              },
              Handler(Looper.getMainLooper()) {
                result.success(null)
                true
              }
      )
  }

  private fun removeAccount(call: MethodCall, result: Result) {
      val accountName = call.argument<String>(ACCOUNT_NAME)
      val accountType = call.argument<String>(ACCOUNT_TYPE)
      val accountManager = AccountManager.get(context)
      val account = Account(accountName, accountType)

      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
        accountManager.removeAccount(account, null, null)
        // TODO: Need to wait for result from AccountManager.removeAccount()
        result.success(true)
      } else {
        val wasRemoved = accountManager.removeAccountExplicitly(account)
        result.success(wasRemoved)
      }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
      "addAccount" -> addAccount(call, result)
      "getAccounts" -> getAccounts(result)
      "getAccessToken" -> getAccessToken(call, result)
      "removeAccount" -> removeAccount(call, result)
      "setAccessToken" -> setAccessToken(call, result)
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
  
  companion object {
      private const val REQUEST_CODE = 23
      private const val ACCOUNT_NAME = "account_name"
      private const val ACCOUNT_TYPE = "account_type"
      private const val AUTH_TOKEN_TYPE = "auth_token_type"
      private const val ACCESS_TOKEN = "access_token"

      // @Suppress("UNUSED")
      // @JvmStatic
      // fun registerWith(registrar: Registrar) {
      //     val channel = MethodChannel(registrar.messenger(), "accountManager")
      //     channel.setMethodCallHandler(AccountManagerPlugin())
      // }
  }
}
