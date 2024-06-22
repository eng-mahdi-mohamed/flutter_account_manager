import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_account_manager_method_channel.dart';

abstract class FlutterAccountManagerPlatform extends PlatformInterface {
  /// Constructs a FlutterAccountManagerPlatform.
  FlutterAccountManagerPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterAccountManagerPlatform _instance =
      MethodChannelFlutterAccountManager();

  /// The default instance of [FlutterAccountManagerPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterAccountManager].
  static FlutterAccountManagerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterAccountManagerPlatform] when
  /// they register themselves.
  static set instance(FlutterAccountManagerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<List<Account>> get getAccounts {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> addAccount(Account account) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<AccessToken?> getAccessToken(Account account, String authTokenType) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> removeAccount(Account account) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> setAccessToken(Account account, AccessToken token) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
