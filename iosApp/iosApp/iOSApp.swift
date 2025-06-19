import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        InitKoinKt.doInitKoin()
        NativeResponseKt.setNativeResponseProvider(provider: IOSNativeResponseProvider())
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
