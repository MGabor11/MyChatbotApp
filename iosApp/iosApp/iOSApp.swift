import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        InitKoinKt.doInitKoin()
        NativeResponse_iosKt.setNativeResponseProvider(provider: IOSNativeResponseProvider())
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
