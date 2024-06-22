import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_account_manager_platform_interface.dart';
import 'account.dart';
import 'account_token.dart';
export 'account.dart';
export 'account_token.dart';

/// An implementation of [FlutterAccountManagerPlatform] that uses method channels.
class MethodChannelFlutterAccountManager extends FlutterAccountManagerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static const methodChannel = MethodChannel('flutter_account_manager');

  static const String _keyAccountName = 'account_name';
  static const String _keyAccountType = 'account_type';
  static const String _keyAuthTokenType = 'auth_token_type';
  static const String _keyAccessToken = 'access_token';

  @override
  Future<bool> addAccount(Account account) async {
    bool? res = await methodChannel.invokeMethod<bool>('addAccount',
        {_keyAccountName: account.name, _keyAccountType: account.accountType});
    return res == true ? true : false;
  }

  @override
  Future<AccessToken?> getAccessToken(
      Account account, String authTokenType) async {
    AccessToken? accessToken;
    final res = await methodChannel.invokeMethod('getAccessToken', {
      _keyAccountName: account.name,
      _keyAccountType: account.accountType,
      _keyAuthTokenType: authTokenType
    });
    if (res != null) {
      accessToken = AccessToken(res[_keyAuthTokenType], res[_keyAccessToken]);
    }
    return accessToken;
  }

  @override
  Future<List<Account>> get getAccounts async {
    List<Account> accounts = [];
    final result = await methodChannel
        .invokeMethod<List>('getAccounts');
    if (result != null) {
      for (var item in result) {
        if (item[_keyAccountName] != null && item[_keyAccountType] != null) {
          accounts.add(Account(item[_keyAccountName]!, item[_keyAccountType]!));
        }
      }
    }
    return accounts;
  }

  @override
  Future<bool> removeAccount(Account account) async {
    bool? res = await methodChannel.invokeMethod<bool>('removeAccount',
        {_keyAccountName: account.name, _keyAccountType: account.accountType});
    return res == true ? true : false;
  }

  @override
  Future<bool> setAccessToken(Account account, AccessToken token) async {
    final data = {
      _keyAccountName: account.name,
      _keyAccountType: account.accountType,
      _keyAuthTokenType: token.tokenType,
      _keyAccessToken: token.token,
    };
    bool? res = await methodChannel.invokeMethod<bool>('setAccessToken', data);
    return res == true ? true : false;
  }
}
