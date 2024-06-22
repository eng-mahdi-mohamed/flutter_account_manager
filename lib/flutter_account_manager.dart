import 'flutter_account_manager_platform_interface.dart';
import 'flutter_account_manager_method_channel.dart';
import 'dart:async';

export 'account.dart';
export 'account_token.dart';

class FlutterAccountManager {
  static Future<bool> addAccount(Account account) {
    return FlutterAccountManagerPlatform.instance.addAccount(account);
  }

  static Future<AccessToken?> getAccessToken(Account account, String authTokenType) {
    return FlutterAccountManagerPlatform.instance
        .getAccessToken(account, authTokenType);
  }

  static Future<List<Account>> get getAccounts async {
    return FlutterAccountManagerPlatform.instance.getAccounts;
  }

  static Future<bool> removeAccount(Account account) async {
    return FlutterAccountManagerPlatform.instance.removeAccount(account);
  }

  static Future<bool> setAccessToken(
      Account account, AccessToken token) async {
    return FlutterAccountManagerPlatform.instance
        .setAccessToken(account, token);
  }
}
