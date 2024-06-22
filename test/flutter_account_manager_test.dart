import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_account_manager/flutter_account_manager.dart';
import 'package:flutter_account_manager/flutter_account_manager_platform_interface.dart';
import 'package:flutter_account_manager/flutter_account_manager_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterAccountManagerPlatform
    with MockPlatformInterfaceMixin
    implements FlutterAccountManagerPlatform {

  @override
  Future<bool> addAccount(Account account) {
    // TODO: implement addAccount
    throw UnimplementedError();
  }
  
  @override
  Future<AccessToken> getAccessToken(Account account, String authTokenType) {
    // TODO: implement getAccessToken
    throw UnimplementedError();
  }
  
  @override
  // TODO: implement getAccounts
  Future<List<Account>> get getAccounts => throw UnimplementedError();
  
  @override
  Future<bool> removeAccount(Account account) {
    // TODO: implement removeAccount
    throw UnimplementedError();
  }
  
  @override
  Future<bool> setAccessToken(Account account, AccessToken token) {
    // TODO: implement setAccessToken
    throw UnimplementedError();
  }
}

void main() {
  final FlutterAccountManagerPlatform initialPlatform = FlutterAccountManagerPlatform.instance;

  test('$MethodChannelFlutterAccountManager is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterAccountManager>());
  });

  test('getAccounts', () async {
    FlutterAccountManager flutterAccountManagerPlugin = FlutterAccountManager();
    MockFlutterAccountManagerPlatform fakePlatform = MockFlutterAccountManagerPlatform();
    FlutterAccountManagerPlatform.instance = fakePlatform;

    // expect(await FlutterAccountManager.getAccounts, '42');
  });
}
