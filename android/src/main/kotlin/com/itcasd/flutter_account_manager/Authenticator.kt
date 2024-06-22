package com.itcasd.flutter_account_manager

import android.accounts.AccountManager
import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.NetworkErrorException
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log

import io.flutter.embedding.android.FlutterActivity


class Authenticator(context: Context?) : AbstractAccountAuthenticator(context) {
    
    private var ctx : Context?	

    init {
        ctx = context
    }

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun addAccount(
            response: AccountAuthenticatorResponse?, accountType: String?,
            authTokenType: String?, requiredFeatures: Array<String?>?, options: Bundle?
    ): Bundle? {
        Log.d("Mymsg", "AccountAuthenticator > addAccount() called with " + "response = [" + response + "], accountType = [" + accountType + "], authTokenType = [" + authTokenType + "], requiredFeatures = [" + requiredFeatures + "], options = [" + options + "]" + "---->START");

        var intent: Intent = FlutterActivity.withNewEngine().initialRoute("/login").build(ctx!!)
        // final Intent intent = new Intent(mContext, DirectLogin.class);
        // Constants.ARG_ACCOUNT_TYPE
        // Constants.ARG_AUTH_TYPE
        // Constants.ARG_IS_ADDING_NEW_ACCOUNT
        intent.putExtra("ACCOUNT_TYPE", accountType)
        intent.putExtra("AUTH_TYPE", authTokenType)
        intent.putExtra("IS_ADDING_NEW_ACCOUNT", true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        var bundle : Bundle? = Bundle()
        bundle!!.putParcelable(AccountManager.KEY_INTENT, intent);
        Log.d("Mymsg", "AccountAuthenticator > addAccount() returned [bundle: " + bundle!! + "----> STOP");
        return bundle!!
        
        // ctx!!.startActivity(
        // FlutterActivity
        //     .withNewEngine()
        //     .initialRoute("/login")
        //     .build(ctx!!)
        // )
        // return null
    }

    @Throws(NetworkErrorException::class)
    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?,
                                    options: Bundle?): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?,
                              authTokenType: String?, options: Bundle?): Bundle? {
        return null
    }

    override fun getAuthTokenLabel(authTokenType: String?): String? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?,
                                   authTokenType: String?, options: Bundle?): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?,
                             features: Array<String?>?): Bundle? {
        return null
    }
}